package cn.net.liaowei.sc.order.rpc.server.exception;

import cn.net.liaowei.sc.order.rpc.server.enums.ErrorEnum;
import lombok.Getter;

//import com.netflix.hystrix.cn.net.liaowei.sc.order.rpc.server.exception.HystrixBadRequestException;

/**
 * @author LiaoWei
 */
@Getter
public class SCException extends RuntimeException {
    private String code;
    private String message;

    private static String getMessage(ErrorEnum errorEnum, String extendMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(errorEnum.getMessage());
        if(extendMessage != null && !"".equals(extendMessage)) {
            sb.append("[").append(extendMessage).append("]");
        }
        return sb.toString();
    }

    public SCException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public SCException(ErrorEnum errorEnum, String extendMessage) {
        super(SCException.getMessage(errorEnum, extendMessage));
        this.code = errorEnum.getCode();
        this.message = super.getMessage();
    }

    public SCException(ErrorEnum errorEnum) {
        super(SCException.getMessage(errorEnum, null));
        this.code = errorEnum.getCode();
        this.message = super.getMessage();
    }

    public SCException(ErrorEnum errorEnum, Throwable cause) {
        super(SCException.getMessage(errorEnum, null), cause);
        this.code = errorEnum.getCode();
        this.message = super.getMessage();
    }

    public SCException(ErrorEnum errorEnum, String extendMessage, Throwable cause) {
        super(SCException.getMessage(errorEnum, extendMessage), cause);
        this.code = errorEnum.getCode();
        this.message = super.getMessage();
    }
}
