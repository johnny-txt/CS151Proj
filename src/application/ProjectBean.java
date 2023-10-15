package application;
import java.time.LocalDate;

public class ProjectBean {
	
	private String name;
	private LocalDate date;
	private String description;
	
	public ProjectBean(String name, LocalDate date, String description) {
		this.name = name;
		this.date = date;
		this.description = description;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate(LocalDate date) {
		this.date = date;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}
