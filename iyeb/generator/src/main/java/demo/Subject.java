package demo;

public interface Subject {
    //订阅
    void attach(Observe observe);
    //取消订阅
    void detach(Observe observe);
    //发布动态，通知观察者
    void notifyObservers(String message);
}
