public class ClickOnPanelObserver {
    private EventObserver eventObserver;

    public ClickOnPanelObserver(EventObserver eventObserver) {
        this.eventObserver = eventObserver;
    }

    public EventObserver getEventObserver() {
        return eventObserver;
    }
}
