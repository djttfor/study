package com.ex.server.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
@TableName("t_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name = "ID", width = 10)
    private Integer id;

    /**
     * 姓名
     */
    @Excel(name = "姓名", width = 20)
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别", width = 10)
    private String gender;

    /**
     * 生日
     */
    @Excel(name = "创建时间",width = 30, format = "yyyy-MM-dd")
    private LocalDateTime birthday;

    /**
     * 身份证号
     */
    @Excel(name = "身份证号", width = 20)
    private String idCard;

    /**
     * 婚姻状况
     */
    @Excel(name = "婚姻状况", width = 20)
    private String wedlock;

    /**
     * 民族
     */
    @Excel(name = "民族代号", width = 10)
    private Integer nationId;

    /**
     * 籍贯
     */
    @Excel(name = "籍贯", width = 10)
    private String nationPlace;

    /**
     * 政治面貌
     */
    @Excel(name = "政治面貌代号", width = 10)
    private Integer politicId;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", width = 20)
    private String email;

    /**
     * 手机
     */
    @Excel(name = "手机", width = 20)
    private String phone;

    /**
     * 地址
     */
    @Excel(name = "地址", width = 20)
    private String address;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号", width = 10)
    private Integer departmentId;

    /**
     * 职称id
     */
    @Excel(name = "职称id", width = 10)
    private Integer jobLevelId;

    /**
     * 职位id
     */
    @Excel(name = "职位id", width = 10)
    private Integer posId;

    /**
     * 聘用形式
     */
    @Excel(name = "聘用形式", width = 20)
    private String engageForm;

    /**
     * 最高学历
     */
    @Excel(name = "最高学历", width = 20)
    private String tiptopDegree;

    /**
     * 所属专业
     */
    @Excel(name = "所属专业", width = 20)
    private String specialty;

    /**
     * 毕业院校
     */
    @Excel(name = "毕业院校", width = 20)
    private String school;

    /**
     * 入职日期
     */
    @Excel(name = "入职日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginDate;

    /**
     * 在职状态
     */
    @Excel(name = "在职状态", width = 20)
    private String workState;

    /**
     * 工号
     */
    @Excel(name = "工号", width = 20)
    private String workId;

    /**
     * 合同期限
     */
    @Excel(name = "合同期限", width = 20)
    private Double contractTerm;

    /**
     * 转正日期
     */
    @Excel(name = "转正日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime conversionTime;

    /**
     * 离职日期
     */
    @Excel(name = "离职日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime notWorkTract;

    /**
     * 合同起始日期
     */
    @Excel(name = "合同起始日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginContract;

    /**
     * 合同终止日期
     */
    @Excel(name = "合同终止日期",width = 30, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endContract;

    /**
     * 工龄
     */
    @Excel(name = "工龄",width = 10)
    private Integer workAge;

    /**
     * 工资账套id
     */
    @Excel(name = "工资账套id",width = 20)
    private Integer salaryId;


}
