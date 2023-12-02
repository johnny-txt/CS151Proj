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
		String query = SearchBar.getText().toLowerCase();
	    URL projectUrl = getClass().getClassLoader().getResource("view/ProjectButton.fxml");
	    try {
	    	
	    	AnchorPane projectBox = commonObjs.getProjectList();
	    
	    	HBox mainBox = commonObjs.getMainBox();
	    	
	    	if (mainBox.getChildren().size() > 1) {
	    		mainBox.getChildren().remove(0);
	    	}
	    	
	    	mainBox.getChildren().add(0, projectBox);
	    	
	    	VBox projectList = commonObjs.getList();
	    	projectList.getChildren().clear();
	    	
	    	for (int projectID : projectDAO.getProjectIDs()) {
	    		String projectName = projectDAO.getProjectNameByID(projectID);
				String projectDesc = projectDAO.getProjectDescByID(projectID);
	    	
				
				if (projectName.toLowerCase().contains(query)) {
					
					// Create a button for the ticket and add it to box1
					Button projectButton = (Button) FXMLLoader.load(projectUrl);
					projectButton.setText(projectName);
					projectList.getChildren().add(projectButton);
				}
	    	}
	    	
	    	// Adds VBox on top of AnchorPane
	    	projectBox.getChildren().set(4, projectList);
	    } catch(IOException e) {
			e.printStackTrace();
		}
	}
}
