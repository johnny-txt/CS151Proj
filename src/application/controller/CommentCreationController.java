package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommentBean;
import application.CommonObjs;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentCreationController {
	private CommentDAO commentDAO;
	
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
		commentDAO = new CommentDAO();
		String tName = "";
		LocalDate theDate = date.getValue();
		String desc = description.getText();
		
		if (tName == null || theDate == null || desc.isEmpty()) {
			return;
		}
		
		int ticketID = TicketDAO.getTicketIDByName(tName);
		CommentBean comment = new CommentBean(theDate, desc);
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
