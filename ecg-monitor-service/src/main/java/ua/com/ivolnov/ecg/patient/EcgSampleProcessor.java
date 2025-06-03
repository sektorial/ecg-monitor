package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

@Component
@RequiredArgsConstructor
class EcgSampleProcessor {

    private final EcgAiModel aiModel;
    private final EcgDataStorage storage;
    private final WsNotifier alertNotifier;
    private final WsNotifier ecgChartNotifier;
    private final PatientService patientService;

    /**
     * AI Model processes data first resulting in delayed ECG Chart update. We might want to change this in the future.
     *
     * @param patientId The patient identifier
     * @param sample    The {@link EcgSourceSample}
     */
    public void process(final UUID patientId, final EcgSourceSample sample) {
        final boolean criticalSpike = aiModel.isCriticalSpike(sample);
        final EcgData ecgData = EcgData.of(sample, criticalSpike);
        storage.save(patientId, ecgData);
        final Patient patient = patientService.getPatient(patientId).orElseThrow();
        if (criticalSpike) {
            alertNotifier.notify(patient, ecgData);
        }
        ecgChartNotifier.notify(patient, ecgData);
    }

}
