package cn.net.liaowei.sc.order.rpc.server.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiaoWei
 */
@Data
@ApiModel("返回数据结构")
public class ResultVO<T> {
    @ApiModelProperty("返回码")
    private String code;

    @ApiModelProperty("返回信息")
    private String message;

    @ApiModelProperty("返回数据")
    private T data;
}
