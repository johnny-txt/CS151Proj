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

	    URL allTicketsUrl = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
	    URL ticketListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
	    URL ticketButtonUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");

	    try {
	        // Load AnchorPane for the AllTickets view
	        AnchorPane allTicketsPane = (AnchorPane) FXMLLoader.load(allTicketsUrl);
	        VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);

	        // Retrieve the mainBox from commonObjs
	        HBox mainBox = commonObjs.getMainBox();

	        // If there is a view page in mainBox, remove it
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        // Adds the AllTickets view to the mainBox
	        mainBox.getChildren().add(allTicketsPane);

	        // Add the ticketList to the AllTickets view
	        allTicketsPane.getChildren().add(ticketList);

	        // Clear the ticketList
	        ticketList.getChildren().clear();

	        for (int ticketID1 : TicketDAO.getTicketIDs()) {
	            int ticketProjectID = TicketDAO.getTicketProjectByID(ticketID1);
	            String projectName = ProjectDAO.getProjectNameByID(ticketProjectID);
	            String ticketName = TicketDAO.getTicketNameByID(ticketID1);
	            String ticketDesc = TicketDAO.getTicketDescByID(ticketID1);

	            // Check if the ticket belongs to the current project
	            if (ticketProjectID == commonObjs.getCurrentProject()) {
	                // Create a button for the ticket and add it to the ticketList
	                Button ticketButton = (Button) FXMLLoader.load(ticketButtonUrl);
	                ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
	                ticketList.getChildren().add(ticketButton);
	            }
	        }
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
