package ua.com.ivolnov.ecg;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.ivolnov.ecg.common.UserRole;
import ua.com.ivolnov.ecg.patient.Patient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(classes = EcgApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EcgApplicationIT {

    private static final String PATIENT_NAME = "good patient";
    private static final String STAFF_USERNAME = "staff";
    private static final SharedData SHARED_TEST_DATA = new SharedData();
    private static final String ADMIN_USERNAME = "admin";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @WithMockUser(username = ADMIN_USERNAME, roles = UserRole.ADMIN)
    void shouldCreateStaffByAdmin() throws Exception {
        mockMvc.perform(post("/web/admin/staff/add")
                        .with(csrf())
                        .param("username", STAFF_USERNAME)
                        .param("password", UUID.randomUUID().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/staff"));
    }

    @Test
    @Order(1)
    @WithMockUser(username = ADMIN_USERNAME, roles = UserRole.ADMIN)
    void shouldCreatePatientByAdmin() throws Exception {
        mockMvc.perform(post("/web/admin/patient/add")
                        .param("name", PATIENT_NAME)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/patient"));
    }

    @Test
    @Order(1)
    @WithMockUser(username = ADMIN_USERNAME, roles = UserRole.ADMIN)
    void shouldRetrievePatientViewByAdmin() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(get("/web/admin/patient")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("msg", "Patient Management"))
                .andExpect(view().name("admin-patient"))
                .andReturn();
        final Set<Patient> patients = (Set<Patient>) mvcResult.getModelAndView().getModel().get("patients");
        assertThat(patients).isNotEmpty();
        SHARED_TEST_DATA.setPatients(patients);
    }

    @Test
    @Order(2)
    @WithMockUser(username = STAFF_USERNAME, roles = UserRole.STAFF)
    void shouldViewPatientByStaff() throws Exception {
        final var patientId = SHARED_TEST_DATA.getAnyPatientIdAsString();
        mockMvc.perform(get("/web/staff/patient/" + patientId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("patientId", patientId))
                .andExpect(view().name("staff-patient"));
    }

}
