package com.luoben.sms.service;

public interface SmsService {
    boolean send(String phone, String code);
}
