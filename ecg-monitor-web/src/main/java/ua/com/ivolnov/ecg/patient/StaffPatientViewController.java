package ua.com.ivolnov.ecg.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static ua.com.ivolnov.ecg.common.UserRole.STAFF;

@PreAuthorize("hasRole('" + STAFF + "')")
@Controller
@RequestMapping("/web/staff/patient")
@RequiredArgsConstructor
public class StaffPatientViewController {

    @GetMapping("/{id}")
    public ModelAndView getPatient(@PathVariable("id") final String id) {
        final ModelAndView view = new ModelAndView("staff-patient");
        view.addObject("patientId", id);
        return view;
    }

}
