package ua.com.ivolnov.ecg.staff;

import java.util.Set;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ua.com.ivolnov.ecg.common.UserRole;

@RequiredArgsConstructor
public class MedicalStaffService {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final Set<MedicalStaff> staffSet;

    @PostConstruct
    public void initStubUser() {
        addStaff("johndoe", "1G@QGFzwG$q");
    }

    public Set<MedicalStaff> getAllStaff() {
        return Set.copyOf(staffSet);
    }

    public void addStaff(final String username, final String password) {
        userDetailsManager.createUser(
                User.withUsername(username)
                        .password(password)
                        .roles(UserRole.STAFF)
                        .build()
        );
        staffSet.add(new MedicalStaff(username, password));
    }

    public void removeStaff(final String username) {
        userDetailsManager.deleteUser(username);
        staffSet.removeIf(staff -> staff.getUsername().equals(username));
    }

}
