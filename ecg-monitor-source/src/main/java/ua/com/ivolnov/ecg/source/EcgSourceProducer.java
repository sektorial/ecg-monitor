package ua.com.ivolnov.ecg.source;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EcgSourceProducer {


    private final EcgSourceScheduledTaskFactory taskFactory;
    private final Map<UUID, ScheduledFuture<?>> tasks;

    public void initProducerForPatient(final UUID patientId, final Consumer<EcgSourceSample> consumer) {
        tasks.computeIfAbsent(patientId, id -> taskFactory.createTask(id, consumer));
    }

    public void disposeProducerForPatient(final UUID patientId) {
        final ScheduledFuture<?> task = tasks.remove(patientId);
        if (task != null) {
            task.cancel(true);
        }
    }

    @PreDestroy
    public void disposeAllProducers() {
        tasks.values().forEach(scheduledFuture -> scheduledFuture.cancel(true));
        tasks.clear();
    }

}
