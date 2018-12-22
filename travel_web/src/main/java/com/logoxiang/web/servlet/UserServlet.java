package com.logoxiang.web.servlet;


import com.logoxiang.domain.ResultInfo;
import com.logoxiang.domain.User;
import com.logoxiang.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
/*注意user前面必须加上/,而对于前端来说,一般开头不加/,因为使用的是相对路径
加上/就变成绝对路径了
*/
@Controller
@RequestMapping("/user")
public class UserServlet  {
    @Autowired
    private UserService service;


    @RequestMapping("/register")
    @ResponseBody
    public ResultInfo register(User user, String check,HttpSession session){

        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //如果验证码为空或者验证码不匹配
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //这是个实体类,用于
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //直接用父类方法就行了
            return info;
            }

        //判断用户名是否重复
        boolean flag = service.register(user);
        ResultInfo info = new ResultInfo();
        if (flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }
        //直接用父类方法就行了
        return info;
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResultInfo login(String username,String password,HttpSession session){
        User u=service.login(username,password);
        ResultInfo info = new ResultInfo();
        if (u==null){
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }else if(u!=null&&"N".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("你未激活");
        }else if (u!=null&&"Y".equals(u.getStatus())){

            session.setAttribute("user",u);
            info.setFlag(true);
        }
        //直接用父类方法就行了
        return info;
    }
    @RequestMapping("/findOne")
    @ResponseBody
    public User findOne(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        //直接用父类方法就行了
        return user;
    }
    @RequestMapping("/exit")
    //以后修改
    public String exit(HttpSession session){
        //把登录用户的session销毁
        session.invalidate();
        //跳转到登录界面
        return "redirect:/login.html";
    }
    @RequestMapping("/active")
    public void active(String code, HttpServletResponse resp)throws  IOException{

        if (code!=null){
            //判断是否有此激活码,其实还应该防止多次点击激活链接,但是这里没写
            boolean flag=service.active(code);

            String msg=null;
            if (flag){
                msg="激活成功";
            }else {
                msg="激活失败";
            }
            //在此界面显示成功或失败
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }
}
