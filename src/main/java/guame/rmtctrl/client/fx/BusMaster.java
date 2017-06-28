package guame.rmtctrl.client.fx;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guam on 28.06.2017.
 */
public class BusMaster {
	
	private static final Logger logger = LoggerFactory.getLogger(BusMaster.class); 
	
	private static final Map<String, EventBus> busMap = new HashMap<>();
	public static final String DEFAULT = "default";
	
	private BusMaster(EventBus eventBus) {}
	
	public static void register(Object object) {
		register(DEFAULT, object);
	}
	
	public static void register(String busName, Object object) {
		logger.info(String.format("registering on bus '%s' listener: %s, classloader: %s", 
		            busName, object, object.getClass().getClassLoader().toString()));
		
		EventBus eventBus = busMap.get(busName);
		if(eventBus == null) {
			eventBus = new EventBus(busName);
			busMap.put(busName, eventBus);
		}
		
		eventBus.register(object);
	}
	
	public static void unregister(Object object) {
		unregister(DEFAULT, object);
	}
	
	public static void unregister(String busName, Object object) {
		EventBus eventBus = busMap.get(busName);
		
		if(eventBus!=null) {
			eventBus.unregister(object);
		}
	}
	
	public static void post(Object object) {
		post(DEFAULT, object);
	}
	
	public static void post(String busName, Object object) {
		EventBus eventBus = busMap.get(busName);
		logger.info(String.format("posting on bus '%s' listener: %s, classloader: %s",
		                          busName, object, object.getClass().getClassLoader().toString()));
		if(eventBus!=null) {
			eventBus.post(object);
		}
	}
}
