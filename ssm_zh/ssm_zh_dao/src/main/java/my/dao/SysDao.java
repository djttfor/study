package my.dao;

import my.domain.Syslog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysDao {
    @Insert("insert into sysLog (visitTime,visitor,ip,url,executionTime,method)" +
            " values(#{visitTime},#{visitor},#{ip},#{url},#{executionTime},#{method})")
    int saveLog(Syslog syslog);


    @Select("select * from sysLog")
    @Results(id = "sysDaoMap",value = {
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "visitTime",column = "visitTime"),
            @Result(property = "visitor",column = "visitor"),
            @Result(property = "ip",column = "ip"),
            @Result(property = "url",column = "url"),
            @Result(property = "executionTime",column = "executionTime"),
            @Result(property = "method",column = "method"),
    })
    List<Syslog> findAll(int pageNum, int pageSize);
}
