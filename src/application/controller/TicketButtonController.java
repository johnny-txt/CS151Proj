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

public class TicketButtonController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML
	private Button ticketButton;
	
	@FXML 
	public void openTicket() {
		// Gets URL of the "ProjectCreation.fxml" file and loads the JavaFx scene graph
		URL url = getClass().getClassLoader().getResource("view/TicketBox.fxml");
		URL commentListUrl = getClass().getClassLoader().getResource("view/TicketCommentList.fxml");

		try {
			commonObjs.setCurrentTicket(commonObjs.getTicketList().getChildren().indexOf(ticketButton) + 1);
			System.out.println(commonObjs.getCurrentTicket());
			
			// Loads and AnchorPane for the ProjectCreation view
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			VBox commentList = (VBox) FXMLLoader.load(commentListUrl);
			commonObjs.setCommentList(commentList);
			
			commentList = commonObjs.getCommentList();
		    commentList.getChildren().clear();
		    
		    for (int commentID : Main.commentDao.getCommentIDs()) {
				int commentTicketID = Main.commentDao.getCommentTicketByID(commentID);
				String commentText = Main.commentDao.getCommentByID(commentID);
				
				if (commentTicketID == commonObjs.getCurrentTicket()) {
					
					Text commentTxt = new Text();
					commentTxt.setText(commentText);
					commentList.getChildren().add(commentTxt);
				}
			}
			
			pane1.getChildren().add(commentList);

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
