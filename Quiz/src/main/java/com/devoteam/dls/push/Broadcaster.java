package com.devoteam.dls.push;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vaadin.ui.UI;

public class Broadcaster implements Serializable {

    static ExecutorService executorService = Executors
            .newSingleThreadExecutor();

    private static Map<UI, BroadcastListener> listeners = new HashMap<>();

    public static synchronized void register(UI ui, BroadcastListener listener) {
        listeners.put(ui, listener);
    }

    public static synchronized void unregister(UI ui) {
        listeners.remove(ui);
    }

    public static synchronized void broadcast(final String message) {
        for (final Map.Entry<UI, BroadcastListener> entry : listeners
                .entrySet()) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {
                    entry.getValue().receiveBroadcast(entry.getKey(), message);
                }
            });
        }
    }

    public interface BroadcastListener {
        void receiveBroadcast(UI ui, String message);
    }
}
/*public class Broadcaster {

	  private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();

	  public static void register(BroadcastListener listener) {
	    listeners.add(listener);
	  }

	  public static void unregister(BroadcastListener listener) {
	    listeners.remove(listener);
	  }

	  public static void broadcast(final String message) {
	    for (BroadcastListener listener : listeners) {
	      listener.receiveBroadcast(message);
	    }
	  }

	  public interface BroadcastListener {
	    public void receiveBroadcast(String message);
	  }
	}*/