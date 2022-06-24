package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.imageio.plugins.bmp.BMPImageWriteParam;
import javax.xml.ws.RequestWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName EvaluationServiceImpl
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/16 14:13
 * @Version 1.0
 **/
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)//不涉及写方法，不开启事务，且只读
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private BookMapper bookMapper;


    /**
     * 功能描述
     *
     * @param bookId
     * @Param:
     * @Return: java.util.List<com.imooc.reader.entity.Evaluation>
     * @Author: changYu
     * @Date: 2021/10/16 14:20
     * @Description: 按照图书的编号查找有效的短评
     */
    @Override
    public List<Evaluation> selectByBoookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id", bookId);
        //有效的短评
        queryWrapper.eq("state", "enable");
        //默认按创造时间的降序排列
        queryWrapper.orderByDesc("create_time");

        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        //evaluation中添加了Book 和 Member 两个表中不存在的属性，mybatisPlus无法处理，需要我们自己处理
        for (Evaluation eva : evaluationList) {
            //拿到会员编号
            Long memberId = eva.getMemberId();
            //利用memberMapper拿到会员对象
            Member member = memberMapper.selectById(memberId);
            //设置会员对象
            eva.setMember(member);
            //设置书本对象
            eva.setBook(book);

        }

        return evaluationList;
    }
}
