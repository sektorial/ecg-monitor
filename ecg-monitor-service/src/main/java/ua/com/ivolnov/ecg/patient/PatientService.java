package ua.com.ivolnov.ecg.patient;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final Set<Patient> patients = new CopyOnWriteArraySet<>();

    public Set<Patient> getAllPatients() {
        return patients;
    }

    public void addPatient(final String name) {
        patients.add(new Patient(UUID.randomUUID(), name));
    }

    public void removePatient(final UUID id) {
        patients.removeIf(patient -> patient.getId().equals(id));
    }
}
