package ua.com.ivolnov.ecg.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ua.com.ivolnov.ecg.patient.Patient;
import ua.com.ivolnov.ecg.processor.EcgSampleProcessor;
import ua.com.ivolnov.ecg.source.EcgSourceProducer;

@RequiredArgsConstructor
public class EcgSourceConnector {

    private final EcgSourceProducer ecgSourceProducer;
    private final EcgSampleProcessor ecgSampleProcessor;

    public void addPatientSource(final Patient patient) {
        ecgSourceProducer.initProducerForPatient(patient.getId(), ecgSampleProcessor);
    }

    public void removePatientSource(@NonNull final Patient patient) {
        ecgSourceProducer.disposeProducerForPatient(patient.getId());
    }

}
