package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.service.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName MemberServiceImpl
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/17 17:33
 * @Version 1.0
 **/
@Service("memberService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)//不涉及写方法，不开启事务，且只读
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberReadStateMapper memberReadStateMapper;


    @Resource
    private EvaluationMapper evaluationMapper;


    @Override
    public Member createMember(String username, String password, String nickname) {
        //先去查询现有的数据库中有没有已注册的username
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        //如果查询的list大于0，说明用户名已存在，需要抛出异常
        if (memberList.size() > 0) {
            throw new BussinessException("M01", "用户名已存在");
        } else {
            Member member = new Member();
            member.setUsername(username);
            member.setNickname(nickname);
            //生成一个随机数字作为盐值,生成1000-1999之间的随机整数
            int salt = new Random().nextInt(1000) + 1000;

            String md5 = MD5Utils.md5Digest(password, salt);

            member.setPassword(md5);
            member.setSalt(salt);

            member.setCreateTime(new Date());

            memberMapper.insert(member);

            return member;

        }

    }


    /**
     * 功能描述
     *
     * @param username
     * @param password
     * @Param:
     * @Return: com.imooc.reader.entity.Member
     * @Author: changYu
     * @Date: 2021/10/17 21:19
     * @Description: 登陆检查
     */
    @Override
    public Member checkLogin(String username, String password) {
        //先检查当前输入的用户名是否正确？即用户不能输入数据库中没有的用户名
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        //selectOne方法只能查出一个对象，或者没有
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new BussinessException("M02", "用户不存在");
        }

        String md5Digest = MD5Utils.md5Digest(password, member.getSalt());
        if (!member.getPassword().equals(md5Digest)) {
            throw new BussinessException("M03", "密码错误");
        }
        //如果上面的验证都通过了，我们就把用户对象返回
        return member;
    }

    @Override
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        //匹配查询对应的会员对对应的书本的阅读状态
        queryWrapper.eq("book_id", bookId);
        queryWrapper.eq("member_id", memberId);

        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);


        return memberReadState;
    }

    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("book_id", bookId);


        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        //如果之前没有阅读状态，就直接赋值
        if (memberReadState == null) {
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setReadState(readState);
            memberReadState.setBookId(bookId);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        } else {
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }

        return memberReadState;
    }

    @Override
    public Evaluation evaluate(Long memberId, Long bookId, Integer score, String content) {
        Evaluation evaluation = new Evaluation();
        evaluation.setMemberId(memberId);
        evaluation.setBookId(bookId);
        evaluation.setScore(score);
        evaluation.setContent(content);
        evaluation.setCreateTime(new Date());
        evaluation.setState("enable");
        evaluation.setEnjoy(0);
        evaluationMapper.insert(evaluation);
        return evaluation;


    }

    @Override
    public Evaluation enjoy(Long evaluationId) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setEnjoy(evaluation.getEnjoy() + 1);
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
