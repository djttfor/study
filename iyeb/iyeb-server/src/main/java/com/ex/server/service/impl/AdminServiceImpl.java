package com.ex.server.service.impl;

import com.ex.server.entity.Admin;
import com.ex.server.mapper.AdminMapper;
import com.ex.server.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
