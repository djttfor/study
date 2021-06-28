package my.service;

import my.domain.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll(int pageNum,int pageSize)throws Exception;

    String checkUrlExist(String url)throws Exception;

    int addPermission(Permission permission) throws Exception;

    Permission findPermissionByPid(String id)throws Exception;
}
