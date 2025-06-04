package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequiredArgsConstructor
class EcgSourceScheduledTaskFactory {

    private static final int INITIAL_DELAY = 0;
    private static final int ECG_DATA_INTERVAL_MILLIS = 40;

    private final EcgWaveGenerator ecgWaveGenerator;
    private final ScheduledExecutorService scheduler;

    ScheduledFuture<?> createTask(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        return scheduler.scheduleAtFixedRate(() -> {
                    final EcgSourceSample sample = ecgWaveGenerator.generateEcgValue(patientId);
                    consumer.accept(sample);
                },
                INITIAL_DELAY,
                ECG_DATA_INTERVAL_MILLIS,
                MILLISECONDS);
    }


}
