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
@TableName("t_position")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 职位
     */
    @Excel(name = "学生姓名", width = 30)
    private String name;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间",width = 30, format = "yyyy-MM-dd HH:mm:ss.sss")
    private LocalDateTime createDate;

    /**
     * 是否启用
     */
    @Excel(name = "是否启用", replace = {"启用_true","停用_false"})
    private Boolean enabled;


}
