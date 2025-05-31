package ua.com.ivolnov.ecg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/web")
@Controller
public class ViewController {

    @GetMapping
    public ModelAndView getHelloWorld() {
        final ModelAndView modelAndView = new ModelAndView("hello-world");
        modelAndView.getModel().put("msg", "Hello World");
        return modelAndView;
    }

}
