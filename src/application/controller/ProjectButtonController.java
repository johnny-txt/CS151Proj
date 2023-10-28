package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjectButtonController {
	@FXML 
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML public void openProject() {
		// Gets URL of the "ProjectCreation.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
		URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		try {
			commonObjs.setCurrentProject(1);
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
			System.out.println(commonObjs.getTicketList());
			commonObjs.setTicketList(ticketList);
			
			pane1.getChildren().add(commonObjs.getTicketList());
			
			for (int ticketID : Main.ticketDao.getTicketIDs()) {
				int ticketProjectID = Main.ticketDao.getTicketProjectByID(ticketID);
				String ticketName = Main.ticketDao.getTicketNameByID(ticketID);
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					Text ticketText = new Text(ticketProjectID + " " + ticketName);
					ticketList.getChildren().add(ticketText);
				}
			}

	        HBox mainBox = commonObjs.getMainBox();

	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        mainBox.getChildren().add(pane1);
			    
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
}
