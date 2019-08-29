package cn.net.liaowei.sc.order.rpc.server.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


/**
 * @author LiaoWei
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlers {
    @ExceptionHandler(value = Exception.class)
    public <E> void handleException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String code = "EEEEEEE";
        String message = "系统内部错误";

        log.error("{}:{}", code, message, e);
//        return ResultUtil.error(code, message);
    }
}
