package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import application.CommonObjs;
import application.Main;
import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentListController {
	
	public TicketDAO ticketDAO;
	
	public CommentDAO commentDAO;
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	@FXML private Label ticketName;
	
	@FXML private Label ticketDescription;
	
	public void initialize() {
		ticketName.setText("Ticket: " + TicketDAO.getTicketNameByID(commonObjs.getCurrentTicket()));
		ticketDescription.setText("Description: " + TicketDAO.getTicketDescByID(commonObjs.getCurrentTicket()));
	}
	
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
	
	public void editTicket() {
		URL url = getClass().getClassLoader().getResource("view/TicketInfo.fxml");
	
		try {
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjs.getMainBox();
			
			// Checks if there is already a child in mainBox, and if so, removes  it
			if(mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			int ticketID = commonObjs.getCurrentTicket();
			System.out.println(ticketID);
			
			// Retrieve ticket information by name
			String ticketName = TicketDAO.getTicketNameByID(ticketID);
			String ticketDesc = TicketDAO.getTicketDescByID(ticketID);
			
			// Set ticket information in CommonObjs
			commonObjs.setCurrentTicket(ticketID);
			System.out.println(ticketID);
	        commonObjs.setProjectName(ticketName);
	        commonObjs.setProjectDesc(ticketDesc);
			
	        // Adds pane1 to the mainBox
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// Handles any exception that may occur during the view loading process
			e.printStackTrace();
		}
	}
	
	public void deleteTicket() {
		URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
		URL ticketButtonUrl = getClass().getClassLoader().getResource("view/TicketButton.fxml");
		
		try {
        	System.out.println(commonObjs.getCurrentTicket());
        	TicketDAO.deleteTicket(commonObjs.getCurrentTicket());
        	commonObjs.setCurrentTicket(0);
        	
            AnchorPane pane = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            if (commentDAO.getCommentIDs().isEmpty()){
            	VBox coolList = commonObjs.getList();
				coolList.getChildren().clear();
				VBox box = commonObjs.getTicketList();
				
				// Retrieve project names from database
				List<String> ticketNames = TicketDAO.getTicketNames();
				
				// Loads the projects in button form
				URL urlButton = getClass().getClassLoader().getResource("view/TicketButton.fxml");
				for (String name : ticketNames) {
					Button ticketButton = (Button) FXMLLoader.load(urlButton);
				    ticketButton.setText(name);
					coolList.getChildren().add(ticketButton);
				}
				
//	            // Ensure that project names are displayed in the UI if projects exist
//			    if (ticketNames.size() > 0 && lol.getChildren().size() < 5) {
//			    	lol.getChildren().add(coolList);
//		    	}
            	
				url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				
				VBox coolList = commonObjs.getList();
				coolList.getChildren().clear();
				VBox box = commonObjs.getTicketList();
				
				// Retrieve ticket names from database
				List<String> ticketNames = TicketDAO.getTicketNames();
				
				// Loads the ticket in button form
				URL urlButton = getClass().getClassLoader().getResource("view/TicketButton.fxml");
				for (String name : ticketNames) {
					Button ticketButton = (Button) FXMLLoader.load(urlButton);
				    ticketButton.setText(name);
					coolList.getChildren().add(ticketButton);
				}
				
	            // Ensure that project names are displayed in the UI if projects exist
//			    if (projNames.size() > 0 && lol.getChildren().size() < 5) {
//			    	lol.getChildren().add(coolList);
//		    	}
			    
				URL allTickets = getClass().getClassLoader().getResource("view/AllTickets.fxml");
			    ticketListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");
					
				// Load AnchorPane for the ProjectBox view
				AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(allTickets);
					
					
				// Adds pane1 to the mainBox
		        mainBox.getChildren().add(ticketBox);
		            
				VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
				commonObjs.setTicketList(ticketList);
				ticketList.getChildren().clear();
					
				for (int commentID : commentDAO.getCommentIDs()) {
					int commentTicketID = commentDAO.getCommentTicketByID(commentID);
					int commentProjectID = commentDAO.getCommentProjectByID(commentID);
					String commentText = commentDAO.getCommentByID(commentID);
					
					// Check if the comment belongs to the current ticket (maybe current project)
					if (commentProjectID == commonObjs.getCurrentProject() && commentTicketID == commonObjs.getCurrentTicket()) {
						
						// Create a Text node for the comment and add to box1
						Text commentTxt = new Text();
						commentTxt.setText(commentText);
//						commentList.getChildren().add(commentTxt);
					}
				}
				ticketBox.getChildren().add(ticketList);
			}
            
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
