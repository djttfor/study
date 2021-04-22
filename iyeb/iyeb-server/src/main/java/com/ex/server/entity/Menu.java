package com.ex.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * url
     */
    private String url;

    /**
     * 路径
     */
    private String path;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 是否保持激活
     */
    private Boolean keepAlive;

    /**
     * 是否要求权限
     */
    private Boolean requireAuth;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 是否启用
     */
    private Boolean enabled;


}
