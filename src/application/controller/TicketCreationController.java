package application.controller;

import java.io.IOException;

import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.TicketBean;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
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
import javafx.scene.text.Text;

public class TicketCreationController {
	private TicketDAO ticketDAO;
	
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
		ticketDAO = new TicketDAO();
		String pName = projectDropdown.getValue();
		String tName = ticketName.getText();
		String desc = description.getText();
		
		if (pName == null || tName.isEmpty() || desc.isEmpty()) {
			return;
		}
		
		int projectID = ProjectDAO.getProjectIDByName(pName);
		TicketBean ticket = new TicketBean(projectID, tName, desc);
		ticketDAO.insertTicket(ticket, projectID);
		
		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
	
	    try {
	        VBox box1 = (VBox) FXMLLoader.load(url);

	        HBox mainBox = commonObjs.getMainBox();

	        if (mainBox.getChildren().size() > 1) {
	            mainBox.getChildren().remove(1);
	        }

	        mainBox.getChildren().add(box1);

			VBox ticketList = commonObjs.getTicketList();
			System.out.println(ticketList);
			ticketList.getChildren().clear();
			    
			for (String ticketName : ticketDAO.getTicketNames()) {
				Button ticketButton = new Button(ticketName);
				ticketButton = (Button) FXMLLoader.load(ticketUrl);
				box1.getChildren().add(ticketButton);
			}
        
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
