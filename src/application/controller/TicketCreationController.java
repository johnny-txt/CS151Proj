package application.controller;

import java.io.IOException;

import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.data_access_objects.ProjectDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TicketCreationController {
	@FXML
	private TextField projName;
	
	@FXML
	private TextField ticketName;
	
	@FXML
	private TextArea description;
	
	@FXML
	public ComboBox<String> projectDropdown;
	
	@FXML
	public void loadProjects() {
		projectDropdown.getItems().clear();
		System.out.println("lol");
		
		List<String> projNames = ProjectDAO.getProjectNames();
		
		for (String name : projNames) {
			projectDropdown.getItems().add(name);
		}
	}
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML
	public void createNewTicketOp() {
		String tName = ticketName.getText();
		String desc = description.getText();
		
		if (tName.isEmpty() || desc.isEmpty()) {
			return;
		}
		
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
	
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
			    
//			    Button projectButton = (Button) FXMLLoader.load(buttonUrl);
//			    projectButton.setText(projName);
//			    coolList.getChildren().add(projectButton);
        
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	}
	
	@FXML
	public void cancelNewTicketOp() {
		URL url = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			pane1.getChildren().add(commonObjs.getTicketList());
			
			HBox mainBox = commonObjs.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane1);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
