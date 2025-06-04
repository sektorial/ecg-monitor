package ua.com.ivolnov.ecg;

import java.util.Set;
import java.util.UUID;

import ua.com.ivolnov.ecg.patient.Patient;

final class SharedData {

    private Set<Patient> patients;

    String getAnyPatientIdAsString() {
        if (patients == null || patients.isEmpty()) {
            throw new IllegalStateException("Patients are not set");
        }
        return patients.stream().findAny()
                .map(Patient::getId)
                .map(UUID::toString)
                .orElseThrow();
    }

    void setPatients(final Set<Patient> patients) {
        if (this.patients != null) {
            throw new IllegalStateException("Patients are already set");
        }
        this.patients = patients;
    }

}
