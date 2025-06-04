package ua.com.ivolnov.ecg.data;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static java.lang.System.currentTimeMillis;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.com.ivolnov.ecg.common.UserRole.ADMIN;
import static ua.com.ivolnov.ecg.common.UserRole.STAFF;
import static ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig.ADMIN_NAME;
import static ua.com.ivolnov.ecg.configuration.WebSecurityTestConfig.STAFF_NAME;

@Import(WebSecurityTestConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = EcgDataController.class)
public class EcgDataControllerTest {

    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final List<EcgData> ECG_DATA_LIST = List.of(EcgData.of(100L, 0.5, false));

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EcgDataStorage storage;

    @WithMockUser(username = STAFF_NAME, roles = STAFF)
    @Test
    void shouldReturnAllEcgDataWhenNoTimestampIsProvided() throws Exception {
        when(storage.getAll(PATIENT_ID)).thenReturn(ECG_DATA_LIST);

        mockMvc.perform(get("/api/patient/" + PATIENT_ID + "/ecg")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(ECG_DATA_LIST)));
    }

    @WithMockUser(username = STAFF_NAME, roles = STAFF)
    @Test
    void shouldReturnEcgDataSinceGivenTimestamp() throws Exception {
        final long now = currentTimeMillis();

        when(storage.getSince(PATIENT_ID, now)).thenReturn(ECG_DATA_LIST);

        mockMvc.perform(get("/api/patient/" + PATIENT_ID + "/ecg")
                        .param("fromMillis", String.valueOf(now))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(ECG_DATA_LIST)));
    }

    @WithMockUser(username = ADMIN_NAME, roles = ADMIN)
    @Test
    void shouldForbidAccessForAdmin() throws Exception {
        mockMvc.perform(get("/api/patient/" + UUID.randomUUID() + "/ecg")
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }

}
