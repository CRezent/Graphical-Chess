package ooad.observer;

import java.util.List;

public interface IObservable {
    void attach(final IObserver observer, final List<EventType> eventTypes);
    void detach(final IObserver observer, final List<EventType> eventTypes);
}
