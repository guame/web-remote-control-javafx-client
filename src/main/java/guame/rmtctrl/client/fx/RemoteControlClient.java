package guame.rmtctrl.client.fx;

import com.google.common.eventbus.Subscribe;
import org.apache.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by nick on 30/09/2015.
 */
public class RemoteControlClient {
	
	private static Logger logger = Logger.getLogger(RemoteControlClient.class);
	
	private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	private StompSession stompSession;
	
	public ListenableFuture<StompSession> connect() {
		
		Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
		List<Transport> transports = Collections.singletonList(webSocketTransport);
		
		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
		
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		
		String url = "ws://{host}:{port}/rmtctrl";
		return stompClient.connect(url, headers, new MyHandler(), "localhost", 8080);
	}
	
	public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
		stompSession.subscribe("/topic/greetings", new StompFrameHandler() {
			
			public Type getPayloadType(StompHeaders stompHeaders) {
				return byte[].class;
			}
			
			public void handleFrame(StompHeaders stompHeaders, Object o) {
				InMessage message = new InMessage(new String((byte[]) o));
				BusMaster.post(message);
			}
		});
	}
	
	@Subscribe
	public void onSendMessage(OutMessage outMessage) {
		String jsonHello = "{ \"name\" : \""+outMessage.text+"\" }";
		stompSession.send("/app/rmtctrl", jsonHello.getBytes());
	}
	
	private class MyHandler extends StompSessionHandlerAdapter {
		public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
			logger.info("Now connected");
		}
	}
	
	
	public void start() throws ExecutionException, InterruptedException {
		
		ListenableFuture<StompSession> f = connect();
		stompSession = f.get();
		BusMaster.register(this);
		logger.info("Subscribing to greeting topic using session " + stompSession);
		subscribeGreetings(stompSession);
	}
	
	public void stop() {
		BusMaster.unregister(this);
		stompSession.disconnect();
	}
	
	
	
}