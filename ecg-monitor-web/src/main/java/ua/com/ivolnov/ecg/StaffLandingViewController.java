package ua.com.ivolnov.ecg;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.com.ivolnov.ecg.patient.PatientService;

@Controller
@RequestMapping("/web/staff")
@RequiredArgsConstructor
public class StaffLandingViewController {

    private final PatientService patientService;

    @GetMapping
    public ModelAndView getHelloWorld() {
        ModelAndView modelAndView = new ModelAndView("staff-landing");
        modelAndView.addObject("msg", "Hello Staff");
        modelAndView.addObject("patients", patientService.getAllPatients());
        return modelAndView;
    }
}

