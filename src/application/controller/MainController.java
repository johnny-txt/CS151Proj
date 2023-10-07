package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;
	@FXML public void CreateProjectOperation() {
		URL url = getClass().getClassLoader().getResource("view/ProjectCreation.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			mainBox.getChildren().add(pane1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
