package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_nation")
public class Nation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 民族id
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * 民族名称
     */
    private String name;


}
