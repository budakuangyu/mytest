package com.logoxiang.service;


import com.logoxiang.domain.User;

public interface UserService  {
    boolean register(User user) ;

    boolean active(String code);

    User login(String username,String password);
}
