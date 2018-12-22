package com.logoxiang.dao;


import com.logoxiang.domain.RouteImg;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RouteImgDao {
    @Select("select * from tab_route_img where rid = #{rid}")
    List<RouteImg> findByRid(int rid);
}
