package ua.com.ivolnov.ecg.staff;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static ua.com.ivolnov.ecg.common.UserRole.ADMIN;

@PreAuthorize("hasRole('" + ADMIN + "')")
@RequestMapping("/web/admin/staff")
@Controller
@RequiredArgsConstructor
public class AdminStaffViewController {

    private final MedicalStaffService staffService;

    @GetMapping
    public ModelAndView staffPage() {
        final ModelAndView mav = new ModelAndView("admin-staff");
        final Set<MedicalStaff> staff = staffService.getAllStaff();
        mav.getModel().put("msg", "Staff Management");
        mav.getModel().put("staff", staff);
        return mav;
    }

    @PostMapping("/add")
    public String addStaff(@RequestParam final String username, @RequestParam final String password) {
        staffService.addStaff(username, password);
        return "redirect:/web/admin/staff";
    }

    @PostMapping("/remove")
    public String removeStaff(@RequestParam final String username) {
        staffService.removeStaff(username);
        return "redirect:/web/admin/staff";
    }

}
