package application;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The CommonObjs class represents a singleton container for common user interface components and state
 * used in a bug tracker software application.
 */
public class CommonObjs {
    // Creates a private static instance of CommonObjs for the singleton pattern
    private static CommonObjs commonObjs = new CommonObjs();

    // Private field to hold the reference to the main HBox representing the primary user interface
    private HBox mainBox;
    
    // Fields to store various UI components
    private AnchorPane projectList;  // Represents the list of projects
    private VBox list;  // Represents a generic list used in the application
    private VBox ticketList;  // Represents the list of tickets within a project
    private VBox commentList;  // Represents the list of comments for a specific ticket
    private ChoiceBox<String> projectDropdown;  // Represents a dropdown for selecting projects
    
    // Fields to store the currently selected project and ticket IDs
    private int currentProjectID;
    private int currentTicketID;
    private int currentCommentID;
    
    private String projectName;
    private String projectDesc;
    
    private String commentText;

	private String commentTimestamp;

    // Instantiates CommonObjs only within this class
    private CommonObjs() {}

    // Static method to retrieve the singleton instance of CommonObjs
    public static CommonObjs getInstance() {
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

    // Getter and setter methods for the projectList field
    public AnchorPane getProjectList() {
        return projectList;
    }

    public void setProjectList(AnchorPane projectList) {
        this.projectList = projectList;
    }

    // Getter and setter methods for the list field
    public VBox getList() {
        return list;
    }

    public void setList(VBox list) {
        this.list = list;
    }

    // Getter and setter methods for the ticketList field
    public VBox getTicketList() {
        return ticketList;
    }

    public void setTicketList(VBox list) {
        this.ticketList = list;
    }

    // Getter and setter methods for the projectDropdown field
    public ChoiceBox<String> getProjectDropdown() {
        return projectDropdown;
    }

    public void setProjectDropdown(ChoiceBox<String> dropdown) {
        this.projectDropdown = dropdown;
    }

    // Getter and setter methods for the commentList field
    public VBox getCommentList() {
        return commentList;
    }

    public void setCommentList(VBox list) {
        this.commentList = list;
    }

    // Getter and setter methods for the currentProjectID field
    public int getCurrentProject() {
        return currentProjectID;
    }

    public void setCurrentProject(int id) {
        this.currentProjectID = id;
    }

    // Getter and setter methods for the currentTicketID field
    public int getCurrentTicket() {
        return currentTicketID;
    }

    public void setCurrentTicket(int id) {
        this.currentTicketID = id;
    }

	public void setProjectName(String projectName) {
		this.projectName = projectName;
		
	}
	
	public String getProjectName() {
		return projectName;
		
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
		
	}
	
	public String getProjectDesc() {
		return projectDesc;
		
	}
	
	public int getCurrentComment() {
		return currentCommentID;
	}
	
	public void setCurrentComment(int id) {
		this.currentCommentID = id;;
		
	}
	
	public void setCommentText(String commentText) {
		this.commentText = commentText;
		
	}
	
	public String getCommentText() {
		return commentText;
		
	}

	public void setCommentTime(String commentTimeStamp) {
		this.commentTimestamp = commentTimeStamp;
	}
	
	public String getCommentTime() {
		return commentTimestamp;
		
	}
}