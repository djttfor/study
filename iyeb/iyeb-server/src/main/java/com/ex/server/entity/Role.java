package com.ex.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("t_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "roleId",orderNum = "2")
    private Integer id;

    /**
     * 角色码
     */
    @ExcelIgnore
    private String code;

    /**
     * 角色名称
     */
    @Excel(name = "角色名称",orderNum = "2")
    private String name;

    /**
     * 创建时间
     */
    @ExcelIgnore
    private LocalDateTime gmtCreate;

    /**
     * 是否启用
     */
    @ExcelIgnore
    private Boolean enabled;

    /**
     * 角色备注
     */
    @ExcelIgnore
    private String remark;


}
