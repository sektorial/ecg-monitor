package ua.com.ivolnov.ecg.source;

import java.util.UUID;

import lombok.Value;

@Value(staticConstructor = "of")
public class EcgSourceSample {

    UUID patientId;
    long timestampMillis = System.currentTimeMillis();
    double voltage;

}
