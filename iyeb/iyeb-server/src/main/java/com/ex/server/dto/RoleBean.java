package com.ex.server.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "用于角色权限的增删")
public class RoleBean {
    @ApiModelProperty(value = "角色id",required = true)
    private Integer rid;
    @ApiModelProperty(value = "角色key",required = true)
    private String code;
    @ApiModelProperty(value = "角色名称",required = true)
    private String name;
    @ApiModelProperty(value = "角色描述",required = true)
    private String remark;
    @ApiModelProperty(value = "增加的Menu路径的id",required = true)
    private List<Integer> addArr;
    @ApiModelProperty(value = "删除的Menu路径的id",required = true)
    private List<Integer> deleteArr;
}
