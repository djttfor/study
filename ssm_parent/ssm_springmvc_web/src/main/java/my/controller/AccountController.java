package my.controller;

import my.domain.Account;
import my.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/account")
public class AccountController {
    @Resource(name = "accountService")
    private AccountService accountService;
    @RequestMapping("/findAll")
    public ModelAndView findAllAccount(){
        ModelAndView mv = new ModelAndView();
        //查询所有用户
        List<Account> accounts = accountService.findAll();
        //设置数据
        mv.addObject("allAccount", accounts);
        //设置跳转的页面
        mv.setViewName("success");
        return mv;
    }
    @RequestMapping("/save")
    public ModelAndView saveAccount(Account account){
        ModelAndView mv = new ModelAndView();
        System.out.println(account);
        int result = 0;
        try {
            result = accountService.saveAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            result=-1;
        }
        mv.addObject("result", result);
        mv.setViewName("result");
        return mv;
    }
    @RequestMapping("/transfer")
    public ModelAndView transfer(Integer out,Integer in,Double money){
        ModelAndView mv = new ModelAndView();
        String message = null;
        int transfer = accountService.transfer(out, in, money);
        if (transfer==3){
            message = "余额不足";
        }else if (transfer==-1){
            message = "转账出错,请联系管理员";
        }else{
            message = "转账成功";
        }
        mv.addObject("message", message);
        mv.setViewName("result");
        return mv;
    }
}
