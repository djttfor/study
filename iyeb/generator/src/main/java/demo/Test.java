package demo;

public class Test {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        subject.attach(new SendNewPersonObserver());
        subject.notifyObservers("aaa");
    }
}
