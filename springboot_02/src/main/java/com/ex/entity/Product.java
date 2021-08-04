package com.ex.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName i_product
 */
@TableName(value ="i_product")
@Accessors(chain = true)
@Data
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 总库存
     */
    private Integer totalCount;

    /**
     * 当前库存
     */
    private Integer currentCount;

    /**
     * 0表示未上架，1表示已上架，2表示已下架
     */
    private Byte isEnabled;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime gmtUpdate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}