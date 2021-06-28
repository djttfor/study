package demo;

public class ConcreteObserve implements Observe {
    @Override
    public void update(String message) {
        System.out.println("收到了消息："+message);
    }
}
