package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class BookServiceImplTest extends TestCase {
    @Resource
    private BookService bookService;

    @Test
    public void testPaging() {
        IPage<Book> paging = bookService.paging(2L, "quantity", 1, 10);
        List<Book> records = paging.getRecords();
        for (Book b : records) {
            System.out.println(b.getBookId() + ":" + b.getBookName());
        }
        System.out.println(paging.getPages());
        System.out.println(paging.getTotal());
    }
}