package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjectButtonController {
	@FXML 
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	private Button projectButton;
	
	// Method triggered when the projectButton is clicked
	@FXML public void openProject() {
		
	    // Gets the URL of the "ProjectBox.fxml" file for displaying box for ticket creation
		URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		
	    // Gets the URL of the "ProjectTicketList.fxml" file for displaying a list of project tickets
		URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		
	    // Gets the URL of the "ticketButton.fxml" file for displaying individual ticket button with details
		URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		
		try {
	        // Set the current project ID in CommonObjs based on the clicked projectButton
			String projectName = projectButton.getText();
			
			List<String> nameList = ProjectDAO.getProjectNames();
			
			for (int i = 0; i < nameList.size(); i++) {
				if(projectName.equals(nameList.get(i))) {
					commonObjs.setCurrentProject(i + 1);
				}
			}
			
			
	        // Load the AnchorPane for displaying page for ticket creation
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			// Load and set up the VBox for displaying tickets
			VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
			System.out.println(commonObjs.getTicketList());
			commonObjs.setTicketList(ticketList);
			pane1.getChildren().add(commonObjs.getTicketList());
			
			// Iterate through all ticket IDs in the database
			
			for (int ticketID : Main.ticketDao.getTicketIDs()) {
				
				// Retrieve project ID, name, and description for each ticket
				int ticketProjectID = Main.ticketDao.getTicketProjectByID(ticketID);
				projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = Main.ticketDao.getTicketNameByID(ticketID);
				String ticketDesc = Main.ticketDao.getTicketDescByID(ticketID);
				
				// Check if the ticket belongs to the current project
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					
					// Load and set up a Button for the ticket
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
			}
			
			// Access the main HBox for CommonObjs
	        HBox mainBox = commonObjs.getMainBox();
	        
	        // Remove the existing view from mainBox
	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }
	        
	        // Add the new view (ProjectCreation with tickets) to the mainBox
	        mainBox.getChildren().add(pane1);
	        
	        System.out.println("Current Project: " + commonObjs.getCurrentProject());
			    
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
}
