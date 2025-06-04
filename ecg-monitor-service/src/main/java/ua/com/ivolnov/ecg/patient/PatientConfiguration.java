package ua.com.ivolnov.ecg.patient;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PatientConfiguration {

    @Bean
    PatientService patientService() {
        return new PatientService(new ConcurrentHashMap<>());
    }
}
