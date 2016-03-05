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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
            String path = "c://tmp//";
            int length = 0;
            try {

                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                // 读取文件流并保持在指定路径
                String time = System.currentTimeMillis()+"";
                String originalName = file.getOriginalFilename();
//                String[] strings = originalName.split("");
//                length = strings.length;
                String ext = "";
                int dot = originalName.lastIndexOf('.');
                if ((dot >-1) && (dot < (originalName.length() - 1))) {
                     ext = originalName.substring(dot + 1);
                }
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(path
                        + time+"."+ext);
                byte[] buffer = file.getBytes();
                int bytesum = 0;
                int byteread = 0;
                while ((byteread = inputStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    outputStream.write(buffer, 0, byteread);
                    outputStream.flush();
                }
                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // store the bytes somewhere
            //在这里就可以对file进行处理了，可以根据自己的需求把它存到数据库或者服务器的某个文件夹

            model.addAttribute("message",length);
            model.addAttribute("message1",file.getOriginalFilename());

            return "home";
        } else {
            model.addAttribute("message","failure");
            return "home";
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void getFile(HttpServletRequest request, HttpServletResponse response){
        String fileName = "1457174309830.png";
        String fullFileName = "c://tmp//1457174309830.png";
        //设置文件MIME类型
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType(request.getSession().getServletContext().getMimeType(fileName));
        //设置Content-Disposition
//        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
        //读取目标文件，通过response将目标文件写到客户端
        //获取目标文件的绝对路径
        //System.out.println(fullFileName);
        //读取文件
        InputStream in = null;
        try {
            in = new FileInputStream(fullFileName);
            OutputStream out = response.getOutputStream();

            //写文件
            int b;
            while((b=in.read())!= -1)
            {
                out.write(b);
            }

            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
