package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ProjectCreationController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML 
	public void CancelNewProjectOp() {
		URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
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
