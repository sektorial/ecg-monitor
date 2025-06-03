package ua.com.ivolnov.ecg.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

@Value(staticConstructor = "of")
class EcgData {

    @JsonProperty("ts_millis")
    long timestamp;
    @JsonProperty("voltage")
    double value;
    @JsonProperty("critical")
    boolean critical;

    static EcgData of(final EcgSourceSample sample, final boolean critical) {
        return new EcgData(sample.getTimestampMillis(), sample.getVoltage(), critical);
    }

}
