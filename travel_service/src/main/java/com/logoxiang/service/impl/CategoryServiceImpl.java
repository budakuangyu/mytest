package com.logoxiang.service.impl;


import com.logoxiang.dao.CategoryDao;
import com.logoxiang.domain.Category;
import com.logoxiang.service.CategoryService;
import com.logoxiang.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
    private CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        //获取jedis对象
        Jedis jedis = JedisUtil.getJedis();
        //获取jedis的category的值,有可能为null
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        //创建一个List集合,泛型Category
        List<Category> cs=null;
        //判断jdeis里面是否含有category
        if (categorys==null||categorys.size()==0){
            //没有的话向数据库中查询,传到List集合中
            cs=categoryDao.findAll();
            //遍历List集合
            for (int i=0;i<cs.size();i++){
                //往jedis中的sortedset添加category,score,和值value
                //但是方法返回值还是List集合
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());

            }
        }else {
            //redis有数据的话
            cs=new ArrayList<>();
            for (Tuple tuple : categorys) {

                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                //从redis中往List集合中添加数据
                cs.add(category);
            }

        }

        return cs;

    }
}
/*
* findAll方法
* 先判断redis有没有数据,没有就向数据库查找,返回List集合,同时添加到redis中
* 有数据的话从redis中取出数据,添加到List集合中,并返回List集合*/