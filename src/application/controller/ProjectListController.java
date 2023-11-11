package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.data_access_objects.ProjectDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProjectListController {
	
	// Creates instance of CommonObjs to access common objects and data across the application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	private ProjectDAO projectDAO;
	
	@FXML public VBox list;
	
	@FXML public Label EmptyListText;
	
	@FXML public TextField SearchBar;
	
	public void CreateProjectOperation() {
		// Gets URL of the "ProjectCreation.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/ProjectCreation.fxml");
		
		try {
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
	
	public void Search() {
		String query = SearchBar.getText();
		URL projectBoxUrl = getClass().getClassLoader().getResource("view/ProjectList.fxml");
	    URL projectUrl = getClass().getClassLoader().getResource("view/projectButton.fxml");
	    try {
	    	
	    	AnchorPane projectBox = (AnchorPane) FXMLLoader.load(projectBoxUrl);
	    
	    	HBox mainBox = commonObjs.getMainBox();
	    	
	    	if (mainBox.getChildren().size() > 1) {
	    		mainBox.getChildren().remove(1);
	    	}
	    	
	    	mainBox.getChildren().add(projectBox);
	    	
	    	AnchorPane projectList = commonObjs.getProjectList();
	    	projectList.getChildren().clear();
	    	
	    	for (int projectID : projectDAO.getProjectIDs()) {
	    		String projectName = projectDAO.getProjectNameByID(projectID);
				String projectDesc = projectDAO.getProjectDescByID(projectID);
	    	
				// Check if the ticket belongs to the current project
				if (projectID == commonObjs.getCurrentProject()) {
					System.out.println(commonObjs.getCurrentProject());
					
					// Create a button for the ticket and add it to box1
					Button projectButton = (Button) FXMLLoader.load(projectUrl);
					projectButton.setText("Project Name: " + projectName + "     Desc: " + projectDesc);
					projectList.getChildren().add(projectButton);
				}
	    	}
	    	
	    	projectBox.getChildren().add(projectList);
	    } catch(IOException e) {
			e.printStackTrace();
		}
	}
}
