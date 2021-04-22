package com.ex.server.service.impl;

import com.ex.server.entity.Oplog;
import com.ex.server.mapper.OplogMapper;
import com.ex.server.service.OplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作记录表 服务实现类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements OplogService {

}
