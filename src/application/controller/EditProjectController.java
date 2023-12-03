package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.ProjectBean;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EditProjectController {
	@FXML
	private Label projectName;
	
	@FXML
	private TextField name;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	public TicketDAO ticketDAO;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	// updates date when editing a project (fix later)
	public void initialize() {
		name.setText(ProjectDAO.getProjectNameByID(commonObjs.getCurrentProject()));
		projectName.setText(ProjectDAO.getProjectNameByID(commonObjs.getCurrentProject()));
		date.setValue(LocalDate.now());
		description.setText(ProjectDAO.getProjectDescByID(commonObjs.getCurrentProject()));
	}
		
		
	public void saveProject() {
		// Extract project details from the input fields
		String projName = name.getText();
		LocalDate theDate = date.getValue();
		String desc = description.getText();
		  
		// Check if any of the fields is empty
		
		int projectID = commonObjs.getCurrentProject();
		
		//ProjectDAO.updateName(projectID, projName);
		//ProjectDAO.updateDesc(projectID, desc);
		
	    ProjectDAO.updateProject(projectID, projName, theDate, desc);
	   
	    // Gets the URL of the "ProjectBox.fxml" file for displaying box for ticket creation
	 	URL url = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
	 		
	 	// Gets the URL of the "ProjectTicketList.fxml" file for displaying a list of project tickets
	 	URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	 		
	 	// Gets the URL of the "ticketButton.fxml" file for displaying individual ticket button with details
	 	URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
			    
		try {
			        
			// Load an AnchorPane for the HomePageWelcome view
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

		    // Retrieve the mainBox from the commonObjs instance
			HBox mainBox = commonObjs.getMainBox();

			// Remove the old page
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			        
			VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
			System.out.println(commonObjs.getTicketList());
			commonObjs.setTicketList(ticketList);
			pane.getChildren().add(commonObjs.getTicketList());
			
			// Iterate through all ticket IDs in the database
			
			for (int ticketID : TicketDAO.getTicketIDs()) {
				
				// Retrieve project ID, name, and description for each ticket
				int ticketProjectID = TicketDAO.getTicketProjectByID(ticketID);
				projName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = TicketDAO.getTicketNameByID(ticketID);
				String ticketDesc = TicketDAO.getTicketDescByID(ticketID);
				
				// Check if the ticket belongs to the current project
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					
					// Load and set up a Button for the ticket
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
			}
			
			mainBox.getChildren().add(pane);

		        
		// Handles any exception that may occur during the view loading process
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		// Gets the URL of the "ProjectBox.fxml" file for displaying box for ticket creation
		URL url = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
				
		// Gets the URL of the "ProjectTicketList.fxml" file for displaying a list of project tickets
		URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
				
		// Gets the URL of the "ticketButton.fxml" file for displaying individual ticket button with details
		URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		try {
			String projectName = name.getText();
					
			// Load AnchorPane for the ProjectBox view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
					
			// Retrieve the mainBox from commonObjs
			HBox mainBox = commonObjs.getMainBox();
			commonObjs.setTicketList(ticketList);
					
			// If there is a view page in mainBox, remove it
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
					
			// Adds pane1 to the mainBox
		    mainBox.getChildren().add(pane1);
					
		    for (int ticketID : TicketDAO.getTicketIDs()) {
				
				// Retrieve project ID, name, and description for each ticket
				int ticketProjectID = TicketDAO.getTicketProjectByID(ticketID);
				projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = TicketDAO.getTicketNameByID(ticketID);
				String ticketDesc = TicketDAO.getTicketDescByID(ticketID);
				
				// Check if the ticket belongs to the current project
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					
					// Load and set up a Button for the ticket
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
			}
					
		    pane1.getChildren().add(commonObjs.getTicketList());
				
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
