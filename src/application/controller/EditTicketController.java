package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditTicketController {
	
	@FXML
	private Label ticketName;
	
	@FXML
	public ComboBox<String> projectDropdown;
	
	@FXML
	private TextField name;
	
	@FXML
	private TextArea description;
	
	public CommentDAO commentDAO;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML public void loadProjects() {
		projectDropdown.getItems().clear();
		
		// Retrieve project names from the database and sort them in ascending order
		List<String> projNames = ProjectDAO.getProjectNames();
		Collections.sort(projNames, String.CASE_INSENSITIVE_ORDER);
		
		// Add project names to the dropdown
		for (String name : projNames) {
			projectDropdown.getItems().add(name);
		}
	}
	
	public void initialize() {
		projectDropdown.setValue(ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(commonObjs.getCurrentTicket())));
		ticketName.setText(TicketDAO.getTicketNameByID(commonObjs.getCurrentTicket()));
		name.setText(TicketDAO.getTicketNameByID(commonObjs.getCurrentTicket()));
		description.setText(TicketDAO.getTicketDescByID(commonObjs.getCurrentTicket()));
	}
	
	public void saveTicket() {
	    String newTicketName = name.getText();
	    String newDesc = description.getText();
		String pName = projectDropdown.getValue();
	    int projectID = ProjectDAO.getProjectIDByName(pName);
	    commonObjs.setCurrentProject(projectID);
	    
	    int ticketID = commonObjs.getCurrentTicket();
	    
	    TicketDAO.updateTicket(ticketID, projectID, newTicketName, newDesc);

	    URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
	    URL ticketListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
	    URL commentUrl = getClass().getClassLoader().getResource("view/CommentDisplay.fxml");

	    try {
	        // Load AnchorPane for the AllTickets view
	        AnchorPane Pane = (AnchorPane) FXMLLoader.load(url);
	        VBox commentList = (VBox) FXMLLoader.load(ticketListUrl);

	        // Retrieve the mainBox from commonObjs
	        HBox mainBox = commonObjs.getMainBox();

	        // If there is a view page in mainBox, remove it
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        // Adds the AllTickets view to the mainBox
	        mainBox.getChildren().add(Pane);

	        // Add the ticketList to the AllTickets view
	        Pane.getChildren().add(commentList);

	        // Clear the ticketList
	        commentList.getChildren().clear();

	        for (int commentID : Main.commentDao.getCommentIDs()) {
	        	int commentTicketID = Main.commentDao.getCommentTicketByID(commentID);
		    	int commentProjectID = Main.commentDao.getCommentProjectByID(commentID);
				String commentText = Main.commentDao.getCommentByID(commentID);
				String commentTime = Main.commentDao.getTimestampForComment(commentID);
				commonObjs.setCommentText(commentText);
				commonObjs.setCommentTime(commentTime);

	            // Check if the ticket belongs to the current project
	            if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
	                // Create a button for the ticket and add it to the ticketList
	            	AnchorPane commentDisplay = (AnchorPane) FXMLLoader.load(commentUrl);
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
		//URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		try {
			
			// Load AnchorPane for the ProjectBox view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
		    VBox box1 = (VBox) FXMLLoader.load(url);
					
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
					
			for (int commentID : commentDAO.getCommentIDs()) {
				int commentTicketID = commentDAO.getCommentTicketByID(commentID);
				int commentProjectID = commentDAO.getCommentProjectByID(commentID);
				String commentText = Main.commentDao.getCommentByID(commentID);
				String commentTime = Main.commentDao.getTimestampForComment(commentID);
				commonObjs.setCommentText(commentText);
				commonObjs.setCommentTime(commentTime);
				
				
				// Check if the comment belongs to the current ticket (maybe current project)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Create a Text node for the comment and add to box1
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
