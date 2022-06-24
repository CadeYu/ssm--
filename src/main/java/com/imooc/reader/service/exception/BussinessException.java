package com.imooc.reader.service.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ClassName BussinessException
 * @Description 与业务相关的异常
 * @Author changYU
 * @Date 2021/10/17 17:43
 * @Version 1.0
 **/
@Getter
@Setter
@ToString
public class BussinessException extends RuntimeException {

    private String code;

    private String msg;

    public BussinessException(String code, String msg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }


}
