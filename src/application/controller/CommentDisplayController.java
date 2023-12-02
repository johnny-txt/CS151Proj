package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentDisplayController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML
	public Button commentEdit;
	public Button commentDelete;
	public Text commentText;
	
	public void initialize() {
		commentText.setText(commonObjs.getCommentText());
	}
	
	// Method triggered when the ticketButton is clicked
	@FXML public void editComment() {
		System.out.println(commentText.getText());
	}
	public void changeCommentText(String newText) {
		commentText.setText(newText);
	}
	@FXML public void deleteComment() {
		URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL commentListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
		
		try {
        	int commentId = CommentDAO.getCommentID(commonObjs.getCurrentProject(), commonObjs.getCurrentTicket(), commentText.getText());
        	CommentDAO.deleteComment(commentId);
			
			
	        // Load the AnchorPane for displaying page for comment creation
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			// Load and set up the VBox for displaying comments
			VBox commentList = (VBox) FXMLLoader.load(commentListUrl);
			commonObjs.setCommentList(commentList);
			
			
			commentList = commonObjs.getCommentList();
		    commentList.getChildren().clear();
		    
		    // Iterate through all comment IDs in the database
		    for (int commentID : Main.commentDao.getCommentIDs()) {
				
		    	// Retrieve projectID, ticketID, and text for each comment
		    	// get ticketprojectID here
		    	int commentTicketID = Main.commentDao.getCommentTicketByID(commentID);
		    	int commentProjectID = Main.commentDao.getCommentProjectByID(commentID);
				String commentText = Main.commentDao.getCommentByID(commentID);
				
				// Check if the comment belongs to the current ticket (maybe project too)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Load the text of the comment
					Text commentTxt = new Text();
					commentTxt.setText(commentText);
					commentList.getChildren().add(commentTxt);
				}
			}
			
			pane1.getChildren().add(commentList);
			
			// Access the main HBox for CommonObjs
	        HBox mainBox = commonObjs.getMainBox();
	        
	        // Remove the existing view from mainBox
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }
	        
	        //
	        mainBox.getChildren().add(pane1);
	        System.out.println("Current Project: " + commonObjs.getCurrentProject());
	        System.out.println("Current Ticket: " + commonObjs.getCurrentTicket());
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}

}
