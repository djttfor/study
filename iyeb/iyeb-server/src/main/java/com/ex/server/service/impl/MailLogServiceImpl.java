package com.ex.server.service.impl;

import com.ex.server.entity.MailLog;
import com.ex.server.mapper.MailLogMapper;
import com.ex.server.service.MailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements MailLogService {

}
