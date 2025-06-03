package ua.com.ivolnov.ecg.source;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EcgSourceProducer {

    private static final Map<UUID, ScheduledFuture<?>> TASKS = new ConcurrentHashMap<>();

    private final EcgSourceScheduledTaskFactory taskFactory;

    public void initProducerForPatient(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        TASKS.computeIfAbsent(patientId, id -> taskFactory.createTask(id, consumer));
    }

    public void disposeProducerForPatient(final UUID patientId) {
        final ScheduledFuture<?> task = TASKS.remove(patientId);
        if (task != null) {
            task.cancel(true);
        }
    }

    @PreDestroy
    public void disposeAllProducers() {
        TASKS.values().forEach(scheduledFuture -> scheduledFuture.cancel(true));
        TASKS.clear();
    }

}
