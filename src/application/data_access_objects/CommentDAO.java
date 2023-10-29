package application.data_access_objects;

import java.sql.Connection;
import java.sql.Date;
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
            		"ticketID INTEGER, " +
                    "timeStamp TEXT, " +
            		"commentText TEXT" +
                    ")";
            statement.executeUpdate(createTableSQL);
            System.out.println("comment_table created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create comment_table: " + e.getMessage());
        }
	}
	
	public void insertComment(CommentBean comment, int ticketID) {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		String timestamp = comment.getTimestamp();
		String commText = comment.getText();
		int id = ticketID;
		
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	          String insert = "INSERT INTO comment_table (ticketID, timeStamp, commentText) VALUES (?, ?, ?)";

	          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	              preparedStatement.setInt(1, id);
	              preparedStatement.setString(2, timestamp);
	              preparedStatement.setString(3, commText);
	              
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
	
	public static List<Integer> getCommentTicketIDs(){
		List<Integer> commentTicketIDs = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentTicketIDs;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT ticketID FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                int commentTicketID = resultSet.getInt("projectID");
	                commentTicketIDs.add(commentTicketID);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved comment ticket id's: " + commentTicketIDs);
		return commentTicketIDs;
		
	}
	
	public static List<Integer> getCommentIDs(){
		List<Integer> commentIDs = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentIDs;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT id FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                int commentID = resultSet.getInt("id");
	                commentIDs.add(commentID);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + commentIDs);
		return commentIDs;
		
	}
	
	public static int getCommentTicketByID(int commentID) {
        int commentTicket = -1;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentTicket;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticketID FROM comment_table WHERE id = ?")) {
            preparedStatement.setInt(1, commentID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	commentTicket = resultSet.getInt("ticketID");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return commentTicket;
    }
	
	public static String getCommentByID(int commentID) {
        String comment = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return comment;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT commentText FROM comment_table WHERE id = ?")) {
            preparedStatement.setInt(1, commentID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                comment = resultSet.getString("commentText");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return comment;
    }
}
