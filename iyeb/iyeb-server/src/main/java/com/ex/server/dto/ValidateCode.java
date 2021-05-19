package com.ex.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis保存验证码与验证码令牌
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCode {
    @ApiModelProperty(value = "验证码",required = true)
    private String code ;

    @ApiModelProperty(value = "验证码令牌",required = true)
    private String codeToken ;
}
