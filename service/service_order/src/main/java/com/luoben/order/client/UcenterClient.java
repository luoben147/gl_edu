package com.luoben.order.client;

import com.luoben.commonutils.vo.UcenterMemberVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @ApiOperation(value = "根据会员id获取登录信息")
    @GetMapping("/ucenterservice/member/getInfoUc/{id}")
    public UcenterMemberVo getMemberInfo(@PathVariable("id") String id);
}
