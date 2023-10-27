package application.controller;

import java.io.IOException;

import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class TicketCreationController {
	@FXML
	private TextField projName;
	
	@FXML
	private TextField ticketName;
	
	@FXML
	private TextArea description;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	public void createNewTicketOp() {
		String tName = ticketName.getText();
		String desc = description.getText();
		
		if (tName.isEmpty() || desc.isEmpty()) {
			return;
		}
	}
	
	@FXML
	public void cancelNewTicketOp() {
		URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane1);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
