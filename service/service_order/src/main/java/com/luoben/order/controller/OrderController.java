package com.luoben.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luoben.commonutils.JwtUtils;
import com.luoben.commonutils.R;
import com.luoben.order.entity.Order;
import com.luoben.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-05-11
 */
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @param courseId
     * @return
     */
    @PostMapping("creatrOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){

       String orderNo= orderService.creatrOrder(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }


    /**
     * 订单id查询订单信息
     */
    @GetMapping("getOrder/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程id，用户id,查询订单状态
     */
    @GetMapping("isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,@PathVariable String courseId) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<Order>().eq("member_id", memberId).eq("course_id", courseId).eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }


    @ApiOperation(value = "获取订单分页列表")
    @GetMapping("{page}/{limit}")
    public R pageBanner(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<Order> pageParam = new Page<>(page, limit);
        orderService.page(pageParam,null);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

}

