package com.byteblogs.djttfor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName(value ="plumemo_my_img")
@Accessors(chain = true)
@Data
public class MyImg implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String imgUrl;
    private String imgName;
    private Integer imgType;
    private LocalDateTime uploadTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
