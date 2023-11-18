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
	   
		// Continue with page change
		URL url = getClass().getClassLoader().getResource("view/HomePage.fxml");
		URL buttonUrl = getClass().getClassLoader().getResource("view/ProjectButton.fxml");
			    
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
			if (TicketDAO.getTicketIDs().isEmpty()){
				url = getClass().getClassLoader().getResource("view/HomePage.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				URL allTickets = getClass().getClassLoader().getResource("view/AllTickets.fxml");
				URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
				URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
							
				// Load AnchorPane for the ProjectBox view
				AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(allTickets);
							
							
				// Adds pane1 to the mainBox
				mainBox.getChildren().add(ticketBox);
				            
				VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
				commonObjs.setTicketList(ticketList);
				ticketList.getChildren().clear();
							
				for (int ticketID : TicketDAO.getTicketIDs()) {
					String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
					String ticketName = TicketDAO.getTicketNameByID(ticketID);
					String ticketDesc = TicketDAO.getTicketDescByID(ticketID);
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
				ticketBox.getChildren().add(ticketList);
			}
			        
			VBox coolList = commonObjs.getList();
			coolList.getChildren().clear();
			AnchorPane lol = commonObjs.getProjectList();
			
			// Retrieve project names from database
			List<String> projNames = ProjectDAO.getProjectNames();
			
			// Loads the projects in button form
			URL urlButton = getClass().getClassLoader().getResource("view/ProjectButton.fxml");
			for (String name : projNames) {
				Button projectButton = (Button) FXMLLoader.load(urlButton);
			    projectButton.setText(name);
				coolList.getChildren().add(projectButton);
			}
			
            // Ensure that project names are displayed in the UI if projects exist
		    if (projNames.size() > 0 && lol.getChildren().size() < 5) {
		    	lol.getChildren().add(coolList);
	    	}

		        
		// Handles any exception that may occur during the view loading process
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cancel() {
		// URL for the "ProjectBox.fxml" file
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/AllTickets.fxml");
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
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
