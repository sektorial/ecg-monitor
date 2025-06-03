package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class AlertNotifier implements WsNotifier {

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void notify(final UUID patientId, final EcgData ecgData) {
        log.info("Sending alert for patient {}", patientId);
        if (!ecgData.isCritical()) {
            log.warn("Attempted to alert non-critical data");
            return;
        }
        // Alert structure can be enhanced
        String msg = "CRITICAL SPIKE for patient " + patientId + " at " + ecgData.getTimestamp();
        messagingTemplate.convertAndSend("/topic/alerts", msg);
    }

}
