package com.ex.server.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ex.server.dto.LoginParam;
import com.ex.server.dto.ResponseBean;
import com.ex.server.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ttfor
 * @since 2021-04-21
 */
public interface AdminService extends IService<Admin> {

    ResponseBean login(LoginParam loginParam, HttpServletRequest request, HttpServletResponse response);

    Admin selectAdminByName(String name);

    List<Admin> queryAll();

    Page<Admin> pageQuery(int current, int pageSize);

    void test();

    List<Admin> selectAll();
}
