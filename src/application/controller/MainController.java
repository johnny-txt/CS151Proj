package application.controller;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	
	// Injects HBox element from the FXML file into this controller
	@FXML HBox mainBox;
	
	// Method is triggers when "Create Project" operation is performed
	@FXML public void CreateProjectOperation() {
		// Gets URL of the "ProjectCreation.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/ProjectCreation.fxml");
		
		try {
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
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
	
}
