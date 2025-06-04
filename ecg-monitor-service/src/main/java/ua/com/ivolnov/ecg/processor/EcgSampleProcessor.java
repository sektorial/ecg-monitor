package ua.com.ivolnov.ecg.processor;

import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.data.EcgDataStorage;
import ua.com.ivolnov.ecg.integration.EcgAiModel;
import ua.com.ivolnov.ecg.notifier.WsNotifier;
import ua.com.ivolnov.ecg.patient.Patient;
import ua.com.ivolnov.ecg.patient.PatientService;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

/**
 * AI Model processes data first resulting in delayed ECG Chart update. We might want to change this in the future.
 */
@RequiredArgsConstructor
public class EcgSampleProcessor implements Consumer<EcgSourceSample> {

    private final EcgAiModel aiModel;
    private final EcgDataStorage storage;
    private final PatientService patientService;
    private final WsNotifier alertNotifier;
    private final WsNotifier ecgChartNotifier;

    @Override
    public void accept(final EcgSourceSample sample) {
        final boolean criticalSpike = aiModel.isCriticalSpike(sample);
        final EcgData ecgData = EcgData.of(sample, criticalSpike);
        storage.save(sample.getPatientId(), ecgData);
        final Patient patient = patientService.getPatient(sample.getPatientId()).orElseThrow();
        if (criticalSpike) {
            alertNotifier.notify(patient, ecgData);
        }
        ecgChartNotifier.notify(patient, ecgData);
    }

}
