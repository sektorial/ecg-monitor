package ua.com.ivolnov.ecg.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EcgDataStorage {

    private final int maxListSize;
    private final Map<UUID, LinkedList<EcgData>> patientIdToDataList;

    public synchronized void save(final UUID patientId, final EcgData ecgData) {
        final LinkedList<EcgData> list = patientIdToDataList.computeIfAbsent(patientId, k -> new LinkedList<>());
        list.addLast(ecgData);
        if (list.size() > maxListSize) {
            list.removeFirst();
        }
    }

    synchronized List<EcgData> getAll(final UUID patientId) {
        final LinkedList<EcgData> list = patientIdToDataList.get(patientId);
        if (list == null) {
            return List.of();
        }
        return List.copyOf(list);
    }

    synchronized List<EcgData> getSince(final UUID patientId, long fromMillis) {
        final LinkedList<EcgData> list = patientIdToDataList.get(patientId);
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
