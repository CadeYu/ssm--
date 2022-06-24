package com.imooc.reader.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.*;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import com.sun.corba.se.spi.ior.IdentifiableFactory;
import com.sun.org.apache.xerces.internal.impl.dv.xs.MonthDV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName BookController
 * @Description 对图书进行操作的控制器
 * @Author changYU
 * @Date 2021/10/11 20:20
 * @Version 1.0
 **/
@Controller
@SuppressWarnings("all")
public class BookController {
    @Resource
    private CategoryService categoryService;

    @Resource
    private BookService bookService;

    @Resource
    private EvaluationService evaluationService;

    @Resource
    private MemberService memberService;

    /**
     * 功能描述
     *
     * @Param:
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: changYu
     * @Date: 2021/10/11 20:25
     * @Description: 展示首页
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView modelAndView = new ModelAndView("/index");
        List<Category> categoryList = categoryService.sellectAll();
        modelAndView.addObject("categoryList", categoryList);
        return modelAndView;


    }

    /*
     * 功能描述
     * @Param:
     * @param p  当前的页面page
     * @Return: com.baomidou.mybatisplus.core.metadata.IPage<com.imooc.reader.entity.Book>
     * @Author: changYu
     * @Date: 2021/10/13 19:07
     * @Description: 进行图书的分页查询
     */
    @GetMapping("/books")
    @ResponseBody//使用springmvc将对象进行json序列化输出
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        //如果前端没有发送数据，那么默认p为1
        if (p == null) {
            p = 1;
        }
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }

    @GetMapping("/book/{id}")//路径变量配置
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session) {
        Book book = bookService.selectById(id);
        //获取对应编号的图书的短评
        List<Evaluation> evaluationList = evaluationService.selectByBoookId(id);
        Member loginMember = (Member) session.getAttribute("loginMember");
        ModelAndView modelAndView = new ModelAndView("/detail");
        if (loginMember != null) {
            //获取会员阅读状态
            MemberReadState memberReadState = memberService.selectMemberReadState(loginMember.getMemberId(), id);
            modelAndView.addObject("memberReadState", memberReadState);
        }


        modelAndView.addObject("book", book);
        modelAndView.addObject("evaluationList", evaluationList);
        return modelAndView;
    }


}
