package ua.com.ivolnov.ecg.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class AlertNotifier implements WsNotifier {

    private static final String DESTINATION = "/topic/alerts";

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void notify(final Patient patient, final EcgData ecgData) {
        log.info("Sending alert for patient {}", patient);
        if (!ecgData.isCritical()) {
            log.warn("Attempted to alert non-critical data");
            return;
        }
        final AlertMessage message = AlertMessage.builder()
                .patientId(patient.getId())
                .patientName(patient.getName())
                .timestamp(ecgData.getTimestamp())
                .build();
        messagingTemplate.convertAndSend(DESTINATION, message);
    }

}
