package com.logoxiang.dao;


import com.logoxiang.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;


public interface UserDao {

    /*    private int uid;//用户id
    private String username;//用户名，账号
    private String password;//密码
    private String name;//真实姓名
    private String birthday;//出生日期
    private String sex;//男或女
    private String telephone;//手机号
    private String email;//邮箱
    private String status;//激活状态，Y代表激活，N代表未激活
    private String code;//激活码（要求唯一）*/
@Select("select * from tab_user where username=#{username}")
   User findByUsername(String username);
@Insert("insert into tab_user values(null,#{username},#{password},#{name},#{birthday}," +
        "#{sex},#{telephone},#{email},#{status},#{code})")
   void save(User user);

    @Select("select * from tab_user where code=#{code}")
   User findByCode(String code);
    @Update("update tab_user set status='Y' where uid=#{uid}")
   void updateStatus(User user);
    @Select("select * from tab_user where username=#{arg0} and password=#{arg1}")
    User findByUsernameAndPassword(String username, String password);
}
