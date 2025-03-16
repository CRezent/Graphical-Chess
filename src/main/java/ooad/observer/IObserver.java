package ooad.observer;

public interface IObserver {
    void update(final EventType eventType, final String eventDescription);
}
