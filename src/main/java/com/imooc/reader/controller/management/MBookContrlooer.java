package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.exception.BussinessException;
import net.sf.jsqlparser.statement.create.table.Index;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.cache.jcache.interceptor.JCacheOperationSourcePointcut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MBookContrlooer
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/20 22:30
 * @Version 1.0
 **/
@Controller
@RequestMapping("/management/book")
public class MBookContrlooer {


    @Resource
    private BookService bookService;

    /**
     * 功能描述
     *
     * @Param:
     * @Return: org.springframework.web.servlet.ModelAndView
     * @Author: changYu
     * @Date: 2021/10/20 22:33
     * @Description: 显示图书管理页面
     */

    @GetMapping("/index.html")
    public ModelAndView showBook() {
        ModelAndView modelAndView = new ModelAndView("/management/book");
        return modelAndView;
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {

        //得到上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        //文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }


    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            //因为前端页面传数据的时候不全，所以要自己补充
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            //解析图书的Description
            Document doc = Jsoup.parse(book.getDescription());
            //选择富文本编辑器上传的第一章图片
            Element img = doc.select("img").first();
            String cover = img.attr("src");
            //来自于Description描述的第一张图
            book.setCover(cover);

            bookService.createBook(book);

            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException exception) {
            exception.printStackTrace();
            result.put("code", exception.getCode());
            result.put("msg", exception.getMsg());
        }


        return result;
    }

    /**
     * 分页查询图书数据
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }

        if (limit == null) {
            limit = 10;
        }

        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");

        result.put("data", pageObject.getRecords());//当前页面数据

        result.put("count", pageObject.getTotal());//未分页时记录总数
        return result;
    }

    /**
     * 获取图书详细信息
     *
     * @param bookId
     * @return
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId) {
        Book book = bookService.selectById(bookId);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    /**
     * 更新图书数据
     *
     * @param book
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) {
        Map result = new HashMap();
        try {
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }


}
