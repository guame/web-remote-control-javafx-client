package guame.rmtctrl.client.fx;

import guame.rmtctrl.client.fx.view.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by guam on 28.06.2017.
 */

public class ClientApp extends Application {
	
	RemoteControlClient remoteControlClient = new RemoteControlClient();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		remoteControlClient.start();
		stage.setScene(new Scene(new MainLayout()));
		stage.show();
		
	}
	
	
}
