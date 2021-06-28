package com.ex.server.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "用于角色与用户id关联")
public class AdminRoleBean {
    private Integer aid;
    private List<Integer> roleIds;
}
