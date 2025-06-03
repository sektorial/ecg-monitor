package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

@Component
@RequiredArgsConstructor
class EcgSourceConsumer {

    private final EcgSampleProcessor processor;

    public void acceptSample(final UUID patientId, final EcgSourceSample sample) {
        processor.process(patientId, sample);
    }

}
