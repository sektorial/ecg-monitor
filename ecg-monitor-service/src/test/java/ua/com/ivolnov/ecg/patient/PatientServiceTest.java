package ua.com.ivolnov.ecg.patient;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    private static final UUID EXISTING_PATIENT_ID = UUID.randomUUID();
    private static final String EXISTING_PATIENT_NAME = "John Doe";

    @InjectMocks
    private PatientService patientService;
    @Spy
    private ConcurrentHashMap<UUID, Patient> patients = new ConcurrentHashMap<>();

    @BeforeEach
    void setUp() {
        patients.clear();
        patients.put(EXISTING_PATIENT_ID, new Patient(EXISTING_PATIENT_ID, EXISTING_PATIENT_NAME));
    }

    @Test
    void shouldAddPatient() {
        final String patientName = EXISTING_PATIENT_NAME;

        final Patient patient = patientService.addPatient(patientName);

        assertThat(patient)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("name", patientName);
        assertThat(patients)
                .hasSize(2)
                .containsEntry(patient.getId(), patient);
    }

    @Test
    void shouldRemovePatient() {
        final Optional<Patient> removedPatient = patientService.removePatient(EXISTING_PATIENT_ID);

        assertThat(removedPatient)
                .contains(new Patient(EXISTING_PATIENT_ID, EXISTING_PATIENT_NAME));
        assertThat(patients)
                .isEmpty();
    }

    @Test
    void shouldGetPatient() {
        final Patient expectedPatient = new Patient(EXISTING_PATIENT_ID, EXISTING_PATIENT_NAME);

        final Optional<Patient> patient = patientService.getPatient(EXISTING_PATIENT_ID);

        assertThat(patient)
                .contains(expectedPatient);
        assertThat(patients)
                .containsEntry(EXISTING_PATIENT_ID, expectedPatient);
    }

    @Test
    void shouldGetAllPatientsUnmodifiableCopy() {
        final Patient expectedPatient = new Patient(EXISTING_PATIENT_ID, EXISTING_PATIENT_NAME);

        final Set<Patient> allPatients = patientService.getAllPatients();

        assertThat(allPatients)
                .containsExactly(expectedPatient);
        assertThatThrownBy(allPatients::clear)
                .isExactlyInstanceOf(UnsupportedOperationException.class);
    }

}
