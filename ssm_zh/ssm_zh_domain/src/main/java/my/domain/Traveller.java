package my.domain;

public class Traveller {
    private String id;
    private String name;
    private String sex;
    private String phoneNumber;
    private Integer credentialsType;
    private String credentialsTypeStr;
    private String credentialsNumber;
    private Integer travellerType;
    private String travellerTypeStr;

    @Override
    public String toString() {
        return "Traveller{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", credentialsType=" + credentialsType +
                ", credentialsTypeStr='" + credentialsTypeStr + '\'' +
                ", credentialsNumber='" + credentialsNumber + '\'' +
                ", travellerType=" + travellerType +
                ", travellerTypeStr='" + travellerTypeStr + '\'' +
                '}';
    }

    public String getTravellerTypeStr() {
        return travellerTypeStr;
    }

    public void setTravellerTypeStr(String travellerTypeStr) {
        this.travellerTypeStr = travellerTypeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCredentialsType() {
        if (credentialsType==0){
            credentialsTypeStr = "身份证";
        }else if(credentialsType==1){
            credentialsTypeStr="护照";
        }else if(credentialsType==2){
            credentialsTypeStr="军官证";
        }
        return credentialsTypeStr;
    }

    public void setCredentialsType(Integer credentialsType) {
        this.credentialsType = credentialsType;
    }

    public String getCredentialsTypeStr() {
        return credentialsTypeStr;
    }

    public void setCredentialsTypeStr(String credentialsTypeStr) {
        this.credentialsTypeStr = credentialsTypeStr;
    }

    public String getCredentialsNumber() {
        return credentialsNumber;
    }

    public void setCredentialsNumber(String credentialsNumber) {
        this.credentialsNumber = credentialsNumber;
    }

    public String getTravellerType() {
        if (travellerType==0){
            travellerTypeStr  = "成人";
        }else if (travellerType==1){
            travellerTypeStr = "小孩";
        }
        return travellerTypeStr;
    }

    public void setTravellerType(Integer travellerType) {
        this.travellerType = travellerType;
    }

 }
