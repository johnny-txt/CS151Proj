package application.controller;

import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.Main;
import application.ProjectBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;

public class ProjectCreationController {
	@FXML
	private TextField name;
	
	@FXML
	private DatePicker date;
	
	@FXML
	private TextArea description;
	
	public void initialize() {
        // Set the DatePicker to the current date
        date.setValue(LocalDate.now());

    }
	
	// Creates instance of CommonObjs to access common objects and data across the application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	// Method is triggered when "Cancel Project" operation is performed
	public void cancelNewProjectOp() {
		
		// Gets URL of the "HomePageWelcome.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
		
		try {
			// Loads and AnchorPane for the HomepageWelcome view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			// Retrieve the mainBox from the commonObjs instance
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
	
	@FXML 
    public void createNewProjectOp() {
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
	        AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

	        HBox mainBox = commonObjs.getMainBox();

	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        mainBox.getChildren().add(pane1);

			    AnchorPane lol = commonObjs.getProjectList();
			
			    Node emptyListText = lol.getChildren().get(0);
			
			    emptyListText.setVisible(false);
			
			    VBox coolList = commonObjs.getList();
			
			    if (lol.getChildren().size() < 3) {
			    	lol.getChildren().add(coolList);
		    	}
			    
			    Button projectButton = (Button) FXMLLoader.load(buttonUrl);
			    projectButton.setText(projName);
			    coolList.getChildren().add(projectButton);
        
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	}
	
}
