package ua.com.ivolnov.ecg.staff;

import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
class MedicalStaffConfiguration {

    @Bean
    MedicalStaffService medicalStaffService(final InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        return new MedicalStaffService(inMemoryUserDetailsManager, new CopyOnWriteArraySet<>());
    }

}
