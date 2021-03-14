package my.domain;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Account implements Serializable {
    private Integer aid;
    private Integer uid;
    private String acname;
    private String password;
    private Double money;

    @Override
    public String toString() {
        return "AccountDao{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", acname='" + acname + '\'' +
                ", password='" + password + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAcname() {
        return acname;
    }

    public void setAcname(String acname) {
        this.acname = acname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
