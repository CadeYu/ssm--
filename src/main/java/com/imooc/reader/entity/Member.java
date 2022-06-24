package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName Member
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/16 15:03
 * @Version 1.0
 **/
@TableName("member")
@Getter
@Setter
@ToString
public class Member {
    @TableId(type = IdType.AUTO)
    private Long memberId;

    private String username;

    private String password;

    private Integer salt;

    private Date createTime;

    private String nickname;


}
