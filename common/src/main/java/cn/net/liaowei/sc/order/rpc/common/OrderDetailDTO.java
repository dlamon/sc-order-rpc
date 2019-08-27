package cn.net.liaowei.sc.order.rpc.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author LiaoWei
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("订单详情数据转换对象")
public class OrderDetailDTO {
    @ApiModelProperty("产品编号")
    private Integer productId;

    @ApiModelProperty("购买金额")
    private BigDecimal buyAmount;
}
