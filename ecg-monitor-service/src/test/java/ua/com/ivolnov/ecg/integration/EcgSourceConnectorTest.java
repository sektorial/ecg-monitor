package ua.com.ivolnov.ecg.integration;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.ivolnov.ecg.patient.Patient;
import ua.com.ivolnov.ecg.processor.EcgSampleProcessor;
import ua.com.ivolnov.ecg.source.EcgSourceProducer;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class EcgSourceConnectorTest {

    private static final UUID PATIENT_ID = UUID.randomUUID();

    @InjectMocks
    private EcgSourceConnector ecgSourceConnector;
    @Mock
    private EcgSourceProducer ecgSourceProducer;
    @Mock
    private EcgSampleProcessor ecgSampleProcessor;

    @Test
    void shouldAddPatientSource() {
        final Patient patient = new Patient(PATIENT_ID, "name");

        doNothing().when(ecgSourceProducer).initProducerForPatient(PATIENT_ID, ecgSampleProcessor);

        ecgSourceConnector.addPatientSource(patient);

        verifyNoMoreInteractions(ecgSourceProducer);
        verifyNoInteractions(ecgSampleProcessor);
    }

    @Test
    void shouldRemovePatientSource() {
        final Patient patient = new Patient(PATIENT_ID, "name");

        doNothing().when(ecgSourceProducer).disposeProducerForPatient(PATIENT_ID);

        ecgSourceConnector.removePatientSource(patient);

        verifyNoMoreInteractions(ecgSourceProducer);
        verifyNoInteractions(ecgSampleProcessor);
    }

}
