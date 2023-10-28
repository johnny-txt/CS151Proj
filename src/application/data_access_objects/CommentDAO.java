package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.CommentBean;

public class CommentDAO {
	
	public void createCommentTable() {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS comment_table (" +
                    "id INTEGER PRIMARY KEY, " +
            		"ticketID INTEGER" +
                    "commentDate DATE, " +
                    "commentText TEXT" +
                    ")";
            statement.executeUpdate(createTableSQL);
            System.out.println("comment_table created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create comment_table: " + e.getMessage());
        }
	}
	
	public void insertComment(CommentBean comment, String ticketName) {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		LocalDate commentDate = comment.getDate();
		String commentText = comment.getText();
		int ticketID = TicketDAO.getTicketIDByName(ticketName);
		
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	          String insert = "INSERT INTO comment_table (ticketID, commentDate, commentText) VALUES (?, ?, ?)";

	          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	              preparedStatement.setInt(1, ticketID);
	              preparedStatement.setDate(2, java.sql.Date.valueOf(commentDate));
	              preparedStatement.setString(3, commentText);

	              preparedStatement.executeUpdate();
	              System.out.println("Data inserted");
	          } catch (SQLException e) {
	              System.out.println("Failed to execute the SQL statement: " + e.getMessage());
	          }
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database: " + e.getMessage());
	    }
	}
	
	public static List<String> getComments(){
		List<String> comments = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return comments;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	             Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT commentText FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                String commentText = resultSet.getString("projectName");
	                comments.add(commentText);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve comments from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
	        System.out.println("Retrieved comments: " + comments);
	        return comments;
	}
}
