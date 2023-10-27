package application;

public class TicketBean {
	private String projectName;
	private String ticketName;
	private String description;
	
	public TicketBean(String projectName, String ticketName, String description) {
		this.projectName = projectName;
		this.ticketName = ticketName;
		this.description = description;
	}
	
//	public String getProjectName() {
//		return projectName;
//	}
//
//	public void setProjectName(String projectName) {
//		this.projectName = projectName;
//	}
	
	public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
}
