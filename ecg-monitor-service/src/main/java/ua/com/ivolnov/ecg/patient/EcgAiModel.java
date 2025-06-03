package ua.com.ivolnov.ecg.patient;

import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

/**
 * Current version is the simplest stub for POC. In real-world scenario AI Model should  the shape and evolution of
 * the waveform over time, not by an individual sample
 */
@Component
class EcgAiModel {

    private static final double SPIKE_THRESHOLD = 1.5;

    public boolean isCriticalSpike(final EcgSourceSample sample) {
        return Math.abs(sample.getVoltage()) > SPIKE_THRESHOLD;
    }

}
