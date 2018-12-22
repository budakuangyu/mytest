package com.logoxiang.service.impl;

import com.logoxiang.dao.FavoriteDao;
import com.logoxiang.domain.Favorite;
import com.logoxiang.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteDao favoriteDao;
    @Override
    //向数据库中查找是否被收藏,返回boolean类型
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite=favoriteDao.findByRidAndUid(Integer.parseInt(rid),uid);
       //查到数据就返回true,否则返回false
        return favorite!=null;
    }

    @Override
    //向数据库中查找
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),new Date(), uid);
    }
}
