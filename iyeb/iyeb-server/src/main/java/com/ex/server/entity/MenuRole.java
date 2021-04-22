package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("t_menu_role")
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单id
     */
    private Integer mid;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 备注
     */
    private String remark;


}
