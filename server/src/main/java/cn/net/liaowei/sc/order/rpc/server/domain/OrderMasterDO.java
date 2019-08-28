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
@ApiModel("订单主要信息")
@Table(name="order_master")
public class OrderMasterDO {
    @Id
    @ApiModelProperty("订单编号")
    private String orderMasterId;

    @ApiModelProperty("客户姓名")
    private String customerName;

    @ApiModelProperty("客户证件类型 0身份证 1其他")
    private Short customerIdType;

    @ApiModelProperty("客户证件号码")
    private String customerIdNo;

    @ApiModelProperty("订单总金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单状态 0正常 1取消")
    private Short orderStatus;

    @ApiModelProperty("支付账号")
    private String paymentAccount;

    @ApiModelProperty("支付状态 0未支付 1支付成功 2支付失败")
    private Short paymentStatus;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
