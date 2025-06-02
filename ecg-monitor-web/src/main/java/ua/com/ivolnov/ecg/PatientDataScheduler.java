package ua.com.ivolnov.ecg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.com.ivolnov.ecg.patient.PatientService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PatientDataScheduler {

    private final PatientService patientService;
    private final SimpMessagingTemplate messagingTemplate;

    @Scheduled(fixedRate = 1_000)
    public void sendPatientUpdate() {
        for (final var patient : patientService.getAllPatients()) {
            log.info("Updating patient data..");
            String id = patient.getId().toString();
            String update = "ECG data for " + patient.getName() + ": " + System.currentTimeMillis();
            messagingTemplate.convertAndSend("/topic/patient/" + id, update);
        }
    }

}
