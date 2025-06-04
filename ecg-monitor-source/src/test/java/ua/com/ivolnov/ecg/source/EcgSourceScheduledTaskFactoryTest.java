package ua.com.ivolnov.ecg.source;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThatObject;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EcgSourceScheduledTaskFactoryTest {

    private static final int INITIAL_DELAY = 0;
    private static final int ECG_DATA_INTERVAL_MILLIS = 40;
    private static final ScheduledFuture<?> TASK = Mockito.mock(ScheduledFuture.class);
    private static final UUID PATIENT_ID = UUID.randomUUID();
    private static final Consumer<EcgSourceSample> CONSUMER = sample -> {};

    private EcgSourceScheduledTaskFactory factory;
    @Mock
    private EcgWaveGenerator ecgWaveGenerator;
    @Mock
    private ScheduledExecutorService scheduler;

    @BeforeEach
    void setUp() {
        factory = new EcgSourceScheduledTaskFactory(ecgWaveGenerator, scheduler, INITIAL_DELAY,
                ECG_DATA_INTERVAL_MILLIS);
    }

    @Test
    void shouldScheduleTask() {
        final EcgSourceTask ecgSourceTask = EcgSourceTask.builder()
                .ecgWaveGenerator(ecgWaveGenerator)
                .patientId(PATIENT_ID)
                .consumer(CONSUMER)
                .build();

        when(scheduler.scheduleAtFixedRate(ecgSourceTask, INITIAL_DELAY, ECG_DATA_INTERVAL_MILLIS, MILLISECONDS))
                .thenAnswer(invocationOnMock -> TASK);

        assertThatObject(factory.scheduleTask(PATIENT_ID, CONSUMER)).isEqualTo(TASK);

        verifyNoMoreInteractions(scheduler);
        verifyNoInteractions(ecgWaveGenerator);
    }

}
