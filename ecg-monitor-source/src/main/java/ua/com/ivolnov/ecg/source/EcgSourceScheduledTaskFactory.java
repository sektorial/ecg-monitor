package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
@RequiredArgsConstructor
class EcgSourceScheduledTaskFactory {

    private static final int INITIAL_DELAY = 0;
    private static final int ECG_DATA_INTERVAL_MILLIS = 40;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(4);

    private final EcgWaveGenerator ecgWaveGenerator;

    ScheduledFuture<?> createTask(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        return SCHEDULER.scheduleAtFixedRate(() -> {
                    final EcgSourceSample sample = ecgWaveGenerator.generateEcgValue(patientId);
                    consumer.accept(sample);
                },
                INITIAL_DELAY,
                ECG_DATA_INTERVAL_MILLIS,
                MILLISECONDS);
    }


}
