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

    /**
     * AI Model processes data first resulting in delayed ECG Chart update. We might want to change this in future.
     *
     * @param patientId The patient identifier
     * @param sample    The {@link EcgSourceSample}
     */
    public void process(final UUID patientId, final EcgSourceSample sample) {
        final boolean criticalSpike = aiModel.isCriticalSpike(sample);
        final EcgData ecgData = EcgData.of(sample, criticalSpike);
        storage.save(patientId, ecgData);
        if (criticalSpike) {
            alertNotifier.notify(patientId, ecgData);
        }
        ecgChartNotifier.notify(patientId, ecgData);
    }

}
