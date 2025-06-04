package ua.com.ivolnov.ecg.staff;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ua.com.ivolnov.ecg.common.UserRole.ADMIN;
import static ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig.ADMIN_NAME;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AdminStaffViewController.class)
@WithMockUser(username = ADMIN_NAME, roles = ADMIN)
@Import(WebSecurityTestConfig.class)
class AdminStaffViewControllerTest {

    private static final String USERNAME = "johny";
    private static final String PASSWORD = UUID.randomUUID().toString();

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MedicalStaffService staffService;

    @Test
    void shouldDisplayStaffPage() throws Exception {
        final Set<MedicalStaff> medicalStaffSet = new HashSet<>();
        final MedicalStaff staffMember = new MedicalStaff(USERNAME, PASSWORD);
        medicalStaffSet.add(staffMember);

        when(staffService.getAllStaff()).thenReturn(medicalStaffSet);

        mockMvc.perform(get("/web/admin/staff")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-staff"))
                .andExpect(model().attribute("staff", hasSize(1)))
                .andExpect(model().attribute("staff",
                        hasItem(allOf(hasProperty("username", is(USERNAME)),
                                hasProperty("password", is(PASSWORD))))));
    }

    @Test
    void shouldAddStaff() throws Exception {
        mockMvc.perform(post("/web/admin/staff/add")
                        .with(csrf())
                        .param("username", USERNAME)
                        .param("password", PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/staff"));
    }

    @Test
    void shouldRemoveStaff() throws Exception {
        mockMvc.perform(post("/web/admin/staff/remove")
                        .with(csrf())
                        .param("username", USERNAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/staff"));
    }

}
