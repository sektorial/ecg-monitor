package ua.com.ivolnov.ecg.data;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.System.currentTimeMillis;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EcgDataStorageTest {

    public static final UUID EXISTING_PATIENT_ID = UUID.randomUUID();
    public static final EcgData EXISTING_ECG_DATA = EcgData.of(currentTimeMillis(), 1, false);
    public static final int MAX_LIST_SIZE = 2;
    private static final Map<UUID, LinkedList<EcgData>> ECG_DATA_MAP = new ConcurrentHashMap<>();
    private EcgDataStorage ecgDataStorage;

    @BeforeEach
    void setUp() {
        ecgDataStorage = new EcgDataStorage(MAX_LIST_SIZE, ECG_DATA_MAP);
        ECG_DATA_MAP.clear();
        final LinkedList<EcgData> ecgDataList = new LinkedList<>();
        ecgDataList.add(EXISTING_ECG_DATA);
        ECG_DATA_MAP.put(EXISTING_PATIENT_ID, ecgDataList);
    }

    @Test
    void shouldSaveWithoutRemoval() {
        final long now = currentTimeMillis();
        final EcgData newEcgData = EcgData.of(now, 2.0, true);

        ecgDataStorage.save(EXISTING_PATIENT_ID, newEcgData);

        assertThat(ECG_DATA_MAP.get(EXISTING_PATIENT_ID))
                .containsExactly(EXISTING_ECG_DATA, newEcgData)
                .last().isEqualTo(newEcgData);
    }

    @Test
    void shouldSaveWithRemoval() {
        final EcgData newEcgData1 = EcgData.of(currentTimeMillis(), 2.0, true);
        final EcgData newEcgData2 = EcgData.of(currentTimeMillis(), 2.0, true);

        ecgDataStorage.save(EXISTING_PATIENT_ID, newEcgData1);
        ecgDataStorage.save(EXISTING_PATIENT_ID, newEcgData2);

        assertThat(ECG_DATA_MAP.get(EXISTING_PATIENT_ID))
                .containsExactly(newEcgData1, newEcgData2)
                .last().isEqualTo(newEcgData2);
    }

    @Test
    void shouldGetAllEcgDataForExistingPatient() {
        assertThat(ecgDataStorage.getAll(EXISTING_PATIENT_ID))
                .containsExactly(EXISTING_ECG_DATA);
    }

    @Test
    void shouldGetAllEcgDataForNonExistingPatient() {
        assertThat(ecgDataStorage.getAll(UUID.randomUUID()))
                .isEmpty();
    }

    @Test
    void shouldGetSince() {
        final long now = currentTimeMillis();
        final EcgData newEcgData = EcgData.of(now, 2.0, true);

        assertThat(ecgDataStorage.getSince(EXISTING_PATIENT_ID, now))
                .isEmpty();

        ECG_DATA_MAP.get(EXISTING_PATIENT_ID).addLast(newEcgData);

        assertThat(ecgDataStorage.getSince(EXISTING_PATIENT_ID, now))
                .containsExactly(newEcgData);
    }

}
