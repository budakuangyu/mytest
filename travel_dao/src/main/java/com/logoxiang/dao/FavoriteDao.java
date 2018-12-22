package com.logoxiang.dao;


import com.logoxiang.domain.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

public interface FavoriteDao {
    @Select("select * from tab_favorite where rid= #{arg0} and uid= #{arg1} ")
    Favorite findByRidAndUid(int rid, int uid);
    @Insert("insert into tab_favorite values(#{arg0},#{arg1},#{arg2})")
    void add(int rid, Date date, int uid);
    @Select("select count(*) from tab_favorite where rid=#{rid}")
    int findCountByRid(int rid);
}
