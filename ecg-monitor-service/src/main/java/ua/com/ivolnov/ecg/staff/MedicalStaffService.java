package ua.com.ivolnov.ecg.staff;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import ua.com.ivolnov.ecg.common.UserRole;

@Service
@RequiredArgsConstructor
public class MedicalStaffService {

    private static final Set<MedicalStaff> STAFFS = new CopyOnWriteArraySet<>();

    private final InMemoryUserDetailsManager userDetailsManager;

    @PostConstruct
    public void initStubUser() {
        addStaff("johndoe", "1G@QGFzwG$q");
    }

    public Set<MedicalStaff> getAllStaff() {
        return Set.copyOf(STAFFS);
    }

    public void addStaff(final String username, final String password) {
        userDetailsManager.createUser(
                User.withUsername(username)
                        .password(password)
                        .roles(UserRole.STAFF)
                        .build()
        );
        STAFFS.add(new MedicalStaff(username, password));
    }

    public void removeStaff(final String username) {
        userDetailsManager.deleteUser(username);
        STAFFS.removeIf(staff -> staff.getUsername().equals(username));
    }

}
