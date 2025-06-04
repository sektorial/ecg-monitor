package ua.com.ivolnov.ecg.patient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ua.com.ivolnov.ecg.common.UserRole.STAFF;
import static ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig.STAFF_NAME;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StaffPatientViewController.class)
@WithMockUser(username = STAFF_NAME, roles = STAFF)
@Import(WebSecurityTestConfig.class)
class StaffPatientViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnValidModelAndViewForValidId() throws Exception {
        final String validId = "validPatientId";

        mockMvc.perform(get("/web/staff/patient/" + validId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("patientId", validId))
                .andExpect(view().name("staff-patient"));
    }

}
