package com.logoxiang.web.servlet;

;
import com.logoxiang.domain.Category;
import com.logoxiang.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/*用于显示header.html的旅游种类标签*/

@Controller
@RequestMapping("/category")
public class CategoryServlet  {
    @Autowired
    private CategoryService service;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Category> findAll() {
       //向数据库中查找
        List<Category> cs =service.findAll();
        //抽取方法了
        return cs;
    }
}
