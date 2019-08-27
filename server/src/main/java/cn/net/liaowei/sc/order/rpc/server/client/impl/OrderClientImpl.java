package cn.net.liaowei.sc.order.rpc.server.client.impl;


import cn.net.liaowei.sc.order.rpc.client.OrderClient;
import cn.net.liaowei.sc.order.rpc.common.OrderDTO;
import cn.net.liaowei.sc.order.rpc.common.OrderDetailDTO;
import cn.net.liaowei.sc.order.rpc.server.domain.dos.OrderDetailDO;
import cn.net.liaowei.sc.order.rpc.server.domain.dos.OrderMasterDO;
import cn.net.liaowei.sc.order.rpc.server.enums.ErrorEnum;
import cn.net.liaowei.sc.order.rpc.server.exception.SCException;
import cn.net.liaowei.sc.order.rpc.server.repository.OrderDetailRepository;
import cn.net.liaowei.sc.order.rpc.server.repository.OrderMasterRepository;
import cn.net.liaowei.sc.order.rpc.server.util.KeyUtil;
import cn.net.liaowei.sc.product.client.ProductClient;
import cn.net.liaowei.sc.product.common.DecreaseQuotaDTO;
import cn.net.liaowei.sc.product.common.ProductInfoDTO;
import com.alipay.sofa.rpc.registry.consul.ConsulConstants;
import com.alipay.sofa.runtime.api.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LiaoWei
 */
@Service
@SofaService(
        interfaceType = ProductClient.class,
        bindings = @SofaServiceBinding(
                bindingType = "bolt",
                parameters = @SofaParameter(key = ConsulConstants.CONSUL_SERVICE_NAME_KEY, value = "${spring.application.name}")
        )
)
public class OrderClientImpl implements OrderClient {
    @SofaReference(
            binding = @SofaReferenceBinding(
                    bindingType = "bolt",
                    parameters = @SofaParameter(key = ConsulConstants.CONSUL_SERVICE_NAME_KEY, value = "product-rpc")
            ),
            jvmFirst = false
    )
    private ProductClient productClient;

    private OrderMasterRepository orderMasterRepository;
    private OrderDetailRepository orderDetailRepository;

    public OrderClientImpl(OrderMasterRepository orderMasterRepository, OrderDetailRepository orderDetailRepository) {
        this.orderMasterRepository = orderMasterRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @Transactional
    public String create(OrderDTO orderDTO) {
        // 产生主订单编号
        String orderMasterId = KeyUtil.genUniqueKey();

        // 通过订单中产品编号获取产品信息
        List<Integer> productIdList = orderDTO.getOrderDetailDTOList().stream()
                .map(OrderDetailDTO::getProductId).collect(Collectors.toList());
        List<ProductInfoDTO> productInfoDTOList = productClient.listByProductId(productIdList);

        // 扣减产品可用额度
        List<DecreaseQuotaDTO> decreaseQuotaDTOList = orderDTO.getOrderDetailDTOList().stream()
                .map(e -> new DecreaseQuotaDTO(e.getProductId(), e.getBuyAmount()))
                .collect(Collectors.toList());
        productClient.decreaseQuota(decreaseQuotaDTOList);

        // 写主订单详情
        BigDecimal orderAmount = orderDTO.getOrderDetailDTOList().stream()
                .map(OrderDetailDTO::getBuyAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        OrderMasterDO orderMasterDO = new OrderMasterDO();
        BeanUtils.copyProperties(orderDTO, orderMasterDO);
        orderMasterDO.setOrderMasterId(orderMasterId);
        orderMasterDO.setOrderAmount(orderAmount);
        orderMasterDO.setOrderStatus(Short.valueOf("0"));
        orderMasterDO.setPaymentStatus(Short.valueOf("0"));
        orderMasterRepository.save(orderMasterDO);

        // 写子订单详情
        int index = 0;
        for (OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailDTOList()) {
            for (index = 0; index < productInfoDTOList.size(); index++) {
                ProductInfoDTO productInfoDTO = productInfoDTOList.get(index);
                if (productInfoDTO.getProductId().equals(orderDetailDTO.getProductId())) {
                    OrderDetailDO orderDetailDO = new OrderDetailDO();
                    // 产生子订单编号
                    String orderDetailId = KeyUtil.genUniqueKey();
                    BeanUtils.copyProperties(productInfoDTO, orderDetailDO);
                    BeanUtils.copyProperties(orderDetailDTO, orderDetailDO);
                    orderDetailDO.setOrderMasterId(orderMasterId);
                    orderDetailDO.setOrderDetailId(orderDetailId);
                    orderDetailRepository.save(orderDetailDO);
                    break;
                }
            }
            if (index == productInfoDTOList.size()) {
                // 没有找到对应的商品信息
                throw new SCException(ErrorEnum.SAVE_DETAIL_ORDER_ERROR);
            }
        }

        // 返回主订单号
        return orderMasterId;
    }

    @Override
    @Transactional
    public List<String> delete(List<String> orderMasterIdList) {
        List<OrderMasterDO> orderMasterDOList = orderMasterRepository.findByOrderMasterIdIn(orderMasterIdList);
        for (OrderMasterDO orderMasterDO : orderMasterDOList) {
            orderMasterDO.setOrderStatus(Short.valueOf("1"));
            orderMasterRepository.save(orderMasterDO);
        }
        return orderMasterIdList;
    }

    @Override
    @Transactional
    public List<String> finish(List<String> orderMasterIdList) {
        List<OrderMasterDO> orderMasterDOList = orderMasterRepository.findByOrderMasterIdIn(orderMasterIdList);
        for (OrderMasterDO orderMasterDO : orderMasterDOList) {
            orderMasterDO.setOrderStatus(Short.valueOf("2"));
            orderMasterRepository.save(orderMasterDO);
        }
        return orderMasterIdList;
    }
}
