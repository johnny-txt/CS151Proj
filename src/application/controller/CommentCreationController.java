package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommentBean;
import application.CommonObjs;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommentCreationController {
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	public void initialize() {
		date.setValue(LocalDate.now());
	}
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	public void createNewCommentOp() {
		LocalDate theDate = date.getValue();
		String desc = description.getText();
		
		if (theDate == null || desc.isEmpty()) {
			return;
		}
		
		CommentBean comment = new CommentBean(theDate, desc);
		
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	}
	
	@FXML
	public void cancelNewCommentOp() {
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		try {
			// Loads and AnchorPane for the HomepageWelcome view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			// Retrieve the mainBox from the commonObjs instance
			HBox mainBox = commonObjs.getMainBox();
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
}
