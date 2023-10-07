package application;

// Import necessary JavaFX classes for building the application
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	// Start method is entry point for JavaFx applications
	@Override
	public void start(Stage primaryStage) {
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
			
			
		} catch(Exception e) {
			// Handles exceptions that may occur when starting application
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// Launches the JavaFX application, calls the start method to display the user interface
		launch(args);
	}
}
