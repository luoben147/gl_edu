package com.luoben.sms.controller;

import com.luoben.commonutils.R;
import com.luoben.sms.service.SmsService;
import com.luoben.sms.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 发送短信服务的控制器
 */
@Api(description = "发送短信服务")
@RestController
@RequestMapping("/edusms/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信验证码
    @ApiOperation(value = "发送短信验证码")
    @GetMapping("send/{phone}")
    public R sendSms(
            @ApiParam(name = "phone", value = "手机号", required = true)
            @PathVariable String phone){
        //1.从redis获取验证码，获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.如果redis获取不到，进行阿里云发送
        //生成随机值，传给阿里云短信服务进行发送
        code = RandomUtil.getFourBitRandom();
        boolean isSend = smsService.send(phone, code);
        if(isSend){
            //发送成功，把发送成功验证码存入redis 设置有效时间
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }

}
