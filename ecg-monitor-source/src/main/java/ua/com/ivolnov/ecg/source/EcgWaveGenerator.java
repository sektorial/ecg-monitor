package ua.com.ivolnov.ecg.source;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class EcgWaveGenerator {

    private static final double HEART_RATE_BPM = 72;
    private static final double HEART_RATE_SECONDS_PER_BEAT = 60.0 / HEART_RATE_BPM;
    private static final int CRITICAL_ECG_SPIKE_INTERVAL_MILLIS = 10_000;
    private static final Map<UUID, AtomicLong> SPIKES_MILLIS = new ConcurrentHashMap<>();

    EcgSourceSample generateEcgValue(final UUID patientId) {
        final long currentTimeMillis = System.currentTimeMillis();
        double valueWithNoise = enrichValueWithNoise(generatePlainEcgValue(currentTimeMillis));
        if (shouldInjectCriticalSpike(patientId, currentTimeMillis)) {
            final AtomicLong criticalSpikeMillis = SPIKES_MILLIS.get(patientId);
            if (criticalSpikeMillis == null) {
                SPIKES_MILLIS.put(patientId, new AtomicLong(currentTimeMillis));
            } else {
                criticalSpikeMillis.set(currentTimeMillis);
            }
            return EcgSourceSample.of(bound(transformValueToSpike(valueWithNoise)));
        }
        return EcgSourceSample.of(bound(valueWithNoise));
    }

    private double bound(double value) {
        return Math.max(-2.0, Math.min(2.0, value));
    }

    private double transformValueToSpike(final double value) {
        double sign = Math.random() > 0.5 ? 1 : -1;
        double magnitude = 1.0 + Math.random(); // 1.5 to 2.0
        return value + sign * magnitude;
    }

    private double enrichValueWithNoise(final double value) {
        return value + (Math.random() - 0.5) * 0.03;
    }

    private static double generatePlainEcgValue(final long currentTimeMillis) {
        final double secondsSinceEpoch = (currentTimeMillis % (long) (HEART_RATE_SECONDS_PER_BEAT * 1000)) / 1000.0;
        // "Phases" for P, Q, R, S, T waves (in seconds, relative to beat start)
        final double pWave = gaussianPulse(secondsSinceEpoch, 0.1 * HEART_RATE_SECONDS_PER_BEAT,
                0.03 * HEART_RATE_SECONDS_PER_BEAT, 0.1);
        final double qWave = gaussianPulse(secondsSinceEpoch, 0.2 * HEART_RATE_SECONDS_PER_BEAT,
                0.016 * HEART_RATE_SECONDS_PER_BEAT, -0.15);
        final double rWave = gaussianPulse(secondsSinceEpoch, 0.22 * HEART_RATE_SECONDS_PER_BEAT,
                0.012 * HEART_RATE_SECONDS_PER_BEAT, 1.0);
        final double sWave = gaussianPulse(secondsSinceEpoch, 0.24 * HEART_RATE_SECONDS_PER_BEAT,
                0.014 * HEART_RATE_SECONDS_PER_BEAT, -0.25);
        final double tWave = gaussianPulse(secondsSinceEpoch, 0.35 * HEART_RATE_SECONDS_PER_BEAT,
                0.05 * HEART_RATE_SECONDS_PER_BEAT, 0.35);
        return pWave + qWave + rWave + sWave + tWave;
    }

    private static boolean shouldInjectCriticalSpike(final UUID id, final long currentTimeMillis) {
        final AtomicLong criticalSpikeMillis = SPIKES_MILLIS.get(id);
        if (criticalSpikeMillis == null) {
            return true;
        }
        return (currentTimeMillis - criticalSpikeMillis.get()) > CRITICAL_ECG_SPIKE_INTERVAL_MILLIS;
    }

    private static double gaussianPulse(double secondsSinceEpoch, double center, double width, double amplitude) {
        final double x = (secondsSinceEpoch - center) / width;
        return amplitude * Math.exp(-x * x / 2.0);
    }

}
