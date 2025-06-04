package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@RequiredArgsConstructor
class EcgSourceScheduledTaskFactory {

    private final EcgWaveGenerator ecgWaveGenerator;
    private final ScheduledExecutorService scheduler;
    private final int initialDelay;
    private final int ecgDataIntervalMillis;

    ScheduledFuture<?> scheduleTask(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        final EcgSourceTask ecgSourceTask = EcgSourceTask.builder()
                .ecgWaveGenerator(ecgWaveGenerator)
                .patientId(patientId)
                .consumer(consumer)
                .build();
        return scheduler.scheduleAtFixedRate(ecgSourceTask,
                initialDelay,
                ecgDataIntervalMillis,
                MILLISECONDS);
    }


}
