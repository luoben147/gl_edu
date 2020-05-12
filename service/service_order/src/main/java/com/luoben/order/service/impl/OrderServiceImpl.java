package com.luoben.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoben.commonutils.vo.CourseWebVoOrder;
import com.luoben.commonutils.vo.UcenterMemberVo;
import com.luoben.order.client.EduClient;
import com.luoben.order.client.UcenterClient;
import com.luoben.order.entity.Order;
import com.luoben.order.mapper.OrderMapper;
import com.luoben.order.service.OrderService;
import com.luoben.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    /**
     * 生成订单
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String creatrOrder(String courseId, String memberId) {

        //远程调用课程服务，根据课程id获取课程信息
        CourseWebVoOrder courseInfo = eduClient.getCourseInfoOrder(courseId);
        //远程调用用户服务，根据用户id获取用户信息
        UcenterMemberVo memberInfo = ucenterClient.getMemberInfo(memberId);


        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0); //订单状态（0：未支付 1：已支付）
        order.setPayType(1); //支付类型（1：微信 2：支付宝）
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
