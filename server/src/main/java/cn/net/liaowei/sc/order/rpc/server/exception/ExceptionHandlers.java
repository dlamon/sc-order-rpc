package cn.net.liaowei.sc.order.rpc.server.exception;

import cn.net.liaowei.sc.order.rpc.server.domain.vo.ResultVO;
import cn.net.liaowei.sc.order.rpc.server.enums.ErrorEnum;
import cn.net.liaowei.sc.order.rpc.server.util.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * @author LiaoWei
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {
    @ExceptionHandler(value = ConstraintViolationException.class)
    public <E> ResultVO<E> handleConstraintViolationException(ConstraintViolationException e) {
        String code = ErrorEnum.PARAM_CHECK_ERROR.getCode();
        String exceptionMessage = e.getMessage();
        int length = exceptionMessage.length();
        exceptionMessage = exceptionMessage.substring(exceptionMessage.lastIndexOf(":")+1, length).trim();
        String message = ErrorEnum.PARAM_CHECK_ERROR.getMessage() + "[" + exceptionMessage + "]";

        log.error("{}:{}", code, message, e);
        return ResultUtil.error(code, message);
    }

    @ExceptionHandler(value = BindException.class)
    public <E> ResultVO<E> handleBindException(BindException e) {
        return this.returnByBindingResult(e);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public <E> ResultVO<E> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return this.returnByBindingResult(e);
    }

    private <E> ResultVO<E> returnByBindingResult(Exception e) {
        String code = ErrorEnum.PARAM_CHECK_ERROR.getCode();
        String message = ErrorEnum.PARAM_CHECK_ERROR.getMessage();
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append("[");

        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        }
        if (bindingResult == null) {
            log.error("{}:{}", code, message, e);
            return ResultUtil.error(code, message);
        } else {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
            }
            sb.append("]");
            message = sb.toString();

            log.error("{}:{}", code, message, e);
            return ResultUtil.error(code, message);
        }
    }

    @ExceptionHandler(SCException.class)
    @ResponseBody
    public <E> ResultVO<E> handleSCException(HttpServletResponse response, SCException e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String code = e.getCode();
        String message = e.getMessage();

        log.error("{}:{}", code, message, e);
        return ResultUtil.error(code, message);
    }

    @ExceptionHandler(value = Exception.class)
    public <E> ResultVO<E> handleException(HttpServletResponse response, Exception e) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String code = ErrorEnum.SYSTEM_INTERNAL_ERROR.getCode();
        String message = ErrorEnum.SYSTEM_INTERNAL_ERROR.getMessage();

        log.error("{}:{}", code, message, e);
        return ResultUtil.error(code, message);
    }
}
