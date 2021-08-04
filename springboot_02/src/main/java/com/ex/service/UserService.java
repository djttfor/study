package com.ex.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ex.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<User> {
    Boolean updatePassword(Long uid,String password);
    Boolean doDecrementCount(int pid,int count);
    Boolean updateCredit(@Param("pid") Long pid, @Param("credit") int credit);
}
