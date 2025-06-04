package ua.com.ivolnov.ecg.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.ivolnov.ecg.data.EcgDataStorage;
import ua.com.ivolnov.ecg.integration.EcgAiModel;
import ua.com.ivolnov.ecg.notifier.WsNotifier;
import ua.com.ivolnov.ecg.patient.PatientService;

@Configuration
class ProcessorConfiguration {

    @Bean
    EcgSampleProcessor ecgSampleProcessor(final EcgAiModel aiModel,
                                          final EcgDataStorage storage,
                                          final PatientService patientService,
                                          final WsNotifier alertNotifier,
                                          final WsNotifier ecgChartNotifier) {
        return new EcgSampleProcessor(aiModel, storage, patientService, alertNotifier, ecgChartNotifier);
    }

}
