package com.logoxiang.service;


import com.logoxiang.domain.PageBean;
import com.logoxiang.domain.Route;

public interface RouteService {
    PageBean<Route> pageQuery(Integer cid, Integer currentPage, Integer pageSize, String rname);

    Route findOne(String rid);
}
