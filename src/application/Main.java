package application;

import java.net.URL;
import java.util.List;

import application.data_access_objects.ProjectDAO;
// Import necessary JavaFX classes for building the application
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;


public class Main extends Application {
	public static ProjectDAO projDao;
	// Start method is entry point for JavaFx applications
	@Override
	public void start(Stage primaryStage) {
		projDao = new ProjectDAO();
	    projDao.createProjectTable();
		try {
			// Loads the main user interface layout from "Main.fxml" file
			HBox mainBox = (HBox)FXMLLoader.load(getClass().getClassLoader().getResource("view/Main.fxml"));
			
			// Create a new scene and associates it with the main user interface
			Scene scene = new Scene(mainBox);
			
			// Adds CSS stylesheet to the scene
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			
			// Sets the primary stage scene and makes it visible
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Initialize and configure a shared instance of CommonObjs
			CommonObjs commonObjs = CommonObjs.getInstance();
			
			commonObjs.setMainBox(mainBox);
			
			URL url = getClass().getClassLoader().getResource("view/ProjectList.fxml");
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			commonObjs.setProjectList(pane);
			mainBox.getChildren().add(pane);
			
			url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");
			pane = (AnchorPane) FXMLLoader.load(url);
			mainBox.getChildren().add(pane);
			
			url = getClass().getClassLoader().getResource("view/List.fxml");
			VBox list = (VBox) FXMLLoader.load(url);
			commonObjs.setList(list);
			
			//To test looking at db
			VBox coolList = commonObjs.getList();
			AnchorPane lol = commonObjs.getProjectList();
			
			List<String> projNames = ProjectDAO.getProjectNames();
			
			for (String name : projNames) {
				coolList.getChildren().add(new Button(name));
			}
			
		    if (projNames.size() > 0 && lol.getChildren().size() < 3) {
		    	lol.getChildren().add(coolList);
	    	}
			
		} catch(Exception e) {
			// Handles exceptions that may occur when starting application
			e.printStackTrace();
		}
	}
	
	public static void addProj(ProjectBean proj) {
		projDao.insertProject(proj);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
