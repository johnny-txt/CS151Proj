package application.controller;

import java.net.URL;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class CommentCreationController {
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	public void initialize() {
		date.setValue(LocalDate.now());
	}
	
	@FXML
	public void createNewCommentOp() {
		
	}
	
	@FXML
	public void cancelNewCommentOp() {
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");

	}
}
