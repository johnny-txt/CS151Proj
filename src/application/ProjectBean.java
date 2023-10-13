package application;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ProjectBean {
	
	
	private String projectName;
	private String textArea;
	private LocalDate date;
	
	
	public ProjectBean(String projectName, String textArea, LocalDate date) {
		this.textArea = textArea;
		this.projectName = projectName;
		this.date = date;
		
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getDescription() {
		return textArea;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setName(String name) {
		projectName = name;
	}
	
	public void setDesc(String desc) {
		textArea = desc;
	}

	
	public void setDate(LocalDate newDate) {
		date = newDate;
	}

	
}
