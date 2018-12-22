package com.logoxiang.web.servlet;


import com.logoxiang.domain.PageBean;
import com.logoxiang.domain.Route;
import com.logoxiang.domain.User;
import com.logoxiang.service.FavoriteService;
import com.logoxiang.service.RouteService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("/route")
public class RouteServlet  {
    @Autowired
    private RouteService routeService;
    @Autowired
    private FavoriteService favoriteService;

    //分页查询,显示旅游条目信息
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean<Route>  pageQuery(Integer currentPage, Integer  cid, String rname) throws IOException {

        if (currentPage==null){
            currentPage=1;
        }

         Integer   pageSize=5;

        System.out.println("cc"+currentPage);
        System.out.println("cid"+cid);
        //把查询到的数据包装成PageBean类,PageBean包含多种数据
        PageBean<Route> pb=routeService.pageQuery(cid,currentPage,pageSize,rname);
        //使用Jackson往ajax回写数据
        return pb;


        }
    //查找一个旅游条目
    @RequestMapping("/findOne")
    @ResponseBody
    public Route findOne(String rid) {
        //向数据库中查询,返回旅游条目详细信息
        Route route=routeService.findOne(rid);
        //使用jackson传输json数据
        return route;
    }
    //判断收藏没收藏
    @RequestMapping("/isFavorite")
    @ResponseBody
    public Boolean isFavorite(String rid, HttpSession session) {

        //接收session传来的用户user
        User user = (User) session.getAttribute("user");
        int uid=0;
        //user为空,uid设置为0
        if (user==null){
            return false;
        }else {
            //user不为空,获取user的id
            uid=user.getUid();
        }
        boolean flag=favoriteService.isFavorite(rid,uid);
       return flag;
    }
    //添加收藏
    @RequestMapping("/addFavorite")
    public void addFavorite(String rid,HttpSession session){
        //获取rid和uid

        User user= (User) session.getAttribute("user");
        int uid;
        if (user==null){
            return;
        }else {
            uid=user.getUid();
        }
        //向数据库中查找
        favoriteService.add(rid,uid);
    }
}
