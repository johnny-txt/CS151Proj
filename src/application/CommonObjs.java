package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommonObjs {
	// Creates a private static instance of CommonObjs for the singleton pattern
	private static CommonObjs commonObjs =  new CommonObjs();
	
	// Private field to hold the reference to the HBox representing the main user interface
	private HBox mainBox;
	private AnchorPane projectList;
	
	// Instantiates CommonObjs only within this class
	private CommonObjs() {}
	
	// Static method to retrieve the singleton instance of CommonObjs
	public static CommonObjs getInstance(){
		return commonObjs;
	}
	
	// Getter method to access the mainBox HBox from outside the class
	public HBox getMainBox() {
		return mainBox;
	}
	
	// Setter method to set the mainBox HBox from outside the class
	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
	
	public AnchorPane getProjectList() {
		return projectList;
	}
	
	// Setter method to set the mainBox HBox from outside the class
	public void setProjectList(AnchorPane projectList) {
		this.projectList = projectList;
	}

}
