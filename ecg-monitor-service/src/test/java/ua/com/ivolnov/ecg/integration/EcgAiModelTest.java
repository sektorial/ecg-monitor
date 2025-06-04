package ua.com.ivolnov.ecg.integration;

import java.util.UUID;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EcgAiModelTest {

    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final EcgAiModel ECG_AI_MODEL = new EcgAiModel(2.0);

    @ValueSource(doubles = {2.01, 3.0, -2.01, -3.0})
    @ParameterizedTest
    void shouldTellCriticalSpike(final double value) {
        assertThat(ECG_AI_MODEL.isCriticalSpike(EcgSourceSample.of(PATIENT_ID, value)))
                .isTrue();
    }

    @ValueSource(doubles = {2.00, 1.0, 0.0, -1.0, -2.0})
    @ParameterizedTest
    void shouldTellNonCriticalSpike(final double value) {
        assertThat(ECG_AI_MODEL.isCriticalSpike(EcgSourceSample.of(PATIENT_ID, value)))
                .isFalse();
    }

}
