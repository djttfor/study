package com.byteblogs.plumemo.auth.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.byteblogs.plumemo.auth.domain.po.AuthToken;
import com.byteblogs.common.base.dao.BaseDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author byteblogs
 * @since 2019-09-18
 */
public interface AuthTokenDao extends BaseDao<AuthToken> {

}
