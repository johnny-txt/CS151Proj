package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.CommentBean;
import application.CommonObjs;
import application.data_access_objects.CommentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentCreationController {
	private CommentDAO commentDAO;
	
	@FXML
	private TextField timestamp;
	
	@FXML
	private TextArea description;
	
	// Initialize the timestamp with the current date and time
	public void initialize() {
	    LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    String formattedDateTime = currentDateTime.format(formatter);
	    
	    timestamp.setText(formattedDateTime);
	}
    // Create an instance of CommonObjs to use for whole application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	// Method triggered when "Create Comment" operation is clicked
	@FXML public void createNewCommentOp() {
		commentDAO = new CommentDAO();
		
		// Extract details from the input fields
		String tName = "";
		String time = timestamp.getText();
		String desc = description.getText();
		
		// Check if any of the fields is empty
		if (tName == null || time == null || desc.isEmpty()) {
			return;
		}
		
		// Retrieve project and ticket IDs associated with the current comment
		int projectID = commonObjs.getCurrentProject();
		int ticketID = commonObjs.getCurrentTicket();
		
		// Create a new comment and insert it to database
		CommentBean comment = new CommentBean(time, desc);
		commentDAO.insertComment(comment, projectID, ticketID);
		
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
//	    URL commentUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);

	        HBox mainBox = commonObjs.getMainBox();
	        
	        // If there is a view page, remove it
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }
	        
	        // Add new view page to mainBox
	        mainBox.getChildren().add(pane1);
	        
	        // Retrieve the commentList from commonObjs
			VBox commentList = commonObjs.getCommentList();
			
			// Clear the existing content of commentList
			commentList.getChildren().clear();
			
			// Loop through all comment IDs in the database
			for (int commentID : commentDAO.getCommentIDs()) {
				int commentTicketID = commentDAO.getCommentTicketByID(commentID);
				int commentProjectID = commentDAO.getCommentProjectByID(commentID);
				String commentText = commentDAO.getCommentByID(commentID);
				
				// Check if the comment belongs to the current ticket (maybe current project)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Create a Text node for the comment and add to box1
					Text commentTxt = new Text();
					commentTxt.setText(commentText);
					commentList.getChildren().add(commentTxt);
				}
			}
			
			// Add box1 to pane1
			pane1.getChildren().add(commentList);
        
         // Handle any exception that may occur during the view loading process
	     } catch (IOException e) {
	          e.printStackTrace();
	     }

	}
	
    // Method triggered when "Cancel Comment" operation is clicked
	@FXML public void cancelNewCommentOp() {
		
		// Gets URL of the "TicketCommentList.fxml" file to load list of comments for list
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
		
		// Gets URL of the "TicketBox.fxml" file to load comment creation page
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
			
			HBox mainBox = commonObjs.getMainBox();
			
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			VBox commentList = commonObjs.getCommentList();
		    commentList.getChildren().clear();
		    
		    for (int commentID : commentDAO.getCommentIDs()) {
		    	int commentTicketID = commentDAO.getCommentTicketByID(commentID);
				int commentProjectID = commentDAO.getCommentProjectByID(commentID);
				String commentText = commentDAO.getCommentByID(commentID);
				
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					Text commentTxt = new Text();
					commentTxt.setText(commentText);
					commentList.getChildren().add(commentTxt);
				}
			}
			
			// Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
			pane1.getChildren().add(commentList);
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
}
