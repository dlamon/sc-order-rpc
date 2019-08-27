package cn.net.liaowei.sc.order.rpc.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    /**
     * 公共错误码枚举
     */
    SYSTEM_INTERNAL_ERROR("EEEEEEE", "系统内部错误"),
    PARAM_CHECK_ERROR("PRM0001", "参数检查错误"),
    /**
     * 服务错误码枚举
     */
    CREATE_ORDER_ERROR("ORD0001", "创建订单失败"),
    SAVE_DETAIL_ORDER_ERROR("ORD0002", "保存子订单信息失败"),
    CREATE_ORDER_UNKNOW_ERROR("ORD0003", "创建订单发生未知错误");


    private String code;
    private String message;
}
