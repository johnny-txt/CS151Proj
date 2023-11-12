package application;

import java.net.URL;
import java.util.List;

import application.data_access_objects.CommentDAO;
import application.data_access_objects.ProjectDAO;
import application.data_access_objects.TicketDAO;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

/**
 * The Main class serves as the entry point for the application.
 * It initializes the database tables, loads the main user interface, and configures the primary stage.
 */
public class Main extends Application {
	public static ProjectDAO projDao;
	public static TicketDAO ticketDao;
	public static CommentDAO commentDao;
	
	// Start method is entry point for JavaFx applications
	@Override
	public void start(Stage primaryStage) {
		
        // Initialize data access objects for projects, tickets, and comments
		projDao = new ProjectDAO();
	    projDao.createProjectTable();
	    ticketDao = new TicketDAO();
	    ticketDao.createTicketTable();
	    commentDao = new CommentDAO();
	    commentDao.createCommentTable();
		
	    try {
	    	
	    	// Load the main user interface layout from the "Main.fxml" file
	    	HBox mainBox = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			
	    	// Create a new scene and set it for the primary stage
	    	Scene scene = new Scene(mainBox);
			
	    	// Add a CSS stylesheet to the scene for styling
	    	scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			
			// Set the primary stage scene and make it visible
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Initialize a shared instance of CommonObjs for accessing common objects across the application
			CommonObjs commonObjs = CommonObjs.getInstance();
			
			// Sets the mainBox HBox in the CommonObjs instance
			commonObjs.setMainBox(mainBox);
			
			// Load and set up the ProjectList view (empty list at first with project creation button)
			URL url = getClass().getClassLoader().getResource("view/ProjectList.fxml");
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			commonObjs.setProjectList(pane);
			mainBox.getChildren().add(pane);
			
			// Load and set up the HomePageWelcome view (homepage)
			if (ticketDao.getTicketIDs().isEmpty()){
				url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
				pane = (AnchorPane) FXMLLoader.load(url);
				mainBox.getChildren().add(pane);
			}
			else {
				URL ticketBoxUrl = getClass().getClassLoader().getResource("view/AllTickets.fxml");
			    URL ticketUrl = getClass().getClassLoader().getResource("view/ticketButton.fxml");
			    URL ticketListUrl = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
					
					// Load AnchorPane for the ProjectBox view
					AnchorPane ticketBox = (AnchorPane) FXMLLoader.load(ticketBoxUrl);
					
					
					// Adds pane1 to the mainBox
		            mainBox.getChildren().add(ticketBox);
		            
					VBox ticketList = (VBox) FXMLLoader.load(ticketListUrl);
					commonObjs.setTicketList(ticketList);
					ticketList.getChildren().clear();
					
					for (int ticketID : TicketDAO.getTicketIDs()) {
						String projectName = ProjectDAO.getProjectNameByID(TicketDAO.getTicketProjectByID(ticketID));
						String ticketName = TicketDAO.getTicketNameByID(ticketID);
						String ticketDesc = TicketDAO.getTicketDescByID(ticketID);
						Button ticketButton = (Button) FXMLLoader.load(ticketUrl);
						ticketButton.setText("Project: " + projectName + "     Ticket Name: " + ticketName + "     Desc: " + ticketDesc);
						ticketList.getChildren().add(ticketButton);
					}
					
				ticketBox.getChildren().add(ticketList);
			}
			
			
			// Load and set up the List view (where existing projects appear)
			url = getClass().getClassLoader().getResource("view/List.fxml");
			VBox list = (VBox) FXMLLoader.load(url);
			commonObjs.setList(list);
			
			/*
			url = getClass().getClassLoader().getResource("view/ProjectTicketList.fxml");
			VBox ticketList = (VBox) FXMLLoader.load(url);
			commonObjs.setList(ticketList);
			*/
			
			
			// Access common objects from CommonObjs instance
			VBox coolList = commonObjs.getList();
			AnchorPane lol = commonObjs.getProjectList();
			
			// Retrieve project names from database
			List<String> projNames = ProjectDAO.getProjectNames();
			
			// Loads the projects in button form
			URL urlButton = getClass().getClassLoader().getResource("view/ProjectButton.fxml");
			for (String name : projNames) {
				Button projectButton = (Button) FXMLLoader.load(urlButton);
			    projectButton.setText(name);
				coolList.getChildren().add(projectButton);
			}
			
            // Ensure that project names are displayed in the UI if projects exist
		    if (projNames.size() > 0 && lol.getChildren().size() < 5) {
		    	lol.getChildren().add(coolList);
	    	}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Static method to add a project to the database
	public static void addProj(ProjectBean proj) {
		projDao.insertProject(proj);
	}
	
	// Main method launches the JavaFx application
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
