package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import application.CommonObjs;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicketListController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	public TicketDAO ticketDAO;
	
	@FXML public VBox list;
	
	@FXML public Label EmptyListText;
	
	@FXML public TextField SearchBar;
	
	public void CreateTicketOperation() {
		// Gets URL of the "ProjectCreation.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/TicketCreation.fxml");
		
		try {
			//TicketCreationController.loadProjects();
			
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
	
	public void Back() {
        URL url = getClass().getClassLoader().getResource("view/HomePage.fxml");

        try {
            AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            if (ticketDAO.getTicketIDs().isEmpty()){
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
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
	
	public void Search() {
		
		// URL for the "ProjectBox.fxml" file
		String query = SearchBar.getText();
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
		try {
			
			// Load AnchorPane for the ProjectBox view
			AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
			
			// Retrieve the mainBox from commonObjs
			HBox mainBox = commonObjs.getMainBox();
			
			// If there is a view page in mainBox, remove it
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			// Adds pane1 to the mainBox
            mainBox.getChildren().add(ticketBox);
            
			VBox ticketList = commonObjs.getTicketList();
			ticketList.getChildren().clear();

			
			for (int ticketID : ticketDAO.getTicketIDs()) {
				int ticketProjectID = ticketDAO.getTicketProjectByID(ticketID);
				String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = ticketDAO.getTicketNameByID(ticketID);
				String ticketDesc = ticketDAO.getTicketDescByID(ticketID);
				
				// Check if the ticket belongs to the current project
				if (ticketName.contains(query) || projectName.contains(query)) {
					
					// Create a button for the ticket and add it to box1
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
			}
			
			ticketBox.getChildren().add(ticketList);
		
		// Handles exceptions
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editProject() {
		URL url = getClass().getClassLoader().getResource("view/ProjectInfo.fxml");
		
		try {
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			int projectID = commonObjs.getCurrentProject();
			System.out.println(projectID);
			
			// Retrieve project information by name
			String projectName = ProjectDAO.getProjectNameByID(projectID);
			String projectDesc = ProjectDAO.getProjectDescByID(projectID);
	        //LocalDate projectDate = ProjectDAO.getProjectDateByID(projectID);
			
			// Set project information in CommonObjs
			commonObjs.setCurrentProject(projectID);
			System.out.println(projectID);
	        commonObjs.setProjectName(projectName);
	        commonObjs.setProjectDesc(projectDesc);
			
	        // Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
	
	public void deleteProject() {
		URL url = getClass().getClassLoader().getResource("view/HomePage.fxml");

        try {
        	System.out.println(commonObjs.getCurrentProject());
        	ProjectDAO.deleteProj(commonObjs.getCurrentProject());
        	commonObjs.setCurrentProject(0);
        	
            AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            if (ticketDAO.getTicketIDs().isEmpty()){
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
            	
				url = getClass().getClassLoader().getResource("view/HomePage.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				
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
            
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
