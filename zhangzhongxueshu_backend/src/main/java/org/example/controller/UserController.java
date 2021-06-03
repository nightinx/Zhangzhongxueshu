package org.example.controller;
import com.alibaba.druid.util.StringUtils;
import com.mysql.cj.x.protobuf.MysqlxResultset;
import com.sun.mail.util.BASE64EncoderStream;
import org.apache.catalina.User;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.tomcat.util.security.MD5Encoder;
import org.example.controller.viewobject.UserVO;
import org.example.dataobj.UserDO;
import org.example.dataobj.UserPasswordDO;
import org.example.error.BusinessException;
import org.example.error.EmBusinessError;
import org.example.response.CommonReturnType;
import org.example.service.UserService;
import org.example.service.model.UserModel;
import org.example.sessionutils.MySessionContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wkr on 2021/5/16
 */
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders ="*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;





    //用户获取otp接口
    @RequestMapping("/getotp")
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "email")String email) throws EmailException {
        //需要按一定规则生成OTP验证码
        Random random=new Random();
        int randomInt=random.nextInt(900000);
        randomInt+=100000;
        String otpCode=String.valueOf(randomInt);

        //将OTP验证码同对应用户的邮箱关联，使用httpsession方式绑定
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(email,otpCode);
        MySessionContext.addSession(session);
        String sessionid=session.getId();
        System.out.println(MySessionContext.getSession(sessionid).getId());

        System.out.println("Email:"+email+" otp:"+otpCode);
        //将OTp验证码通过邮箱通道发送给用户

        System.out.println("Email:"+email+" otp:"+otpCode);
        HtmlEmail htmlEmail=new HtmlEmail();
        htmlEmail.setHostName("smtp.163.com");
        htmlEmail.setCharset("utf-8");
        htmlEmail.addTo(email);
        htmlEmail.setFrom("zhangzhongxueshu@163.com", "zhangzhongxueshu");
        htmlEmail.setAuthentication("zhangzhongxueshu@163.com","PLUBGXQEYBLCMHTT");
        htmlEmail.setSubject("掌中学术验证码");//设置发送主题
        htmlEmail.setMsg(otpCode);//设置发送内容
        htmlEmail.send();//进行发送


        return CommonReturnType.create(sessionid);
    }


    //用户注册登录接口
    @RequestMapping(value = "/register_or_login")
    @ResponseBody
    public CommonReturnType register_or_login(@RequestParam(name="email")String MailBoxAddress,
                                              @RequestParam(name="otp")String otpCode,
                                              @RequestParam(name="sessionID")String sessionID) throws BusinessException, NoSuchAlgorithmException {
        //验证邮箱和对应otpcode相符合

        HttpSession session = MySessionContext.getSession(sessionID);
        String inSessionOtpCode=(String)session.getAttribute(MailBoxAddress);


        //System.out.println("register_or_login sessionID:"+sessionID);
        //System.out.println("register_or_login otp:"+otpCode);
        //System.out.println("register_or_login session.getId():"+session.getId());
        //System.out.println("register_or_login inSessionOtpCode:"+inSessionOtpCode);
        //System.out.println("register_or_login before verification success");
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"验证码不符合");
        }
        MySessionContext.removeSession(session);
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel=userService.getUserByEmail(MailBoxAddress);
        System.out.println("register_or_login verification success");
        if(userModel==null){
            System.out.println("fail to find user, register begin");
            //用户注册流程
            String name=MailBoxAddress.split("\\@")[0];
            userModel=new UserModel();
            userModel.setName(name);
            userModel.setEncrptPassword(EncodeByMd5("root"));
            userModel.setMailBox(MailBoxAddress);
            System.out.println("user name: "+name);
            String school=MailBoxAddress.split("\\@")[1].split("\\.")[0];
            userModel.setSchool(school);
            userModel.setInterest_1("all");
            userModel.setInterest_2("all");
            userModel.setInterest_3("all");
            //System.out.println("user name: "+name);
            System.out.println("school: "+school);
            //System.out.println("MailBoxAddress: "+MailBoxAddress);
            userService.register(userModel);
            return CommonReturnType.create("注册成功");
        }
        else{
            //用户登录流程
            return CommonReturnType.create("登录成功");
        }
    }

    //根据email获取用户信息
    @RequestMapping(value = "/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="email") String email) throws BusinessException {
        //调用service服务获取对应email的用户对象并返回给前端
        UserModel userModel=userService.getUserByEmail(email);
        //若获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        UserVO userVO=convertFromModel(userModel);
        return CommonReturnType.create(userVO);
    }

    //用户更新recom_info接口
    @RequestMapping(value = "/update")
    @ResponseBody
    public CommonReturnType update_recom_info(@RequestParam(name="email") String email,
                                              @RequestParam(name="interest1") String interest1,
                                              @RequestParam(name="interest2") String interest2,
                                              @RequestParam(name="interest3") String interest3) throws BusinessException {
        //调用service服务获取对应email的用户对象并返回给前端
        UserModel userModel=userService.getUserByEmail(email);
        //若获取的对应用户信息不存在
        if(userModel==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
        }
        userModel.setInterest_1(interest1);
        userModel.setInterest_2(interest2);
        userModel.setInterest_3(interest3);
        userService.update_recom(userModel);
        return CommonReturnType.create("{更新成功}");
    }

    private UserVO convertFromModel(UserModel userModel){
        if (userModel==null){
            return null;
        }
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    public String EncodeByMd5(String str) throws NoSuchAlgorithmException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        Base64.Encoder encoder=Base64.getEncoder();
        //加密字符串
        String result=encoder.encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
        return result;
    }


}
