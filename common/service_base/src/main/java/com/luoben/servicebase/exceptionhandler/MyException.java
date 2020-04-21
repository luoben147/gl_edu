package com.luoben.servicebase.exceptionhandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException{

    @ApiModelProperty(value = "状态码")
    private Integer code;//状态码
    private String msg;//异常信息


    @Override
    public String toString() {
        return "MyException{" +
        "message=" + this.getMessage() +
        ", code=" + code +
        '}';
    }
}
