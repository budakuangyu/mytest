package com.logoxiang.test;

import com.logoxiang.dao.RouteDao;
import com.logoxiang.domain.Route;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RouteDaoTest {
    public static void main(String[] args) throws IOException {
      InputStream is= Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
       SqlSession sqlSession = factory.openSession();
       RouteDao routeDao = sqlSession.getMapper(RouteDao.class);
        int totalCount = routeDao.findTotalCount(5, null);
        System.out.println(totalCount);
        System.out.println("====================");
        List<Route> list = routeDao.findByPage(5, 1, 5, "西安");
        System.out.println(list);
    }
}
