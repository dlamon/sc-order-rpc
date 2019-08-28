package cn.net.liaowei.sc.order.rpc.server.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LiaoWei
 */
@Data
@Entity
@ApiModel("订单详细信息")
@Table(name="order_detail")
public class OrderDetailDO {
    @Id
    @ApiModelProperty("子订单编号")
    private String orderDetailId;

    @ApiModelProperty("主订单编号")
    private String orderMasterId;

    @ApiModelProperty("产品编号")
    private Integer productId;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("购买金额")
    private BigDecimal buyAmount;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;
}
