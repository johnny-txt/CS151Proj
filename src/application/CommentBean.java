package application;

public class CommentBean {
	private String timestamp;
	private String text;
	
	public CommentBean(String time, String text) {
		this.timestamp = time;
		this.text = text;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
}
