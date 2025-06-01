package ua.com.ivolnov.ecg.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web/admin/staff")
@Controller
public class AdminStaffViewController {

    @GetMapping
    public ModelAndView getHelloWorld() {
        final ModelAndView modelAndView = new ModelAndView("admin-staff");
        modelAndView.getModel().put("msg", "Staff management will be here...");
        return modelAndView;
    }

}
