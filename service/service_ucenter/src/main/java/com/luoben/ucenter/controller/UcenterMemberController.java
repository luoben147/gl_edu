package com.luoben.ucenter.controller;


import com.luoben.commonutils.JwtUtils;
import com.luoben.commonutils.R;
import com.luoben.ucenter.entity.UcenterMember;
import com.luoben.ucenter.service.UcenterMemberService;
import com.luoben.ucenter.vo.LoginVo;
import com.luoben.ucenter.vo.RegisterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author luoben
 * @since 2020-05-07
 */
@Api(description = "会员登陆注册")
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("getLoginInfo")
    public R getLoginInfo(HttpServletRequest request) {
        //根据request 头里的token获取用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);

    }
}

