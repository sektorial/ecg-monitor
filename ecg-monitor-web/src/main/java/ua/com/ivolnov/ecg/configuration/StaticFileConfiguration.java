package ua.com.ivolnov.ecg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class StaticFileConfiguration implements WebMvcConfigurer {

    @Bean
    public ResourceUrlProvider resourceUrlProvider() {
        return new ResourceUrlProvider();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new VersionResourceResolver()
                        .addContentVersionStrategy("/**"));
    }

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/login")
                .setViewName("login");
    }


}
