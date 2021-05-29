package com.ex.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_admin")
public class Admin implements Serializable,UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "用户id",needMerge = true)
    private Integer id;

    /**
     * 姓名
     */
    @Excel(name = "用户名称",needMerge = true)
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 住宅电话
     */
    private String telephone;

    /**
     * 用户地址
     */
    private String address;


    /**
     * 是否启用
     */
    @Getter(value = AccessLevel.NONE)
    private Boolean enabled;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String userFace;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleCode;

    /**
     * 测试字段
     *
     */
    @TableField(exist = false)
    @ExcelEntity(id = "aid")
    private AdminRole adminRole;


    /**
     * 角色集合
     */
    @TableField(exist = false)
    @ExcelCollection(name = "Role集合")
    private List<Role> roles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
