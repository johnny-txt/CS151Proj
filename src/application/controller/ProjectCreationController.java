package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.Main;
import application.ProjectBean;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

public class ProjectCreationController {
	@FXML
	private TextField name;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	// Initialize the date field with the current date
	public void initialize() {
		date.setValue(LocalDate.now());
    }
	
	// Create an instance variable of CommonObjs to share across application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	// Method is triggered when "Cancel Project" operation is performed
	@FXML public void cancelNewProjectOp() {
		
		// Gets URL of the "HomePageWelcome.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
		
		try {
			// Loads and AnchorPane for the HomepageWelcome view
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			// Retrieve the mainBox from the commonObjs instance
			HBox mainBox = commonObjs.getMainBox();
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Adds pane1 to the mainBox
			if (TicketDAO.getTicketIDs().isEmpty()){
				url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				URL ticketBoxUrl = getClass().getClassLoader().getResource("view/AllTickets.fxml");
			    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
			    URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
					
					// Load AnchorPane for the ProjectBox view
					AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
					
					
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
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
		
	}
	
	// Method triggered when "Create Project" operation is clicked
	@FXML public void createNewProjectOp() {
		
		// Extract project details from the input fields
		String projName = name.getText();
	    LocalDate theDate = date.getValue();
	    String desc = description.getText();
  
	    // Check if any of the fields is empty
	    if (projName.isEmpty() || theDate == null || desc.isEmpty()) {
	        return;
	    }

	    // If all fields are non-empty, create the project
	    ProjectBean proj = new ProjectBean(projName, theDate, desc);
	    Main.addProj(proj);

	    // Continue with page change
	    URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
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
				url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				URL ticketBoxUrl = getClass().getClassLoader().getResource("view/AllTickets.fxml");
			    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
			    URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
					
					// Load AnchorPane for the ProjectBox view
					AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
					
					
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
	        
	        // Adjust the project list display in the GUI
			AnchorPane lol = commonObjs.getProjectList();
			Node emptyListText = lol.getChildren().get(0);
			emptyListText.setVisible(false);
			
			VBox coolList = commonObjs.getList();
			
			// If the project list in lol is empty, add coolList to it
			if (lol.getChildren().size() < 5) {
				lol.getChildren().add(coolList);
			}
			
			// Create a button for the new project and add it to coolList
			Button projectButton = (Button) FXMLLoader.load(buttonUrl);
			projectButton.setText(projName);
			coolList.getChildren().add(projectButton);
        
         // Handles any exception that may occur during the view loading process
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	}
	
}
