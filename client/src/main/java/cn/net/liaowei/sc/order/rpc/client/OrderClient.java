package cn.net.liaowei.sc.order.rpc.client;

import cn.net.liaowei.sc.order.rpc.common.OrderDTO;

import java.util.List;

public interface OrderClient {
    /**
     * 创建订单
     * @param OrderDTO 订单数据
     * @return 创建的订单编号
     */
    String create(OrderDTO orderDTO);

    /**
     * 删除订单
     * @param ids 订单列表
     * @return 删除的订单列表
     */
    List<String> delete(List<String> ids);

    /**
     * 完结订单
     * @param ids 订单列表
     * @return 删除的订单列表
     */
    List<String> finish(List<String> ids);
}
