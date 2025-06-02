package ua.com.ivolnov.ecg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlertScheduler {

    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 5_000)
    public void sendAlert() {
        log.info("Alerting..");
        messagingTemplate.convertAndSend("/topic/alerts", "ALERT: Check ECG!");
    }

}
