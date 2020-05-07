package com.luoben.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.luoben.sms.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceImpl implements SmsService {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @param code
     * @return
     */
    @Override
    public boolean send(String phone, String code) {

        if(StringUtils.isEmpty(phone)) return false;

        //替换成你的AK
        final String accessKeyId = "your accessKeyId";//你的accessKeyId
        final String accessKeySecret = "your accessKeySecret";//你的accessKeySecret
        //初始化ascClient,暂时不支持多region（请勿修改）
        DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId,
                accessKeySecret);

        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象

        //设置固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "我的罗本在线教育网站"); //阿里云短信服务里申请的签名名称
        request.putQueryParameter("TemplateCode", "SMS_189613510");    //阿里云短信服务里申请的模板code
        Map<String,Object> param=new HashMap<>();
        param.put("code",code);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); //验证码数据，转换为json数据

        //请求失败这里会抛ClientException异常
        try {
            CommonResponse response = acsClient.getCommonResponse(request);
            System.out.println(response.getData());
            System.out.println(response.getHttpResponse().isSuccess());
            return response.getHttpResponse().isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
