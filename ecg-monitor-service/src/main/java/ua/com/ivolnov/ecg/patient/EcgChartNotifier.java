package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class EcgChartNotifier implements WsNotifier {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notify(final UUID patientId, final EcgData ecgData) {
        log.info("Sending ECG data for patient {}", patientId);
        simpMessagingTemplate.convertAndSend("/topic/patient/" + patientId, ecgData);
    }

}
