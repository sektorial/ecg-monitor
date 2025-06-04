package ua.com.ivolnov.ecg.notifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
class NotifierConfiguration {

    private static final String ALERT_DESTINATION = "/topic/alerts";
    private static final String ECG_CHART_DESTINATION_PREFIX = "/topic/patient/";

    @Bean
    WsNotifier alertNotifier(final SimpMessagingTemplate messagingTemplate) {
        return new AlertNotifier(messagingTemplate, ALERT_DESTINATION);
    }

    @Bean
    WsNotifier ecgChartNotifier(final SimpMessagingTemplate messagingTemplate) {
        return new EcgChartNotifier(messagingTemplate, ECG_CHART_DESTINATION_PREFIX);
    }

}
