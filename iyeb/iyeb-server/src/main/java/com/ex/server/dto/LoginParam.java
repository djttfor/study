package com.ex.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "登录参数类")
public class LoginParam {

    @ApiModelProperty(value = "用户名",required = true)
    private String username ;

    @ApiModelProperty(value = "密码",required = true)
    private String password ;

    @ApiModelProperty(value = "验证码",required = true)
    private String code ;

    @ApiModelProperty(value = "验证码令牌,从cookie中获取",required = true)
    private String codeToken ;
}
