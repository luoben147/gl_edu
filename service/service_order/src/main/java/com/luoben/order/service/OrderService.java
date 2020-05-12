package com.luoben.order.service;

import com.luoben.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
public interface OrderService extends IService<Order> {

    String creatrOrder(String courseId, String memberId);
}
