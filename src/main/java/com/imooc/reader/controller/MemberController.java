package com.imooc.reader.controller;


import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName MemberController
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/16 21:17
 * @Version 1.0
 **/
@SuppressWarnings("all")
@Controller
public class MemberController {
    @Resource
    MemberService memberService;


    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        ModelAndView modelAndView = new ModelAndView("/register");
        return modelAndView;

    }

    //登陆界面的控制器方法
    @GetMapping("/login.html")
    public ModelAndView showLogin() {

        ModelAndView modelAndView = new ModelAndView("/login");
        return modelAndView;
    }


    @PostMapping("/registe")
    @ResponseBody
    public Map reigste(String vc, String username, String password, String nickname, HttpServletRequest request) {
        Map result = new HashMap();

        //从session中获取正确的验证码
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");

        //判断验证码是否正确
        if (vc == null || verifyCode == null || !verifyCode.equalsIgnoreCase(vc)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            //注册验证
            try {
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("msg", "success");

            } catch (BussinessException bussinessException) {
                bussinessException.printStackTrace();
                result.put("code", bussinessException.getCode());
                result.put("msg", bussinessException.getMsg());
            }

        }

        return result;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String vc, String username, String password, HttpSession session) {
        Map result = new HashMap();

        //从session中获取正确的验证码
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");

        //判断验证码是否正确
        if (vc == null || verifyCode == null || !verifyCode.equalsIgnoreCase(vc)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            //登陆检查
            try {
                Member member = memberService.checkLogin(username, password);
                //将已经登陆的对象放入session中，以便后续使用
                session.setAttribute("loginMember", member);

                result.put("code", "0");
                result.put("msg", "success");

            } catch (BussinessException bussinessException) {
                bussinessException.printStackTrace();
                result.put("code", bussinessException.getCode());
                result.put("msg", bussinessException.getMsg());
            }

        }

        return result;
    }


    /**
     * 功能描述
     *
     * @param memberId
     * @param bookId
     * @param readState
     * @Param:
     * @Return: java.util.Map
     * @Author: changYu
     * @Date: 2021/10/18 20:36
     * @Description: 用户阅读状态更新的控制器
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map result = new HashMap();
        try {
            MemberReadState memberReadState = memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "sucess");
        } catch (BussinessException bussinessException) {
            bussinessException.printStackTrace();
            result.put("code", bussinessException.getCode());
            result.put("msg", bussinessException.getMsg());
        }


        return result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluate(Long memberId, Long bookId, Integer score, String content) {
        Map result = new HashMap();
        try {
            Evaluation eva = memberService.evaluate(memberId, bookId, score, content);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation", eva);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @PostMapping("/enjoy")
    @ResponseBody
    public Map evaluate(Long evaluationId) {
        Map result = new HashMap();
        try {
            Evaluation enjoy = memberService.enjoy(evaluationId);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation", enjoy);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }


        return result;
    }


}