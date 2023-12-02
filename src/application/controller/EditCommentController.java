package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditCommentController {
	
	@FXML public TextField timestamp;
	
	@FXML public TextArea description;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	private CommentDAO commentDAO;
	
	public void initialize() {
		LocalDateTime currentDateTime = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
	    String formattedDateTime = currentDateTime.format(formatter);
	    
	    timestamp.setText(formattedDateTime);
	    
	    description.setText(commonObjs.getCommentText());
	}
	
	public void save() {
	    String newDesc = description.getText();
	    
	    int commentID = commonObjs.getCurrentComment();
	    
	    CommentDAO.updateComment(commentID, timestamp.getText(), newDesc);
	    
	    URL ticketBox = getClass().getClassLoader().getResource("view/TicketBox.fxml");
	    URL commentListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
	    URL displayCommentUrl = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");
	    
	    try {
	        // Load AnchorPane for the AllTickets view
	        AnchorPane pane = (AnchorPane) FXMLLoader.load(ticketBox);
	        VBox commentList = (VBox) FXMLLoader.load(commentListUrl);

	        // Retrieve the mainBox from commonObjs
	        HBox mainBox = commonObjs.getMainBox();

	        // If there is a view page in mainBox, remove it
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        // Adds the AllTickets view to the mainBox
	        mainBox.getChildren().add(pane);

	        // Add the ticketList to the AllTickets view
	        pane.getChildren().add(commentList);

	        // Clear the ticketList
	        commentList.getChildren().clear();

	        for (int commentID1 : Main.commentDao.getCommentIDs()) {
				
		    	// Retrieve projectID, ticketID, and text for each comment
		    	// get ticketprojectID here
		    	int commentTicketID = Main.commentDao.getCommentTicketByID(commentID1);
		    	int commentProjectID = Main.commentDao.getCommentProjectByID(commentID1);
				String commentText = Main.commentDao.getCommentByID(commentID1);
				String commentTime = Main.commentDao.getTimestampForComment(commentID1);
				commonObjs.setCommentText(commentText);
				commonObjs.setCommentTime(commentTime);
				
				// Check if the comment belongs to the current ticket (maybe project too)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Load the text of the comment
					AnchorPane commentDisplay = (AnchorPane) FXMLLoader.load(displayCommentUrl);
					commentList.getChildren().add(commentDisplay);
				}
			}

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void cancel() {
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
		URL commentUrl = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");
		try {
			
			// Load AnchorPane for the ProjectBox view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
					
			// Retrieve the mainBox from commonObjs
			HBox mainBox = commonObjs.getMainBox();
					
			// If there is a view page in mainBox, remove it
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
					
			// Adds pane1 to the mainBox
		    mainBox.getChildren().add(pane1);
		            
			VBox commentList = commonObjs.getCommentList();
			commentList.getChildren().clear();
					
			for (int commentID1 : Main.commentDao.getCommentIDs()) {
				
		    	// Retrieve projectID, ticketID, and text for each comment
		    	// get ticketprojectID here
		    	int commentTicketID = Main.commentDao.getCommentTicketByID(commentID1);
		    	int commentProjectID = Main.commentDao.getCommentProjectByID(commentID1);
				String commentText = Main.commentDao.getCommentByID(commentID1);
				String commentTime = Main.commentDao.getTimestampForComment(commentID1);
				commonObjs.setCommentText(commentText);
				commonObjs.setCommentTime(commentTime);
				
				// Check if the comment belongs to the current ticket (maybe project too)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Load the text of the comment
					AnchorPane commentDisplay = (AnchorPane) FXMLLoader.load(commentUrl);
					commentList.getChildren().add(commentDisplay);
				}
			}
					
			pane1.getChildren().add(commentList);
				
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
