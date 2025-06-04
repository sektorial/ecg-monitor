package ua.com.ivolnov.ecg.patient;

import java.util.Optional;
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
import ua.com.ivolnov.ecg.integration.EcgSourceConnector;

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
@WebMvcTest(controllers = AdminPatientViewController.class)
@WithMockUser(username = ADMIN_NAME, roles = ADMIN)
@Import(WebSecurityTestConfig.class)
class AdminPatientViewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PatientService patientService;
    @MockitoBean
    private EcgSourceConnector ecgSourceConnector;

    @Test
    public void shouldReturnPatientManagementWhenGetPatients() throws Exception {
        final Set<Patient> patients = Set.of(new Patient(UUID.randomUUID(), "patient #2"));

        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/web/admin/patient")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("msg", "Patient Management"))
                .andExpect(model().attribute("patients", patients))
                .andExpect(view().name("admin-patient"));
    }

    @Test
    public void shouldRedirectToAdminPatientWhenAddPatient() throws Exception {
        final String patientName = "patient #3";
        final Patient patient = new Patient(UUID.randomUUID(), patientName);

        when(patientService.addPatient(patientName)).thenReturn(patient);

        mockMvc.perform(post("/web/admin/patient/add")
                        .param("name", patientName)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/patient"));
    }

    @Test
    public void shouldRedirectToAdminPatientWhenRemovePatient() throws Exception {
        final UUID patientId = UUID.randomUUID();
        final Patient patient = new Patient(patientId, "patient #4");

        when(patientService.removePatient(patientId)).thenReturn(Optional.of(patient));

        mockMvc.perform(post("/web/admin/patient/remove")
                        .param("id", patientId.toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/web/admin/patient"));
    }

}
