package ua.com.ivolnov.ecg.patient;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.ivolnov.ecg.source.EcgSourceProducer;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final Map<UUID, Patient> patients = new ConcurrentHashMap<>();

    private final EcgSourceProducer ecgSourceProducer;
    private final EcgSourceConsumer ecgSourceConsumer;

    @PostConstruct
    public void initStubPatient() {
        addPatient("patient #1");
    }

    public Set<Patient> getAllPatients() {
        return Set.copyOf(patients.values());
    }

    public void addPatient(final String name) {
        final UUID id = UUID.randomUUID();
        patients.put(id, new Patient(id, name));
        ecgSourceProducer.scheduleForPatient(id, sample -> ecgSourceConsumer.acceptSample(id, sample));
    }

    public void removePatient(final UUID id) {
        patients.remove(id);
        ecgSourceProducer.unscheduleForPatient(id);
    }
}
