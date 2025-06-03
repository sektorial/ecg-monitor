package ua.com.ivolnov.ecg.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
class EcgDataStorage {

    private static final int MAX_SIZE = 3600 * 25; // 1 hour at 25Hz
    private static final Map<UUID, LinkedList<EcgData>> DATA = new HashMap<>();

    synchronized void save(final UUID patientId, final EcgData ecgData) {
        final LinkedList<EcgData> list = DATA.computeIfAbsent(patientId, k -> new LinkedList<>());
        list.addLast(ecgData);
        if (list.size() > MAX_SIZE) {
            list.removeFirst();
        }
    }

    synchronized List<EcgData> getAll(final UUID patientId) {
        final LinkedList<EcgData> list = DATA.get(patientId);
        if (list == null) {
            return List.of();
        }
        return List.copyOf(list);
    }

    synchronized List<EcgData> getSince(final UUID patientId, long fromMillis) {
        final LinkedList<EcgData> list = DATA.get(patientId);
        if (list == null) {
            return List.of();
        }
        final List<EcgData> recent = new ArrayList<>();
        for (final var data : list) {
            if (data.getTimestamp() >= fromMillis) {
                recent.add(data);
            }
        }
        return List.copyOf(recent);
    }

}
