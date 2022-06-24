package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName Category
 * @Description 图书类别所对于的实体类
 * @Author changYU
 * @Date 2021/10/11 19:50
 * @Version 1.0
 **/
@TableName("category")
@Getter
@Setter
@ToString
public class Category {
    @TableId(type = IdType.AUTO)//主键自增
    private Long categoryId;

    private String categoryName;

}
