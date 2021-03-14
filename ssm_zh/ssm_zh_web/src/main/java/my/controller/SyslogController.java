package my.controller;

import my.domain.Syslog;
import my.service.SysService;
import my.util.controller.utils.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/syslog")
@Transactional(readOnly = false)
public class SyslogController {
    @Autowired
    SysService sysService;
    @Autowired
    MyUtil<Syslog> myUtil;
    @RequestMapping("/all")
    @Transactional(readOnly = false)
    public ModelAndView findAll(@RequestParam(name = "pageNum",required = true,defaultValue = "1") int pageNum,
                                @RequestParam(name = "pageSize",required = true,defaultValue = "20") int pageSize){
        List<Syslog> syslogList = sysService.findAll(pageNum,pageSize);
        return myUtil.findAllPaging(syslogList, "syslog-list");
    }
}
