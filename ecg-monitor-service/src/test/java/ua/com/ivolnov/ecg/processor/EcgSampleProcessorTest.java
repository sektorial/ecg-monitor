package ua.com.ivolnov.ecg.processor;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.ivolnov.ecg.data.EcgData;
import ua.com.ivolnov.ecg.data.EcgDataStorage;
import ua.com.ivolnov.ecg.integration.EcgAiModel;
import ua.com.ivolnov.ecg.notifier.WsNotifier;
import ua.com.ivolnov.ecg.patient.Patient;
import ua.com.ivolnov.ecg.patient.PatientService;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EcgSampleProcessorTest {

    private static final String TIMESTAMP = "timestamp";
    private static final boolean CRITICAL_SPIKE = true;
    private static final UUID PATIENT_ID = UUID.randomUUID();

    @InjectMocks
    private EcgSampleProcessor ecgSampleProcessor;
    @Mock
    private EcgAiModel aiModel;
    @Mock
    private EcgDataStorage storage;
    @Mock
    private PatientService patientService;
    @Mock
    private WsNotifier notifier;
    @Captor
    private ArgumentCaptor<EcgSourceSample> sampleCaptor;
    @Captor
    private ArgumentCaptor<EcgData> ecgDataCaptor;

    @Test
    void shouldAccept() {
        final Patient patient = new Patient(PATIENT_ID, "John Doe");
        final EcgSourceSample expectedSample = EcgSourceSample.of(PATIENT_ID, 1.3);
        final EcgData expectedEcgData = EcgData.of(expectedSample, CRITICAL_SPIKE);

        when(aiModel.isCriticalSpike(sampleCaptor.capture())).thenReturn(CRITICAL_SPIKE);
        doNothing().when(storage).save(eq(PATIENT_ID), ecgDataCaptor.capture());
        when(patientService.getPatient(PATIENT_ID)).thenReturn(Optional.of(patient));
        doNothing().when(notifier).notify(eq(patient), ecgDataCaptor.capture());

        ecgSampleProcessor.accept(expectedSample);

        assertThat(sampleCaptor.getValue())
                .usingRecursiveComparison()
                .ignoringFields(TIMESTAMP)
                .isEqualTo(expectedSample);
        assertThat(ecgDataCaptor.getAllValues())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields(TIMESTAMP)
                .containsOnly(expectedEcgData);
        verifyNoMoreInteractions(aiModel, storage, patientService, notifier);
    }

}
