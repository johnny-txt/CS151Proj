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
	private TextField name;
	
	@FXML
	private TextArea description;
	
	public CommentDAO commentDAO;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	public void initialize() {
		ticketName.setText(TicketDAO.getTicketNameByID(commonObjs.getCurrentTicket()));
		name.setText(TicketDAO.getTicketNameByID(commonObjs.getCurrentTicket()));
		description.setText(TicketDAO.getTicketDescByID(commonObjs.getCurrentTicket()));
	}
	
	public void saveTicket() {
		String newTicketName = name.getText();
		String newDesc = description.getText();
		
		int ticketID = commonObjs.getCurrentTicket();
		
		TicketDAO.updateTicket(ticketID, newTicketName, newDesc);
		
		//URL url = getClass().getClassLoader().getResource("view/HomePage.fxml");
		URL url = getClass().getClassLoader().getResource("view/HomePage.fxml");
		URL buttonUrl = getClass().getClassLoader().getResource("view/TicketButton.fxml");
	
		try {
	        
			// Load an AnchorPane for the HomePageWelcome view
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

		    // Retrieve the mainBox from the commonObjs instance
			HBox mainBox = commonObjs.getMainBox();

			// Remove the old page
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			        
			// Add the new page
			if (CommentDAO.getCommentIDs().isEmpty()){
				url = getClass().getClassLoader().getResource("view/HomePage.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
//				URL allComments = getClass().getClassLoader().getResource("view/AllTickets.fxml");
//				URL commentUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
				URL commentListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
							
				// Load AnchorPane for the ProjectBox view
				AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(commentListUrl);
							
							
				// Adds pane1 to the mainBox
				mainBox.getChildren().add(ticketBox);
				            
				VBox ticketList = (VBox) FXMLLoader.load(commentListUrl);
				commonObjs.setTicketList(ticketList);
				ticketList.getChildren().clear();
							
				for (int commentID : commentDAO.getCommentIDs()) {
					int commentTicketID = commentDAO.getCommentTicketByID(commentID);
					int commentProjectID = commentDAO.getCommentProjectByID(commentID);
					String commentText = commentDAO.getCommentByID(commentID);
					
					// Check if the comment belongs to the current ticket (maybe current project)
					if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
						
						// Create a Text node for the comment and add to box1
						Text commentTxt = new Text();
						commentTxt.setText(commentText);
//						commentList.getChildren().add(commentTxt);
					}
				}
				ticketBox.getChildren().add(ticketList);
			}
			        
//			VBox coolList = commonObjs.getList();
//			coolList.getChildren().clear();
//			AnchorPane lol = commonObjs.getProjectList();
//			
//			// Retrieve project names from database
//			List<String> projNames = ProjectDAO.getProjectNames();
//			
//			// Loads the projects in button form
//			URL urlButton = getClass().getClassLoader().getResource("view/ProjectButton.fxml");
//			for (String name : projNames) {
//				Button projectButton = (Button) FXMLLoader.load(urlButton);
//			    projectButton.setText(name);
//				coolList.getChildren().add(projectButton);
//			}
//			
//            // Ensure that project names are displayed in the UI if projects exist
//		    if (projNames.size() > 0 && lol.getChildren().size() < 5) {
//		    	lol.getChildren().add(coolList);
//	    	}

		        
		// Handles any exception that may occur during the view loading process
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL url = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
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
				String commentText = commentDAO.getCommentByID(commentID);
				
				// Check if the comment belongs to the current ticket (maybe current project)
				if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
					
					// Create a Text node for the comment and add to box1
					Text commentTxt = new Text();
					commentTxt.setText(commentText);
					commentList.getChildren().add(commentTxt);
				}
			}
					
			pane1.getChildren().add(commentList);
				
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
