package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static service.EventEnum.CLEAR_SPACE;

public class NotifierService implements EventListener {
    
    private  Map<EventEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscribe(final EventEnum event, final EventListener listener) {
        var selectedListeners = listeners.get(event);
        selectedListeners.add(listener);
    }

    public void notify(final EventEnum event) {
        listeners.get(event).forEach(listener -> listener.update(event));
    }

    @Override
    public void update(final EventEnum event) {
        notify(event);
    }

}
