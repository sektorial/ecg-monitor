package ua.com.ivolnov.ecg.notifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.patient.Patient;

@Slf4j
@RequiredArgsConstructor
class EcgChartNotifier implements WsNotifier {


    private final SimpMessagingTemplate simpMessagingTemplate;
    private final String destinationPrefix;

    @Override
    public void notify(final Patient patient, final EcgData ecgData) {
        log.debug("Sending ECG data for patient {}", patient.getId());
        simpMessagingTemplate.convertAndSend(destinationPrefix + patient.getId(), ecgData);
    }

}
