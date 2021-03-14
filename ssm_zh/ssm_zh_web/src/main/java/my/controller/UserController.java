package my.controller;

import com.github.pagehelper.PageInfo;
import my.domain.Role;
import my.domain.UserInfo;
import my.service.UserService;
import my.util.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * 查询所有用户
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @RequestMapping("/all")
    public ModelAndView findAll(@RequestParam(name = "pageNum",required = true,defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize",required = true,defaultValue = "4") int pageSize) throws Exception {
        List<UserInfo> userInfos = userService.findAll(pageNum,pageSize);
        PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(userInfos);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("user_list");
        return mv;
    }
    @RequestMapping("/show")
    public ModelAndView showUserInfo(@RequestParam(name = "id",required = true) String id) throws Exception {
        UserInfo user = userService.findById(id);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.setViewName("user_show");
        return mv;
    }
    @RequestMapping("/save")
    public String saveUser(UserInfo userInfo) throws Exception {
        String username = userInfo.getUsername();
        if ("".equals(username) || username==null){
            throw new MyException("用户名不能为空");
        }
        String cname = userService.checkUsernameExist(username);
        if(cname!=null){
            throw new MyException("用户名已存在,请重新输入");
        }else{
            userService.saveUser(userInfo);
        }
        return "forward:all";
    }

    /**
     * 查询出用户未拥有的角色,然后跳转到添加角色页面
     * @param userId
     * @return
     */
    @RequestMapping("/addRole")
    public ModelAndView addRoleToUser(@RequestParam(name = "id",required = true) String userId){
        List<Role> roleList = userService.findRolesByUser(userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("userId", userId);
        mv.addObject("roleList", roleList);
        mv.setViewName("user-role-add");
        return mv;
    }
    @RequestMapping("addRoleToUser")
    public String addRoleToUser (String userId,String[] ids) throws Exception {
        userService.addRoleToUser(userId,ids);
        return "forward:all";
    }
}
