package ua.com.ivolnov.ecg.source;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class EcgSourceConfiguration {

    @Bean
    EcgWaveGenerator ecgWaveGenerator() {
        return new EcgWaveGenerator(new ConcurrentHashMap<>());
    }

    @Bean
    EcgSourceScheduledTaskFactory ecgSourceScheduledTaskFactory(final EcgWaveGenerator ecgWaveGenerator) {
        return new EcgSourceScheduledTaskFactory(ecgWaveGenerator, Executors.newScheduledThreadPool(4));
    }


    @Bean
    EcgSourceProducer ecgSourceProducer(final EcgSourceScheduledTaskFactory taskFactory) {
        return new EcgSourceProducer(taskFactory, new ConcurrentHashMap<>());
    }

}
