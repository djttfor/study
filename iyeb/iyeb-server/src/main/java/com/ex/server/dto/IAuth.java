package com.ex.server.dto;

import com.ex.server.entity.Menu;
import com.ex.server.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "认证对象，包含角色名与权限路径")
public class IAuth {
    @ApiModelProperty(value = "角色名称",required = true)
    private String roleCode;
    @ApiModelProperty(value = "权限路径",required = true)
    private List<String> authPath;
    public IAuth(){}

    public IAuth(String roleCode,List<String> authPath){
        this.authPath = authPath;
        this.roleCode = roleCode;
    }

    public IAuth(Role role,List<Menu> menus){
        if (role != null ){
            this.roleCode = role.getCode();
        }
        if (menus!=null && menus.size()>0){
            this.authPath = new ArrayList<>();
            for (Menu menu : menus) {
                this.authPath.add(menu.getPath());
            }
        }
    }
}
