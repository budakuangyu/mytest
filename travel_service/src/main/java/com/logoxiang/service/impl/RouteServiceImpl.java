package com.logoxiang.service.impl;


import com.logoxiang.dao.*;
import com.logoxiang.domain.*;
import com.logoxiang.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteDao routeDao;
    @Autowired
    private RouteImgDao routeImgDao;
    @Autowired
    private SellerDao sellerDao;
    @Autowired
    private FavoriteDao favoriteDao;
    @Autowired
    private CategoryDao categoryDao;
    @Override
    //通过传入的参数和计算得到参数得到PageBean对象
    public PageBean<Route> pageQuery(Integer cid, Integer currentPage, Integer pageSize, String rname) {
        PageBean<Route> pb=new PageBean<>();

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        int totalCount=routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        int start=(currentPage-1)*pageSize;
        List<Route> list=routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);
        //计算得出总共需要显示多少页
        int totalPage=totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        //返回包装类
        return pb;
    }

    //返回一条旅游条目的详细信息
    @Override
    //Route是包含3个实体类的
    public Route findOne(String rid) {
        Route route=routeDao.findOne(Integer.parseInt(rid));

        //根据route的rid获取imgList集合
        List<RouteImg> routeImgList=routeImgDao.findByRid(route.getRid());
       //设置到包装类
        route.setRouteImgList(routeImgList);
        //同样根据route的rid获取seller对象,并设置
        Seller seller=sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //获取该旅游条目被收藏的次数
        int count=favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

       Category c= categoryDao.findOne(route.getCid());

       route.setCategory(c);

        return route;
    }
}
