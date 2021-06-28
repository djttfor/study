package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("t_salary")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 基本工资
     */
    private Double basicSalary;

    /**
     * 奖金
     */
    private Double bonus;

    /**
     * 午餐补助
     */
    private Double lunchSalary;

    /**
     * 交通补助
     */
    private Double trafficSalary;

    /**
     * 应发工资
     */
    private Double allSalary;

    /**
     * 养老基金数
     */
    private Double pensionBase;

    /**
     * 养老金比率
     */
    private Double pensionPer;

    /**
     * 启用时间
     */
    private LocalDateTime createDate;

    /**
     * 医疗基数
     */
    private Integer medicalBase;

    /**
     * 医疗保险比率
     */
    private Float medicalPer;

    /**
     * 公积金基数
     */
    private Integer accumulationFundBase;

    /**
     * 公积金比率
     */
    private Float accumulationFundPer;


}
