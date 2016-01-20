package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pojo.ModifiedNickName;
import vo.RestResult;

/**
 * Created by dengrong on 2015/12/28.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    @RequestMapping("/index")
    public String home(Model model){
        model.addAttribute("message","hello world");
        return "home";
    }
    @RequestMapping("/testEncoding")
    @ResponseBody
    public RestResult testEncoding(ModifiedNickName modifiedNickName){
        System.out.print(modifiedNickName.getNickName());
        return RestResult.CreateResult(1,modifiedNickName);
    }

}
