package ua.com.ivolnov.ecg.notifier;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.patient.Patient;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class EcgChartNotifierTest {

    private static final String DESTINATION_PREFIX = "/topic/ecg/";

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Test
    void shouldNotify() {
        final UUID patientId = UUID.randomUUID();
        final Patient patient = new Patient(patientId, "name");
        final EcgData ecgData = EcgData.of(System.currentTimeMillis(), 1.1, true);

        doNothing().when(simpMessagingTemplate).convertAndSend(DESTINATION_PREFIX + patientId, ecgData);

        new EcgChartNotifier(simpMessagingTemplate, DESTINATION_PREFIX).notify(patient, ecgData);

        verifyNoMoreInteractions(simpMessagingTemplate);
    }

}
