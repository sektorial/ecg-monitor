package ua.com.ivolnov.ecg.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ua.com.ivolnov.ecg.source.EcgSourceSample;

@Value(staticConstructor = "of")
public class EcgData {

    @JsonProperty("ts_millis")
    long timestamp;
    @JsonProperty("voltage")
    double value;
    @JsonProperty("critical")
    boolean critical;

    public static EcgData of(final EcgSourceSample sample, final boolean critical) {
        return new EcgData(sample.getTimestampMillis(), sample.getVoltage(), critical);
    }

}
