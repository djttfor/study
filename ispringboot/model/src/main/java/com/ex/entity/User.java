package com.ex.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Data
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private String username;
    private String password;
    private String phone;
    private String address;
}
