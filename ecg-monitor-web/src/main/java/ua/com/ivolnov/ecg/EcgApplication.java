package ua.com.ivolnov.ecg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "ua.com.ivolnov.ecg")
public class EcgApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EcgApplication.class, args);
    }

}
