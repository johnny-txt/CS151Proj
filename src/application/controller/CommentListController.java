package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CommentListController {
	public TicketDAO ticketDAO;
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	public void createCommentOp() {
		URL url = getClass().getClassLoader().getResource("view/CommentCreation.fxml");
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
	
	public void Back() {
		ticketDAO = Main.ticketDao;
		URL ticketBoxUrl = getClass().getClassLoader().getResource("view/ProjectBox.fxml");
//		URL url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
	    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
	    
        try {
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            // Adds pane1 to the mainBox
            mainBox.getChildren().add(pane1);
            
            VBox ticketList = commonObjs.getTicketList();

			System.out.println(ticketList);
			ticketList.getChildren().clear();
			    
			for (int ticketID : ticketDAO.getTicketIDs()) {
				int ticketProjectID = ticketDAO.getTicketProjectByID(ticketID);
				String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
				String ticketName = ticketDAO.getTicketNameByID(ticketID);
				String ticketDesc = ticketDAO.getTicketDescByID(ticketID);
				if (ticketProjectID == commonObjs.getCurrentProject()) {
					System.out.println(commonObjs.getCurrentProject());
					
					Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
					ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
					ticketList.getChildren().add(ticketButton);
				}
			}
			
			pane1.getChildren().add(ticketList);
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
