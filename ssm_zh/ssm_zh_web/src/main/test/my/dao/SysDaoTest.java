package my.dao;

import my.domain.Syslog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class SysDaoTest {
    @Autowired
    SysDao sysDao;
    @Test
    public void saveLog(){
        Syslog syslog = new Syslog();
        syslog.setVisitor("呵呵");
        syslog.setIp("19999");
        syslog.setVisitTime(new Date());
        syslog.setUrl("/aaa/bbb");
        syslog.setExecutionTime(10086);
        syslog.setMethod("my.controller.OrdersController.findAll");
        sysDao.saveLog(syslog);
    }
    @Test
    public void findAll(){
        List<Syslog> all = sysDao.findAll(1, 4);
        for (Syslog syslog : all) {
            System.out.println(syslog);
        }
    }
}
