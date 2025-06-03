package ua.com.ivolnov.ecg.source;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
@Component
@RequiredArgsConstructor
public class EcgSourceProducer {

    private static final int ECG_DATA_INTERVAL_MILLIS = 40;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(4);
    private static final Map<UUID, ScheduledFuture<?>> TASKS = new ConcurrentHashMap<>();

    private final EcgWaveGenerator ecgWaveGenerator;

    public void scheduleForPatient(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        TASKS.computeIfAbsent(patientId,
                id -> SCHEDULER.scheduleAtFixedRate(() -> {
                            final EcgSourceSample sample = ecgWaveGenerator.generateEcgValue(id);
                            consumer.accept(sample);
                        },
                        0,
                        ECG_DATA_INTERVAL_MILLIS,
                        MILLISECONDS));
    }

    public void unscheduleForPatient(final UUID patientId) {
        final ScheduledFuture<?> task = TASKS.remove(patientId);
        if (task != null) {
            task.cancel(true);
        }
    }

    @PreDestroy
    public void unscheduleAll() {
        TASKS.values().forEach(f -> f.cancel(true));
        TASKS.clear();
    }

}
