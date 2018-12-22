package com.logoxiang.dao;


import com.logoxiang.domain.Route;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
* concat(concat('%',#{username}),'%')*/

public interface RouteDao {
    //可能arg0和cid会报错
    @Select("<script> \n" +
            "            select count(*) from tab_route\n" +
            "            <where>\n" +
            "            <if test=\"arg0 != null\">\n" +
            "            and cid = #{arg0}\n" +
            "            </if>\n" +
            "            <if test=\"arg1 != null\">\n" +
            "            and rname like concat(concat('%',#{arg1}),'%')\n"+
            "            </if>\n" +
            "            </where>\n" +
            "            </script>")
    int findTotalCount(int cid, String rname);

    @Select("<script> \n" +
            "            select * from tab_route\n" +
            "            <where>\n" +
            "            <if test=\"arg0 != null\">\n" +
            "            and cid = #{arg0}\n" +
            "            </if>\n" +
            "            <if test=\"arg3 != null\">\n" +
            "            and rname like concat(concat('%',#{arg3}),'%')\n"+
            "            </if>\n" +
            "            </where>\n" +
            "            limit #{arg1} , #{arg2}\n" +
            "            </script>")
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    @Select("select * from tab_route where rid = #{rid}")
    Route findOne(int rid);
}
