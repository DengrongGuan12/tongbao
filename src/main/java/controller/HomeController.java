package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dengrong on 2015/12/28.
 */
@Controller
public class HomeController {
    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("message","hello world");
        return "home";
    }

}
