package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import application.CommonObjs;
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
	
	private CommentDAO commentDAO;
	
	@FXML
	public Button commentEdit;
	public Button commentDelete;
	public Text commentText;
	
	public void initialize() {
		commentText.setText(commonObjs.getCommentText());
	}
	
	@FXML public void editComment() {
		URL url = getClass().getClassLoader().getResource("view/CommentInfo.fxml");
		
		try {
			// Loads and AnchorPane for the ProjectCreation view
			commonObjs.setCurrentComment(CommentDAO.getCommentID(commonObjs.getCurrentProject(), commonObjs.getCurrentTicket(), commentText.getText()));
			commonObjs.setCommentText(commentText.getText());
			System.out.println(commonObjs.getCommentText());
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			int projectID = commonObjs.getCurrentProject();
			System.out.println(projectID);
			
			// Retrieve comment information by name
			String comment = commentDAO.getCommentByID(projectID);
			
			
	        // Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
	
	@FXML public void deleteComment() {
		URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL ticketListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
		URL commentUrl = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");
		
		try {
        	commonObjs.setCurrentComment(CommentDAO.getCommentID(commonObjs.getCurrentProject(), commonObjs.getCurrentTicket(), commentText.getText()));
        	System.out.println(commonObjs.getCurrentComment());
        	CommentDAO.deleteComment(commonObjs.getCurrentComment());
        	
            AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
    		
			// Adds pane1 to the mainBox
		    mainBox.getChildren().add(pane);
		            
			VBox commentList = commonObjs.getCommentList();
			commentList.getChildren().clear();
					
			for (int commentID : commentDAO.getCommentIDs()) {
				int commentTicketID = commentDAO.getCommentTicketByID(commentID);
				int commentProjectID = commentDAO.getCommentProjectByID(commentID);
				String commentText = commentDAO.getCommentByID(commentID);
				commonObjs.setCommentText(commentText);
				
				// Check if the comment belongs to the current ticket (maybe current project)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					AnchorPane commentDisplay = (AnchorPane) FXMLLoader.load(commentUrl);
					commentList.getChildren().add(commentDisplay);
				}
			}
					
			pane.getChildren().add(commentList);
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
