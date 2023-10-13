package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import application.CommonObjs;
import application.ProjectBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ProjectCreationController {
	
	private TextField Name;
	private TextArea Description;
	private DatePicker Date;
	// Creates instance of CommonObjs to access common objects and data across the application
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML 
	// Method is triggered when "Cancel Project" operation is performed
	public void CancelNewProjectOp() {
		
		
	
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
    public void CreateNewProjectOp() {
        URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");

        try {
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            mainBox.getChildren().add(pane1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
	@FXML
	//method that takes user input so it can be made into an object
	public void getInfo() {
		String description = Description.getText();
		String projName = Name.getText();
		LocalDate theDate = Date.getValue();
		ProjectBean proj = new ProjectBean(projName, description, theDate);
		
	}
	
	
}
