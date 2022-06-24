package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName MemberReadState
 * @Description 会员阅读状态的实体类
 * @Author changYU
 * @Date 2021/10/18 19:41
 * @Version 1.0
 **/
@TableName("member_read_state")
@Getter
@Setter
@ToString
public class MemberReadState {

    @TableId(type = IdType.AUTO)
    private Long rsId;

    private Long bookId;

    private Long memberId;

    private Integer readState;

    private Date createTime;


}
