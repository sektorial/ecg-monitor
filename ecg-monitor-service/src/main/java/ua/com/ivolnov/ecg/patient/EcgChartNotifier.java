package ua.com.ivolnov.ecg.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class EcgChartNotifier implements WsNotifier {

    private static final String DESTINATION_PREFIX = "/topic/patient/";

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void notify(final Patient patient, final EcgData ecgData) {
        log.info("Sending ECG data for patient {}", patient.getId());
        simpMessagingTemplate.convertAndSend(DESTINATION_PREFIX + patient.getId(), ecgData);
    }

}
