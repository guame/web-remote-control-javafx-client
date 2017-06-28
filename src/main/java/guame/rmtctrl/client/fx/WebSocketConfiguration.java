//package guame.rmtctrl.client.fx;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.client.WebSocketConnectionManager;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//
///**
// * Created by guam on 28.06.2017.
// */
//@Configuration
//public class WebSocketConfiguration {
//	private static final String WS_URI = "ws://localhost:8080/rmtctrl";
//	
//	@Bean
//	public WebSocketConnectionManager connectionManager() {
//		WebSocketConnectionManager manager = new WebSocketConnectionManager(client(), handler(), WS_URI);
//		manager.setAutoStartup(true);
//		return manager;
//	}
//	
//	@Bean
//	public StandardWebSocketClient client() {
//		return new StandardWebSocketClient();
//	}
//	
//	@Bean
//	public WebSocketHandler handler() {
//		return new WebSocketHandler();
//	}
//}
