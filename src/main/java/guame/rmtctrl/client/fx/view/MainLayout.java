package guame.rmtctrl.client.fx.view;

import com.google.common.eventbus.Subscribe;
import guame.rmtctrl.client.fx.BusMaster;
import guame.rmtctrl.client.fx.InMessage;
import guame.rmtctrl.client.fx.OutMessage;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Created by guam on 28.06.2017.
 */
public class MainLayout extends VBox {
	
	Button sendButton;
	TextArea receivedMessage;
	TextField sendMessage;
	
	
	
	@Subscribe
	public void onMessage(InMessage inMessage) {
		receivedMessage.appendText(inMessage.text);
		receivedMessage.appendText("\n");
	}
	
	public MainLayout() {
		sendButton = new Button("Send");
		receivedMessage = new TextArea();
		sendMessage = new TextField();
		getChildren().addAll(sendMessage, sendButton, receivedMessage);
		BusMaster.register(this);
		
		sendButton.setOnMouseClicked(event -> {
			OutMessage message = new OutMessage(sendMessage.getText());
			BusMaster.post(message);
			sendMessage.clear();
		});
		
	}
}
