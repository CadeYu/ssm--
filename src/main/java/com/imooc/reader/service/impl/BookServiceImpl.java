package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName BookServiceImpl
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/13 13:49
 * @Version 1.0
 **/
@SuppressWarnings("all")
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)//不涉及写方法，不开启事务，且只读
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /*
     * 功能描述
     * @Param:
     * @param page 要查询第几页数据？
     * @param row  每页要显示多少条？
     * @Return: com.baomidou.mybatisplus.core.metadata.IPage<com.imooc.reader.entity.Book>
     * @Author: changYu
     * @Date: 2021/10/13 13:59
     * @Description: 图书的分页查询
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer row) {
        Page p = new Page<Book>(page, row);
        QueryWrapper queryWrapper = new QueryWrapper();
        //从前端传来了有效的分类编号
        if (categoryId != null && categoryId != -1) {
            //增加where条件字句
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            //按照评价人数排序
            if (order.equals("quantity")) {
                //降序排序
                queryWrapper.orderByDesc("evaluation_quantity");
            }//按照评分排序
            else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }

        }


        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;
    }

    @Override
    public Book selectById(Long id) {
//        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("book_id", id);
        if (id != null && id != -1) {
            Book book = bookMapper.selectById(id);
            return book;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void updataEvaluation() {
        bookMapper.updataEvaluation();
    }

    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        return book;
    }

    /**
     * 更新图书
     *
     * @param book 新图书数据
     * @return 更新后的数据
     */
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    /**
     * 删除图书及相关数据
     *
     * @param bookId 图书编号
     */
    @Transactional
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();
        mrsQueryWrapper.eq("book_id", bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<Evaluation>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationMapper.delete(evaluationQueryWrapper);

    }

}

