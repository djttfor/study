package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("t_employee_ec")
public class EmployeeEc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    private Integer eid;

    /**
     * 奖罚日期
     */
    private LocalDateTime ecDate;

    /**
     * 奖罚原因
     */
    @TableField("ec_Reason")
    private String ecReason;

    /**
     * 奖励分
     */
    private Integer ecPoint;

    /**
     * 奖罚类别，0：将，1：罚
     */
    private Integer ecType;

    /**
     * 备注
     */
    private String remark;


}
