package com.logoxiang.service.impl;

import com.logoxiang.dao.UserDao;
import com.logoxiang.domain.User;
import com.logoxiang.service.UserService;
import com.logoxiang.util.MailUtils;
import com.logoxiang.util.UuidUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    private UserDao userDao;




    @Override
    //注册用户的方法,返回true注册成功
    public boolean register(User user)   {

        User u = userDao.findByUsername(user.getUsername());
        //不等于空,意思查到了数据,就不可以再注册了,所以返回false
        if (u!=null){
            return false;
        }
        //数据库没有用户名重名的情况
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        //保存激活码和激活状态到user对象
        userDao.save(user);
        //向用户邮箱发送激活邮件
        String content="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        //返回true意思是注册成功了
        return true;
    }

    @Override
    //激活用户的方法,返回ture激活成功
    public boolean active(String code) {
        //向数据库中查找是否有此激活码
       User user =userDao.findByCode(code);
       //不为空就是有
       if (user!=null){
           //向数据库设置激活状态
           userDao.updateStatus(user);
           return true;
       }else {
           return false;
       }
    }

    @Override
    //查找用户的方法,判断登录
    public User login(String username,String password) {
        return userDao.findByUsernameAndPassword(username,password);
    }
}
