package cn.net.liaowei.sc.order.rpc.server.repository;

import cn.net.liaowei.sc.order.rpc.server.domain.OrderMasterDO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author LiaoWei
 */
public interface OrderMasterRepository extends JpaRepository<OrderMasterDO, Integer> {
    /**
     * 通过主订单编号列表查询编号列表对应的主订单信息列表
     * @param orderMasterIdList 主订单编号列表
     * @return 主订单信息列表
     */
    List<OrderMasterDO> findByOrderMasterIdIn(List<String> orderMasterIdList);
}
