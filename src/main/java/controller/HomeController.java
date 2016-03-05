package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pojo.ModifiedNickName;
import vo.RestResult;

import java.io.IOException;

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

    @RequestMapping("/fileUploadTest")
    public String fileUploadTest(Model model){
        return "file";
    }
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String handleFormUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file,Model model) {
        //MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // store the bytes somewhere
            //在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹

            model.addAttribute("message","success");
            return "home";
        } else {
            model.addAttribute("message","failure");
            return "home";
        }
    }

}
