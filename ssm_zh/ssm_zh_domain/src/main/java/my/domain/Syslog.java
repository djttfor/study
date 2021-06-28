package my.domain;

import my.util.date.MyDateFormat;

import java.util.Date;

public class Syslog {
    private String id ;
    private Date visitTime;
    private String visitor;
    private String ip;
    private String url;
    private long executionTime;
    private String method;

    @Override
    public String toString() {
        return "Syslog{" +
                "id='" + id + '\'' +
                ", visitTime=" + visitTime +
                ", visitor='" + visitor + '\'' +
                ", ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                ", executionTime=" + executionTime +
                ", method='" + method + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitTime() {
        return MyDateFormat.dateToString(visitTime, "yyyy-MM-dd HH:mm:ss");
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public String getVisitor() {
        return this.visitor;
    }

    public void setVisitor(String visitor) {
        this.visitor = visitor;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
