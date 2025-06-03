package ua.com.ivolnov.ecg.patient;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class EcgDataStorage {

    private static final int MAX_SIZE = 3600 * 25; // 1 hour at 25Hz
    private static final Map<UUID, List<EcgData>> DATA = new HashMap<>();

    synchronized void save(final UUID patientId, final EcgData ecgData) {
        final List<EcgData> list = DATA.computeIfAbsent(patientId, k -> new LinkedList<>());
        list.add(ecgData);
        if (list.size() > MAX_SIZE) {
            list.remove(0);
        }
    }

    synchronized List<EcgData> get(final UUID patientId) {
        return DATA.getOrDefault(patientId, List.of());
    }
}

