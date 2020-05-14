package com.luoben.ucenter.service;

import com.luoben.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luoben.ucenter.vo.LoginVo;
import com.luoben.ucenter.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author luoben
 * @since 2020-05-07
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
