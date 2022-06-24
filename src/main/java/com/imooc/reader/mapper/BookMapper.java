package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Book;

////图书mapper接口
public interface BookMapper extends BaseMapper<Book> {

    /*
     * 功能描述
     * @Param:
     * @Return: void
     * @Author: changYu
     * @Date: 2021/10/19 21:28
     * @Description: 更新图书的评分
     */

    public void updataEvaluation();


}
