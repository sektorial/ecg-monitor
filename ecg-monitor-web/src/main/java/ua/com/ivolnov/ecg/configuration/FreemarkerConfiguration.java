package ua.com.ivolnov.ecg.configuration;

import freemarker.template.TemplateModelException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class FreemarkerConfiguration {

    private final FreeMarkerConfigurer freeMarkerConfigurer;
    private final Boolean hrmEnabled;

    public FreemarkerConfiguration(final FreeMarkerConfigurer freeMarkerConfigurer,
                                   @Value("${ecg.development.hrm-enabled}") final Boolean hrmEnabled) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.hrmEnabled = hrmEnabled;
    }

    @PostConstruct
    public void configuration() throws TemplateModelException {
        freeMarkerConfigurer.getConfiguration()
                .setSharedVariable("hrmEnabled", hrmEnabled);
    }

}
