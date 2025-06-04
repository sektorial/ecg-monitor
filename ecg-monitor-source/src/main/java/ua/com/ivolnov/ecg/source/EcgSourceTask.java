package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.function.Consumer;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
class EcgSourceTask implements Runnable {

    EcgWaveGenerator ecgWaveGenerator;
    UUID patientId;
    Consumer<EcgSourceSample> consumer;

    @Override
    public void run() {
        final EcgSourceSample sample = ecgWaveGenerator.generateEcgSample(patientId);
        consumer.accept(sample);
    }

}
