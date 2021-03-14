package my.service;

import my.domain.Role;
import my.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserInfo> findAll(int pageNum,int pageSize)throws Exception;

    UserInfo findById(String id)throws Exception;

    int saveUser(UserInfo userInfo) throws Exception;

    String checkUsernameExist(String username);

    /**
     * 查找用户未拥有的角色
     * @param userId uid
     * @return List<Role>
     */
    List<Role> findRolesByUser(String userId);

    int addRoleToUser(String userId, String[] ids);
}
