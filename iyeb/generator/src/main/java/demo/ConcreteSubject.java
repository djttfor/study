package demo;

import java.util.ArrayList;
import java.util.List;

public class ConcreteSubject implements Subject {
    private final List<Observe> observes = new ArrayList<>();
    @Override
    public void attach(Observe observe) {
        observes.add(observe);
    }

    @Override
    public void detach(Observe observe) {
        observes.remove(observe);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observe observe : observes) {
            observe.update(message);
        }
    }
}
