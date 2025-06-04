package ua.com.ivolnov.ecg.configuration;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ua.com.ivolnov.ecg.configuration.authentication.DefaultAuthenticationSuccessHandler;
import ua.com.ivolnov.ecg.configuration.authentication.SecurityConfiguration;

import static ua.com.ivolnov.ecg.common.UserRole.ADMIN;
import static ua.com.ivolnov.ecg.common.UserRole.STAFF;

@TestConfiguration
public class WebSecurityTestConfig extends SecurityConfiguration {

    public static final String STAFF_NAME = "John Doe";
    public static final String ADMIN_NAME = "Amdinistrator Name";
    private static final String STAFF_PASSWORD = UUID.randomUUID().toString();
    private static final String ADMIN_PASSWORD = UUID.randomUUID().toString();
    private static final Map<String, UserDetails> TEST_USERS
            = Stream.of(User.withUsername(STAFF_NAME)
                            .password(STAFF_PASSWORD)
                            .roles(STAFF)
                            .build(),
                    User.withUsername(ADMIN_NAME)
                            .password(ADMIN_PASSWORD)
                            .roles(ADMIN)
                            .build())
            .collect(Collectors.toMap(UserDetails::getUsername, Function.identity()));

    WebSecurityTestConfig() {
        super(new DefaultAuthenticationSuccessHandler());
    }

    @Bean
    @Override
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager() {
            @Override
            public void createUser(UserDetails user) {
                throw new UnsupportedOperationException("Not supported");
            }

            @Override
            public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
                return TEST_USERS.get(username);
            }
        };
    }
}
