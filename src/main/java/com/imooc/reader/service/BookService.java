package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import org.omg.CORBA.PUBLIC_MEMBER;

@SuppressWarnings("all")
public interface BookService {
    //对图书进行分页查询

    /**
     * 功能描述
     *
     * @param page 要查询第几页数据？
     * @param row  每页要显示多少条？
     * @Param: categoryId：要查询的类型ID，order：查询的顺序
     * @Return: com.baomidou.mybatisplus.core.metadata.IPage<com.imooc.reader.entity.Book>
     * @Author: changYu
     * @Date: 2021/10/13 13:52
     * @Description:
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer row);


    /**
     * 功能描述
     *
     * @param id 图书的编号
     * @Param:
     * @Return: com.imooc.reader.entity.Book
     * @Author: changYu
     * @Date: 2021/10/15 17:21
     * @Description: 根据图书的编码查询出图书对象
     */
    public Book selectById(Long id);


    /**
     * 功能描述
     *
     * @Param:
     * @Return: void
     * @Author: changYu
     * @Date: 2021/10/19 21:36
     * @Description: 更新图书的评分
     */
    public void updataEvaluation();


    /**
     * 功能描述
     *
     * @Param:
     * @Return: com.imooc.reader.entity.Book
     * @Author: changYu
     * @Date: 2021/10/21 20:21
     * @Description: 创建新的图书
     */
    public Book createBook(Book book);


    /**
     * 更新图书
     *
     * @param book 新图书数据
     * @return 更新后的数据
     */
    public Book updateBook(Book book);

    /**
     * 删除图书及相关数据
     *
     * @param bookId 图书编号
     */
    public void deleteBook(Long bookId);

}
