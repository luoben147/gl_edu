package com.luoben.eduservice.client;

import com.luoben.commonutils.vo.UcenterMemberVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMemberVo getMemberInfo(String id) {
        //没有获取到数据 返回null
        return null;
    }
}
