package com.luoben.order.service;

import com.luoben.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    Map<String,String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String,String> map);
}
