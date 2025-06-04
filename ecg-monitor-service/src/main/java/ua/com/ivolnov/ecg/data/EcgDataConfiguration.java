package ua.com.ivolnov.ecg.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EcgDataConfiguration {

    @Bean
    EcgDataStorage ecgDataStorage() {
        final int maxListSize = 3600 * 25; // 1 hour at 25Hz
        final HashMap<UUID, LinkedList<EcgData>> patientIdToDataList = new HashMap<>();
        return new EcgDataStorage(maxListSize, patientIdToDataList);
    }

}
