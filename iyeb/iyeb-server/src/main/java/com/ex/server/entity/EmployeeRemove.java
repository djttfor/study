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
 * 员工变动表
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_employee_remove")
public class EmployeeRemove implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工编号
     */
    private Integer eid;

    /**
     * 调动后的部门
     */
    private Integer afterDeptId;

    /**
     * 调动后的职位
     */
    private Integer afterJobId;

    /**
     * 调动日期
     */
    private LocalDateTime removeDate;

    /**
     * 调动原因
     */
    private String reason;

    /**
     * 备注
     */
    private String remark;


}
