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
class AlertNotifierTest {

    private static final String DESTINATION = "/topic/alert";

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @Test
    void shouldNotify() {
        final UUID patientId = UUID.randomUUID();
        final String patientName = "name";
        final long timestamp = System.currentTimeMillis();
        final Patient patient = new Patient(patientId, patientName);
        final EcgData ecgData = EcgData.of(timestamp, 1.1, true);
        final AlertMessage alertMessage = AlertMessage.builder()
                .patientId(patientId)
                .patientName(patientName)
                .timestamp(timestamp)
                .build();

        doNothing().when(simpMessagingTemplate).convertAndSend(DESTINATION, alertMessage);

        new AlertNotifier(simpMessagingTemplate, DESTINATION).notify(patient, ecgData);

        verifyNoMoreInteractions(simpMessagingTemplate);
    }

}
