package my.domain;

import my.util.date.MyDateFormat;

import java.util.Date;
import java.util.List;

public class Orders {
    private String id;
    private String orderNumber;
    private Date orderTime;
    private int peopleCount;
    private String orderDesc;
    private Integer payType;
    private int orderStatus;
    private Product product;
    private List<Traveller> travellers;
    private Member member;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id='" + id + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderTime=" + orderTime +
                ", peopleCount=" + peopleCount +
                ", orderDesc='" + orderDesc + '\'' +
                ", payType=" + payType +
                ", orderStatus=" + orderStatus +
                ", product=" + product +
                ", travellers=" + travellers +
                ", member=" + member +
                '}';
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderTime() {
        return MyDateFormat.dateToString(orderTime, "yyyy-MM-dd HH:mm");
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getPayType() {
        StringBuilder sb = new StringBuilder();
        if (payType == 0){
            sb.append("支付宝");
        }else if (payType==1){
            sb.append("微信");
        }else{
            sb.append("其他");
        }
        return sb.toString();
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderStatus() {
        StringBuilder sb = new StringBuilder();
        if (orderStatus==0){
            sb.append("未付款");
        }else if(orderStatus==1){
            sb.append("已付款");
        }
        return sb.toString();
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Traveller> getTravellers() {
        return travellers;
    }

    public void setTravellers(List<Traveller> travellers) {
        this.travellers = travellers;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
