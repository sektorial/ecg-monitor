package ua.com.ivolnov.ecg.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.source.EcgSourceProducer;

@Component
@RequiredArgsConstructor
class EcgSourceStub {

    private final EcgSourceProducer ecgSourceProducer;
    private final EcgSourceConsumer ecgSourceConsumer;

    public void addPatientSource(final Patient patient) {
        ecgSourceProducer.initProducerForPatient(patient.getId(),
                sample -> ecgSourceConsumer.acceptSample(patient.getId(), sample));
    }

    public void removePatientSource(@NonNull final Patient patient) {
        ecgSourceProducer.disposeProducerForPatient(patient.getId());
    }

}
