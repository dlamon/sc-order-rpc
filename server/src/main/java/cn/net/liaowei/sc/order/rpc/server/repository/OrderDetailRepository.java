package cn.net.liaowei.sc.order.rpc.server.repository;

import cn.net.liaowei.sc.order.rpc.server.domain.dos.OrderDetailDO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author LiaoWei
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetailDO, Integer> {
}
