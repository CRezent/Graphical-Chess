package ooad.observer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus {
    private static EventBus instance;
    private final Map<EventType, Set<IObserver>> observers;

    private EventBus() {
        observers = new HashMap<>();
    }

    public static synchronized EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void attach(IObserver observer, EventType eventType) {
        observers.computeIfAbsent(eventType, k -> new HashSet<>()).add(observer);
    }

    public void detach(IObserver observer, EventType eventType) {
        if (observers.containsKey(eventType)) {
            observers.get(eventType).remove(observer);
        }
    }

    public void postMessage(EventType eventType, String eventDescription) {

        if (observers.containsKey(eventType)) {
            for (IObserver observer : observers.get(eventType)) {
                observer.update(eventType, eventDescription);
            }
        }

        if (observers.containsKey(EventType.ALL)) {
            for (IObserver observer : observers.get(EventType.ALL)) {
                observer.update(eventType, eventDescription);
            }
        }
    }
}
