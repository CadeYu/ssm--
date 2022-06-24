package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.pattern.PathPattern;

/**
 * @ClassName Book
 * @Description 书本的实体类
 * @Author changYU
 * @Date 2021/10/13 13:40
 * @Version 1.0
 **/
@TableName("book")
@Getter
@Setter
@ToString
public class Book {
    @TableId(type = IdType.AUTO)
    private Long bookId;

    private String bookName;

    private String subTitle;

    private String author;

    private String cover;

    private String description;

    private String categoryId;

    private Float evaluationScore;

    private Integer evaluationQuantity;

}
