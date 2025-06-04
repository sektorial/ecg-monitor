package ua.com.ivolnov.ecg.integration;

import lombok.RequiredArgsConstructor;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

/**
 * Current version is the simplest stub for POC. In real-world scenario AI Model should  the shape and evolution of
 * the waveform over time, not by an individual sample
 */
@RequiredArgsConstructor
public class EcgAiModel {

    private final double spikeThreshold;

    public boolean isCriticalSpike(final EcgSourceSample sample) {
        return Math.abs(sample.getVoltage()) > spikeThreshold;
    }

}
