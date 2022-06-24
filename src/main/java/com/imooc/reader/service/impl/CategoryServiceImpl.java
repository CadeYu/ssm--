package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Category;
import com.imooc.reader.mapper.CategoryMapper;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/11 20:05
 * @Version 1.0
 **/
@Service("categoryService")//在容器中它的id为它实现的接口名，面向接口编程
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)//不涉及写方法，不开启事务，且只读
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 功能描述
     *
     * @Param:
     * @Return: java.util.List<com.imooc.reader.entity.Category>
     * @Author: changYu
     * @Date: 2021/10/11 20:12
     * @Description: 查询所有图书的分类
     */

    @Override
    public List<Category> sellectAll() {
        List<Category> categoryList = categoryMapper.selectList(new QueryWrapper<>());
        return categoryList;
    }


}
