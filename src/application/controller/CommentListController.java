package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CommentListController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	
	public void createCommentOp() {
		
	}
	
	public void Back() {
		URL url = getClass().getClassLoader().getResource("view/HomePageWelcome.fxml");

        try {
            AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);

            HBox mainBox = commonObjs.getMainBox();

            // Checks if there is already a child in mainBox, and if so, removes  it
            if(mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            // Adds pane1 to the mainBox
            mainBox.getChildren().add(pane1);
        } catch(IOException e) {
            e.printStackTrace();
        }
	}
}
