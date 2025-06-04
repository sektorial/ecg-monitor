package ua.com.ivolnov.ecg.notifier;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class AlertMessage {

    @JsonProperty("patient_id")
    UUID patientId;
    @JsonProperty("patient_name")
    String patientName;
    @JsonProperty("ts_millis")
    long timestamp;

}
