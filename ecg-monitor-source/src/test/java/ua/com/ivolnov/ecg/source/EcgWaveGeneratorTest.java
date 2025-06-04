package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

class EcgWaveGeneratorTest {

    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final EcgWaveGenerator ECG_WAVE_GENERATOR = new EcgWaveGenerator(new ConcurrentHashMap<>());

    @RepeatedTest(1000)
    void shouldGenerateEcgSample() {
        final EcgSourceSample sample = ECG_WAVE_GENERATOR.generateEcgSample(PATIENT_ID);
        final long now = System.currentTimeMillis();

        assertThat(sample.getPatientId()).isEqualTo(PATIENT_ID);
        assertThat(sample.getVoltage()).isBetween(-2.0, 2.0);
        assertThat(sample.getTimestampMillis()).isBetween(now - 1000, now + 1000);
    }

}
