package ooad.mock;

import ooad.observer.EventType;
import ooad.observer.IObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ObserverMock implements IObserver {
    private static final Logger logger = LoggerFactory.getLogger(ObserverMock.class);
    public List<EventType> events = new ArrayList<>();
    public List<String> eventDescriptions = new ArrayList<>();

    @Override
    public void update(final EventType eventType, final String eventDescription) {
        events.add(eventType);
        eventDescriptions.add(eventDescription);
        logger.info(eventDescription);
    }
}
