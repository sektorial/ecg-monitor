package ua.com.ivolnov.ecg.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web/admin/patient")
@Controller
public class AdminPatientViewController {

    @GetMapping
    public ModelAndView getHelloWorld() {
        final ModelAndView modelAndView = new ModelAndView("admin-patient");
        modelAndView.getModel().put("msg", "Patient management will be here...");
        return modelAndView;
    }

}
