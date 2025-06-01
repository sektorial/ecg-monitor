package ua.com.ivolnov.ecg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web/admin")
@Controller
public class AdminLandingViewController {

    @GetMapping
    public ModelAndView getHelloWorldAdmin() {
        final ModelAndView modelAndView = new ModelAndView("admin-landing");
        modelAndView.getModel().put("msg", "Hello Admin");
        return modelAndView;
    }

}
