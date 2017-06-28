//package guame.rmtctrl.client.fx;
//
//import com.google.common.eventbus.Subscribe;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//
///**
// * Created by guam on 28.06.2017.
// */
//public class WebSocketHandler extends TextWebSocketHandler {
//	
//	private WebSocketSession session;
//	
//	@Override
//	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//		this.session = session;
//		BusMaster.register(this);
//	}
//	
//	@Override
//	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		BusMaster.post(new InMessage(message.getPayload()));			
//	}
//	
//	@Override
//	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//		BusMaster.unregister(this);
//	}
//	
//	public WebSocketSession getSession() {
//		return session;
//	}
//	
//	@Subscribe
//	public void onSendMessage(OutMessage message) throws IOException {
//		getSession().sendMessage(new TextMessage(message.text));
//	}
//}
