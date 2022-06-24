package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;

/**
 * @ClassName MemberService
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/17 17:31
 * @Version 1.0
 **/

public interface MemberService {


    /**
     * 功能描述
     *
     * @param username
     * @param password
     * @param nickname
     * @Param:
     * @Return: void
     * @Author: changYu
     * @Date: 2021/10/17 17:32
     * @Description: 创建新的会员
     */
    public Member createMember(String username, String password, String nickname);


    /**
     * 功能描述
     *
     * @param username
     * @param password
     * @Param:
     * @Return: com.imooc.reader.entity.Member
     * @Author: changYu
     * @Date: 2021/10/17 21:17
     * @Description: 检查用户登录的service方法
     */
    public Member checkLogin(String username, String password);


    /**
     * 功能描述
     *
     * @param memberId
     * @param bookId
     * @Param:
     * @Return: com.imooc.reader.entity.MemberReadState
     * @Author: changYu
     * @Date: 2021/10/18 19:49
     * @Description: 查询对应的会员对对应的书本的阅读信息
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);


    /**
     * 功能描述
     *
     * @param memberId
     * @param bookId
     * @param readState
     * @Param:
     * @Return: com.imooc.reader.entity.MemberReadState
     * @Author: changYu
     * @Date: 2021/10/18 20:26
     * @Description: 更新阅读状态
     */
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);


    /**
     * 功能描述
     *
     * @param memberId
     * @param bookId
     * @Param:
     * @Return: com.imooc.reader.entity.Evaluation
     * @Author: changYu
     * @Date: 2021/10/18 21:22
     * @Description: 用户为图书添加短评
     */
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content);


    /**
     * 功能描述
     *
     * @param evaluationId
     * @Param:
     * @Return: com.imooc.reader.entity.Evaluation
     * @Author: changYu
     * @Date: 2021/10/19 21:00
     * @Description: 短评点赞功能
     */
    public Evaluation enjoy(Long evaluationId);


}
