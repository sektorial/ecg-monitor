package ua.com.ivolnov.ecg.patient;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PatientService {

    private final Map<UUID, Patient> patients;

    public Set<Patient> getAllPatients() {
        return Set.copyOf(patients.values());
    }

    public Patient addPatient(final String name) {
        final UUID id = UUID.randomUUID();
        final Patient patient = new Patient(id, name);
        patients.put(patient.getId(), patient);
        return patient;
    }

    public Optional<Patient> removePatient(final UUID id) {
        return getPatient(id).stream()
                .peek(patient -> patients.remove(patient.getId()))
                .findFirst();
    }

    public Optional<Patient> getPatient(final UUID id) {
        return Optional.ofNullable(patients.get(id));
    }

}
