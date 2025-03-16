package ooad.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudibleObserver implements IObserver {
    private static final Logger logger = LoggerFactory.getLogger(AudibleObserver.class);
    private final int delayInSeconds;

    public AudibleObserver(int delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public void update(final EventType eventType, final String eventDescription) {
        // NOTE: our team is working on Windows and is using the nircmd package to speak, switch the cmd to line below for Unix
        // String[] cmd = {"say", eventDescription};
        String[] cmd = {"src/main/resources/nircmd.exe", "speak", "text", eventDescription};

        try{
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e){
            logger.error("Audible Observer Speech Error: {}", e.getMessage());
        }

        try{
            Thread.sleep(this.delayInSeconds * 1000L);
        } catch (Exception e){
            logger.error("Audible Observer Sleep Error: {}", e.getMessage());
        }
    }
}
