package my.service.impl;

import com.github.pagehelper.PageHelper;
import my.dao.PermissionDao;
import my.domain.Permission;
import my.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;
    @Override
    public List<Permission> findAll(int pageNum,int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        return permissionDao.findAll();
    }

    @Override
    public String checkUrlExist(String url) throws Exception {
        return permissionDao.checkUrlExist(url);
    }

    @Override
    public int addPermission(Permission permission) throws Exception {
        return permissionDao.addPermission(permission);
    }

    @Override
    public Permission findPermissionByPid(String id) throws Exception {
        return permissionDao.findPermissionByPid(id);
    }
}
