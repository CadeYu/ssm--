package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 功能描述
     *
     * @param bookId
     * @Param:
     * @Return: java.util.List<com.imooc.reader.entity.Evaluation>
     * @Author: changYu
     * @Date: 2021/10/16 14:12
     * @Description: 根据图书编号来查找评论
     */
    public List<Evaluation> selectByBoookId(Long bookId);

}
