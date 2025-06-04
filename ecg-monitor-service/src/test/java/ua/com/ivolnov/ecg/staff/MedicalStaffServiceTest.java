package ua.com.ivolnov.ecg.staff;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ua.com.ivolnov.ecg.common.UserRole;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalStaffServiceTest {

    private static final String USERNAME = UUID.randomUUID().toString();
    private static final String PASSWORD = UUID.randomUUID().toString();

    @InjectMocks
    private MedicalStaffService medicalStaffService;
    @Mock
    private InMemoryUserDetailsManager userDetailsManager;
    @Mock
    private Set<MedicalStaff> staffSet;

    @BeforeEach
    void setUp() {
        medicalStaffService = new MedicalStaffService(userDetailsManager, staffSet);
    }

    @Test
    void shouldAddStaff() {
        final UserDetails userDetails = User.withUsername(USERNAME)
                .password(PASSWORD)
                .roles(UserRole.STAFF)
                .build();
        when(staffSet.add(new MedicalStaff(USERNAME, PASSWORD))).thenReturn(true);

        doNothing().when(userDetailsManager).createUser(userDetails);

        medicalStaffService.addStaff(USERNAME, PASSWORD);

        verifyNoMoreInteractions(staffSet, userDetailsManager);
    }

    @Test
    void shouldRemoveStaff() {
        doNothing().when(userDetailsManager).deleteUser(USERNAME);
        when(staffSet.removeIf(any(Predicate.class))).thenReturn(true);

        medicalStaffService.removeStaff(USERNAME);

        verifyNoMoreInteractions(staffSet, userDetailsManager);
    }

}
