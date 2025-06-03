package ua.com.ivolnov.ecg.source;

import lombok.Value;

@Value(staticConstructor = "of")
public class EcgSourceSample {

    long timestampMillis = System.currentTimeMillis();
    double voltage;

}
