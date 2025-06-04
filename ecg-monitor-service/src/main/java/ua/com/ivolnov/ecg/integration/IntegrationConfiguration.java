package ua.com.ivolnov.ecg.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.ivolnov.ecg.processor.EcgSampleProcessor;
import ua.com.ivolnov.ecg.source.EcgSourceProducer;

@Configuration
class IntegrationConfiguration {

    @Bean
    EcgAiModel ecgAiModel() {
        return new EcgAiModel(1.5);
    }

    @Bean
    EcgSourceConnector ecgSourceConnector(final EcgSourceProducer ecgSourceProducer,
                                          final EcgSampleProcessor ecgSampleProcessor) {
        return new EcgSourceConnector(ecgSourceProducer, ecgSampleProcessor);
    }

}
