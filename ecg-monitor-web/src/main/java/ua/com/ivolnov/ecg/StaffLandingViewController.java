package ua.com.ivolnov.ecg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web/staff")
@Controller
public class StaffLandingViewController {

    @GetMapping
    public ModelAndView getHelloWorld() {
        final ModelAndView modelAndView = new ModelAndView("staff-landing");
        modelAndView.getModel().put("msg", "Hello Staff");
        return modelAndView;
    }

}
