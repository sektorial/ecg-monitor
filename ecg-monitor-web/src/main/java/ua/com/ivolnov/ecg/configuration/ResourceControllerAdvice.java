package ua.com.ivolnov.ecg.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
@RequiredArgsConstructor
public class ResourceControllerAdvice {

    private final ResourceUrlProvider resourceUrlProvider;

    @ModelAttribute("resources")
    public ResourceUrlProvider resources() {
        return this.resourceUrlProvider;
    }

}
