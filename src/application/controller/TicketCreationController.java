package application.controller;

import java.io.IOException;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.TicketBean;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicketCreationController {
	public TicketDAO ticketDAO;
	
	@FXML
	private TextField ticketName;
	
	@FXML
	private TextArea description;
	
	@FXML
	public ComboBox<String> projectDropdown;
	
	// Loads projects into the project dropdown
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
	
    // Create an instance of CommonObjs to be used for whole application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	// Method triggered when "Create Ticket" button is clicked
	@FXML public void createNewTicketOp() {
		ticketDAO = Main.ticketDao;
		
		// Extract details from the input fields
		String pName = projectDropdown.getValue();
		String tName = ticketName.getText();
		String desc = description.getText();
		
		// Check if any of the fields is empty
		if (pName == null || tName.isEmpty() || desc.isEmpty()) {
			return;
		}
		
		// Retrieve the project ID associated with the selected project name
		int projectID = ProjectDAO.getProjectIDByName(pName);
		
		// Create a new ticket and insert it into the database
		TicketBean ticket = new TicketBean(projectID, tName, desc);
		ticketDAO.insertTicket(ticket, projectID);
		
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
	
	    try {
	    	
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
	        VBox box1 = (VBox) FXMLLoader.load(url);

	        HBox mainBox = commonObjs.getMainBox();

	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        mainBox.getChildren().add(pane1);

	        // Retrive the ticketList from commonObjs
			VBox ticketList = commonObjs.getTicketList();
			System.out.println(ticketList);
			
			// Clear the existing content of ticketList
			ticketList.getChildren().clear();
			    
			// Loop through all ticket IDs in the database
			
			for (int ticketID : ticketDAO.getTicketIDs()) {
				int ticketProjectID = ticketDAO.getTicketProjectByID(ticketID);
				String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = ticketDAO.getTicketNameByID(ticketID);
				String ticketDesc = ticketDAO.getTicketDescByID(ticketID);
				
				// Check if the ticket belongs to the current project
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					System.out.println(commonObjs.getCurrentProject());
					
					// Create a button for the ticket and add it to box1
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					box1.getChildren().add(ticketButton);
				}
			}
			
			// Add view of tickets associated to the project to pane1
			pane1.getChildren().add(box1);
        
         // Handle any exception that may occur during the view loading process
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	}
	
    // Method triggered when "Cancel Ticket" operation is performed
	@FXML public void cancelNewTicketOp() {
		
		// URL for the "ProjectBox.fxml" file
		URL allTickets = getClass().getClassLoader().getResource("view/AllTickets.fxml");
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		try {
			
			// Load AnchorPane for the ProjectBox view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(allTickets);
            VBox box1 = (VBox) FXMLLoader.load(url);
			
			// Retrieve the mainBox from commonObjs
			HBox mainBox = commonObjs.getMainBox();
			
			// If there is a view page in mainBox, remove it
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Adds pane1 to the mainBox
            mainBox.getChildren().add(pane1);
            
			VBox ticketList = commonObjs.getTicketList();
			ticketList.getChildren().clear();
			
			for (int ticketID : ticketDAO.getTicketIDs()) {
				int ticketProjectID = ticketDAO.getTicketProjectByID(ticketID);
				String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = ticketDAO.getTicketNameByID(ticketID);
				String ticketDesc = ticketDAO.getTicketDescByID(ticketID);
				
					System.out.println(commonObjs.getCurrentProject());
					
					// Create a button for the ticket and add it to box1
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					box1.getChildren().add(ticketButton);
				}
			
			pane1.getChildren().add(box1);
		
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
