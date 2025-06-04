package ua.com.ivolnov.ecg.source;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsSecondArg;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EcgSourceProducerTest {

    private static final Consumer<EcgSourceSample> CONSUMER = (sample) -> {};
    private static final UUID PATIENT_ID = UUID.randomUUID();

    @Mock
    public ScheduledFuture<?> task;
    @InjectMocks
    private EcgSourceProducer ecgSourceProducer;
    @Mock
    private EcgSourceScheduledTaskFactory taskFactory;
    @Mock
    private Map<UUID, ScheduledFuture<?>> tasks;

    @Test
    void shouldInitProducerForPatient() {
        when(tasks.containsKey(PATIENT_ID)).thenReturn(false);
        when(taskFactory.scheduleTask(PATIENT_ID, CONSUMER)).thenAnswer(invocation -> task);
        doAnswer(returnsSecondArg()).when(tasks).put(PATIENT_ID, task);

        ecgSourceProducer.initProducerForPatient(PATIENT_ID, CONSUMER);

        verifyNoMoreInteractions(taskFactory, tasks);
        verifyNoInteractions(task);
    }

    @Test
    void shouldFailToInitProducerForPatient() {
        when(tasks.containsKey(PATIENT_ID)).thenReturn(true);

        assertThatThrownBy(() -> ecgSourceProducer.initProducerForPatient(PATIENT_ID, CONSUMER))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task has already been created for patient: " + PATIENT_ID);

        verifyNoMoreInteractions(tasks);
        verifyNoInteractions(taskFactory);
        verifyNoInteractions(task);
    }

    @Test
    void shouldDisposeProducerForPatientIfTaskExists() {
        when(tasks.remove(PATIENT_ID)).thenAnswer(invocation -> task);
        when(task.cancel(true)).thenReturn(true);

        ecgSourceProducer.disposeProducerForPatient(PATIENT_ID);

        verifyNoMoreInteractions(tasks, task);
        verifyNoInteractions(taskFactory);
    }

    @Test
    void shouldDisposeProducerForPatientIfTaskDoesNotExist() {
        when(tasks.remove(PATIENT_ID)).thenReturn(null);

        ecgSourceProducer.disposeProducerForPatient(PATIENT_ID);

        verifyNoMoreInteractions(tasks);
        verifyNoInteractions(taskFactory, task);
    }

    @Test
    void shouldDisposeAllProducers() {
        when(tasks.values()).thenReturn(Set.of(task));
        when(task.cancel(true)).thenReturn(true);
        doNothing().when(tasks).clear();

        ecgSourceProducer.disposeAllProducers();

        verifyNoMoreInteractions(tasks, task);
        verifyNoInteractions(taskFactory);
    }

}
