package com.logoxiang.dao;


import com.logoxiang.domain.Seller;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


public interface SellerDao {
    @Select("select * from tab_seller where sid = #{sid}")
    Seller findById(int sid);
}
