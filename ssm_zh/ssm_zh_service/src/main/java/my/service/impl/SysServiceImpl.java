package my.service.impl;

import com.github.pagehelper.PageHelper;
import my.dao.SysDao;
import my.domain.Syslog;
import my.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("sysService")
@Transactional(readOnly = false)
public class SysServiceImpl implements SysService {

    @Autowired
    SysDao sysDao;
    @Override
    @Transactional(readOnly = false)
    public int saveLog(Syslog syslog) {
        return sysDao.saveLog(syslog);
    }

    @Override
    public List<Syslog> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysDao.findAll(pageNum,pageSize);
    }
}
