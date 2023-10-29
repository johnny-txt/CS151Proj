package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.CommentBean;
import application.CommonObjs;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CommentCreationController {
	private CommentDAO commentDAO;
	
	@FXML
	private TextField timestamp;
	
	@FXML
	private TextArea description;
	
	public void initialize() {
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    String formattedDateTime = currentDateTime.format(formatter);
	    
	    timestamp.setText(formattedDateTime);
	}
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	public void createNewCommentOp() {
		commentDAO = new CommentDAO();
		String tName = "";
		String time = timestamp.getText();
		String desc = description.getText();
		
		if (tName == null || time == null || desc.isEmpty()) {
			return;
		}
		
		int ticketID = TicketDAO.getTicketIDByName(tName);
		CommentBean comment = new CommentBean(time, desc);
		commentDAO.insertComment(comment, ticketID);
		
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
//	    URL commentUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		
		try {
	        VBox box1 = (VBox) FXMLLoader.load(url);

	        HBox mainBox = commonObjs.getMainBox();

	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        mainBox.getChildren().add(box1);

			    VBox commentList = commonObjs.getCommentList();
			    commentList.getChildren().clear();
			    
			    
//			    Text ticketText = new Text(tName);
//			    commentList.getChildren().add(ticketText);
        
	     } catch (IOException e) {
	          e.printStackTrace();
	     }

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
