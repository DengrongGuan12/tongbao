package controller;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pojo.*;
import service.OrderService;
import service.UserService;
import vo.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cg on 2015/12/29.
 */
@Controller
@RequestMapping("user")
public class UserController {
    public static int count = 0;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @RequestMapping("/userRegister")
    public String userRegister(Model model){
        boolean result=userService.register("15851813131214", "fsdf", new Byte("1"));
        if(result){
            model.addAttribute("message","register success");
        }else{
            model.addAttribute("message","register fail");
        }
        return "register";
    }
    @RequestMapping(value = "/id/{id}",method = RequestMethod.GET)
        @ResponseBody
    public RestResult getUser(@PathVariable int id){
//        String[] strings = {"a","b","c"};
//        String string = "sdsd";
        count++;
        User user = userService.getUserById(id);
        return RestResult.CreateResult(200,user);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
        @ResponseBody
    public int getCount(){
        count++;
        return count;
    }

    @RequestMapping(value = "/uploadPicture", method = RequestMethod.POST)
    @ResponseBody
    public RestResult uploadPicture(@RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            String path = "/store/picture/";
            String storeName ="";
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
                storeName = path + time+"."+ext;
                InputStream inputStream = file.getInputStream();
                OutputStream outputStream = new FileOutputStream(storeName);
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
            int id = userService.addFile(storeName);
            String url = "http://120.27.112.9:8080/tongbao/user/picture?id="+id;
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setUrl(url);
            return RestResult.CreateResult(1,urlInfo);
        } else {
            return RestResult.CreateResult(0,"文件不存在");
        }
    }
    @RequestMapping(value = "/picture",method = RequestMethod.GET)
    public void picture(@RequestParam("id") int id, HttpServletRequest request, HttpServletResponse response){
        String fullFileName = userService.getFilePathById(id);
        File tempFile =new File( fullFileName.trim());

        String fileName = tempFile.getName();
        //设置文件MIME类型
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType(request.getSession().getServletContext().getMimeType(fileName));
        //设置Content-Disposition,不设置表示直接在浏览器显示
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

    @RequestMapping(value = "/register",method = RequestMethod.POST)
        @ResponseBody
    public RestResult register(UserAuthInfo userAuthInfo){
//        try {
//            userAuthInfo.setPhoneNumber(new String(userAuthInfo.getPhoneNumber().getBytes("ISO-8859-1"),"UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return RestResult.CreateResult(1,userAuthInfo);
        if(userService.register(userAuthInfo.getPhoneNumber(),userAuthInfo.getPassword(),userAuthInfo.getType())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"注册失败");
        }

    }

    @RequestMapping(value = "/hasRegister", method = RequestMethod.POST)
    @ResponseBody
    public RestResult hasRegister(PhoneNumInfo phoneNumInfo){
        if(userService.phoneNumExist(phoneNumInfo.getPhoneNumber())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
        @ResponseBody
    public RestResult login(@ModelAttribute("UserAuthInfo")UserAuthInfo userAuthInfo, HttpSession session){
        vo.User user = userService.login(userAuthInfo.getPhoneNumber(),userAuthInfo.getPassword(),userAuthInfo.getType(),session);

        if(user == null){
            return RestResult.CreateResult(0,"登录失败");
        }else{
            return RestResult.CreateResult(1,user);
        }
    }

    @RequestMapping(value = "/auth/modifyNickName", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyNickName(@ModelAttribute("ModifiedNickName")ModifiedNickName modifiedNickName){
        int id = userService.hasLogin(modifiedNickName.getToken());
        if(userService.changeNickName(id,modifiedNickName.getNickName())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }

    @RequestMapping(value = "/auth/modifyPassword", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyPassword(@ModelAttribute("ModifiedPassword")ModifiedPassword modifiedPassword){
        int id = userService.hasLogin(modifiedPassword.getToken());
        if(userService.changePassword(id,modifiedPassword.getOldPassword(),modifiedPassword.getNewPassword())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }

    @RequestMapping(value = "/auth/modifyIcon", method = RequestMethod.POST)
        @ResponseBody
    public RestResult modifyIcon(@ModelAttribute("ModifiedIcon")ModifiedIcon modifiedIcon){
        int id = userService.hasLogin(modifiedIcon.getToken());
        if(userService.changeIconUrl(id,modifiedIcon.getIconUrl())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败!");
        }
    }
    @RequestMapping(value = "/auth/showAccount",method = RequestMethod.POST)
    @ResponseBody
    public RestResult showAccount(@ModelAttribute("TokenAuthInfo") TokenAuthInfo tokenAuthInfo){
//        List list = userService.getUserAccount(1);
//        return RestResult.CreateResult(1,list);
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List list = userService.getUserAccount(userId);
        return RestResult.CreateResult(1,list);
    }

    @RequestMapping(value = "/auth/getContacts",method = RequestMethod.POST)
        @ResponseBody
    public RestResult getContacts(@ModelAttribute("TokenAuthInfo") TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List list = userService.getContacts(userId);
        return RestResult.CreateResult(1,list);
    }
    @RequestMapping(value = "/auth/getContactDetail",method = RequestMethod.POST)
        @ResponseBody
    public RestResult getContactDetail(@ModelAttribute("UserIdInfoWithAuth")UserIdInfoWithAuth userIdInfoWithAuth){
        int userId = userService.hasLogin(userIdInfoWithAuth.getToken());
        ContactDetail contactDetail = userService.getContactDetail(userIdInfoWithAuth.getId());
        return RestResult.CreateResult(1,contactDetail);
    }
    @RequestMapping(value = "/auth/getMyMessages",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getMyMessages(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        List list = userService.getMessagesByUserId(userId);
        return RestResult.CreateResult(1,list);

    }
    @RequestMapping(value = "/auth/readMessage", method = RequestMethod.POST)
    @ResponseBody
    public RestResult readMessage(@ModelAttribute("IdInfoWithToken")IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(userService.readMessage(userId,idInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败");
        }
    }
    @RequestMapping(value = "/auth/deleteMessage", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteMessage(@ModelAttribute("IdInfoWithToken")IdInfoWithAuth idInfoWithAuth){
        int userId = userService.hasLogin(idInfoWithAuth.getToken());
        if(userService.deleteMessage(userId,idInfoWithAuth.getId())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败");
        }
    }
    @RequestMapping(value = "getAllTruckTypes",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getAllTruckTypes(){
        List list = userService.getTruckTypes();
        return RestResult.CreateResult(1,list);

    }
    @RequestMapping(value = "/auth/recharge",method = RequestMethod.POST)
    @ResponseBody
    public RestResult recharge(@ModelAttribute("RechargeInfo")RechargeInfo rechargeInfo){
        int userId = userService.hasLogin(rechargeInfo.getToken());
        if(userService.recharge(userId,rechargeInfo.getMoney())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"充值失败!");
        }
    }
    @RequestMapping(value = "/auth/withdraw", method = RequestMethod.POST)
    @ResponseBody
    public RestResult withdraw(@ModelAttribute("WithdrawInfo")RechargeInfo rechargeInfo){
        int userId = userService.hasLogin(rechargeInfo.getToken());
        if(userService.withdraw(userId,rechargeInfo.getMoney())){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"操作失败");
        }
    }
    @RequestMapping(value = "/auth/getMoney",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getMoney(@ModelAttribute("TokenAuthInfo")TokenAuthInfo tokenAuthInfo){
        int userId = userService.hasLogin(tokenAuthInfo.getToken());
        UserSimple userSimple = userService.getUserSimpleById(userId);
        return RestResult.CreateResult(1,userSimple);
    }
    @RequestMapping(value = "/testJPush",method = RequestMethod.GET)
    @ResponseBody
    public RestResult testJPush(@RequestParam("alias")String alias){
//        JPushClient jPushClient = new JPushClient("9f5b375a48f78a79f18aaa0c","12be19e543158d0d057b2d09",3);
//        PushPayload payload = userService.buildPushObject_all_all_alert(tag);
//        try {
//            PushResult pushResult = jPushClient.sendPush(payload);
//            return RestResult.CreateResult(1,pushResult);
//        } catch (APIConnectionException e) {
//            e.printStackTrace();
//        } catch (APIRequestException e) {
//            e.printStackTrace();
//        }
        Map<String,String> extras = new HashMap<String, String>();
        extras.put("type","0");
        extras.put("id","1");
        PushResult pushResult = userService.push(alias,"title","content",extras);
        if(pushResult != null){
            return RestResult.CreateResult(1,pushResult);
        }
        return RestResult.CreateResult(0,"failure!");

    }
    @RequestMapping(value = "/addFeedback",method = RequestMethod.GET)
    @ResponseBody
    public RestResult addFeedback(@RequestParam("content")String content){
        if(userService.addFeedback(content)){
            return RestResult.CreateResult(1);
        }else{
            return RestResult.CreateResult(0,"error");
        }
    }

    @RequestMapping(value = "/auth/getAccountByMonth",method = RequestMethod.GET)
    @ResponseBody
    public RestResult getAccountByMonth(@RequestParam("token")String token,@RequestParam("year")int year,@RequestParam("month")int month){
        int userId = userService.hasLogin(token);
        MonthAccount monthAccount = userService.getAccountByMonth(userId,year,month);
        return RestResult.CreateResult(1,monthAccount);
    }

    @RequestMapping(value = "/getBannerInfo",method = RequestMethod.GET)
    @ResponseBody
    public RestResult getBannerInfo(){
        return RestResult.CreateResult(1,userService.getBannerInfoList());
    }

}
