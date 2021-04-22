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
@TableName("t_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private LocalDateTime birthday;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 婚姻状况
     */
    private String wedlock;

    /**
     * 民族
     */
    private Integer nationId;

    /**
     * 籍贯
     */
    private String nationPlace;

    /**
     * 政治面貌
     */
    private Integer politicId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 部门编号
     */
    private Integer departmentId;

    /**
     * 职称id
     */
    private Integer jobLevelId;

    /**
     * 职位id
     */
    private Integer posId;

    /**
     * 聘用形式
     */
    private String engageForm;

    /**
     * 最高学历
     */
    private String tiptopDegree;

    /**
     * 所属专业
     */
    private String specialty;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 入职日期
     */
    private LocalDateTime beginDate;

    /**
     * 在职状态
     */
    private String workState;

    /**
     * 工号
     */
    private String workId;

    /**
     * 合同期限
     */
    private Double contractTerm;

    /**
     * 转正日期
     */
    private LocalDateTime conversionTime;

    /**
     * 离职日期
     */
    private LocalDateTime notWorkTract;

    /**
     * 合同起始日期
     */
    private LocalDateTime beginContract;

    /**
     * 合同终止日期
     */
    private LocalDateTime endContract;

    /**
     * 工龄
     */
    private Integer workAge;

    /**
     * 工资账套id
     */
    private Integer salaryId;


}
