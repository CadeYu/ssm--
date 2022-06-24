package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @ClassName KaptchaController
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/16 20:45
 * @Version 1.0
 **/
@SuppressWarnings("all")
@Controller
public class KaptchaController {


    @Resource
    private Producer kaptchaProducer;


    /**
     * 功能描述
     *
     * @param request
     * @param response
     * @Param:
     * @Return: void
     * @Author: changYu
     * @Date: 2021/10/16 20:48
     * @Description: 生成验证码图片
     */
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //设置过期时间,响应立即过期
        response.setDateHeader("Expires", 0);

        //清除浏览器缓存
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        //设置响应的格式
        response.setContentType("image/png");


        //生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode", verifyCode);
        //在后台输出验证码
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));


        //根据创建验证码字符文本，创建验证码图片
        BufferedImage image = kaptchaProducer.createImage(verifyCode);


        //向浏览器输出图片
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "png", outputStream);


        //对IO流进行处理
        outputStream.flush();
        outputStream.close();


    }


}
