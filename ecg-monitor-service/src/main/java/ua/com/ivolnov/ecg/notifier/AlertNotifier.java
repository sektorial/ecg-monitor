package ua.com.ivolnov.ecg.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.patient.Patient;

@Slf4j
@RequiredArgsConstructor
class AlertNotifier implements WsNotifier {

    private final SimpMessagingTemplate messagingTemplate;
    private final String destination;

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
        messagingTemplate.convertAndSend(destination, message);
    }

}
