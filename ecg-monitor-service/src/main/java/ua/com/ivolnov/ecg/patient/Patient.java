package ua.com.ivolnov.ecg.patient;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Patient {
    private UUID id;
    private String name;
}
