package com.imooc.reader.task;

import com.imooc.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @ClassName ComputerTask
 * @Description TODO
 * @Author changYU
 * @Date 2021/10/19 21:41
 * @Version 1.0
 **/
@Component
public class ComputerTask {
    @Resource
    private BookService bookService;

    //对以下方法进行任务调度
    //在每分钟0秒的时候自动执行下面的定时任务
    @Scheduled(cron = "0 * * * * ?")
    public void updataEvaluation() {
        bookService.updataEvaluation();
        System.out.println("短评已经更新");
    }


}
