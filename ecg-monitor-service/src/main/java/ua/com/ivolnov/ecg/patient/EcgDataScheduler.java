package ua.com.ivolnov.ecg.patient;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
@RequiredArgsConstructor
public class EcgDataScheduler {

    private static final int ECG_DATA_INTERVAL_MILLIS = 40;
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(4);
    private static final Map<UUID, ScheduledFuture<?>> TASKS = new ConcurrentHashMap<>();


    private final SimpMessagingTemplate messagingTemplate;
    private final EcgWaveGenerator ecgWaveGenerator;

    public void scheduleForPatient(final UUID patientId) {
        TASKS.computeIfAbsent(patientId, id -> SCHEDULER.scheduleAtFixedRate(() -> {
            double value = ecgWaveGenerator.generateEcgValue(id);
            messagingTemplate.convertAndSend("/topic/patient/" + id, new EcgData(value));
        }, 0, ECG_DATA_INTERVAL_MILLIS, MILLISECONDS));
    }

    public void unscheduleForPatient(final UUID patientId) {
        ScheduledFuture<?> future = TASKS.remove(patientId);
        if (future != null) {
            future.cancel(true);
        }
    }

    @PreDestroy
    public void unscheduleAll() {
        TASKS.values().forEach(f -> f.cancel(true));
        TASKS.clear();
    }

    @Value
    private static class EcgData {
        @JsonProperty("ts_millis")
        long timestamp = System.currentTimeMillis();
        @JsonProperty("voltage")
        double value;
    }

}

