/**
 * Class used to provide observation on events and storing event observer
 * */

public class ClickOnPanelObserver {
    private EventObserver eventObserver;

    public ClickOnPanelObserver(EventObserver eventObserver) {
        this.eventObserver = eventObserver;
    }

    public EventObserver getEventObserver() {
        return eventObserver;
    }
}
