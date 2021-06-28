package my.service;


import my.domain.Syslog;

import java.util.List;

public interface SysService {
    int saveLog(Syslog syslog);

    List<Syslog> findAll(int pageNum, int pageSize);
}
