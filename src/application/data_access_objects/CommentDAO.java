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
	
	// Create database table for comments
	public void createCommentTable() {
		
		// Attempt to load sqlite driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		// Connection to the database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement()) {
            
        	// Create table with 5 columns (id, projectID, ticketID, time, text) if doesn't exist
        	String createTableSQL = "CREATE TABLE IF NOT EXISTS comment_table (" +
                    "id INTEGER PRIMARY KEY, " +
                    "projectID INTEGER, " +
            		"ticketID INTEGER, " +
                    "timeStamp TEXT, " +
            		"commentText TEXT" +
                    ")";
            statement.executeUpdate(createTableSQL);
            System.out.println("comment_table created successfully.");
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to create comment_table: " + e.getMessage());
        }
	}
	
	// Inserting a new ticket into the ticket table
	public void insertComment(CommentBean comment, int projectID, int ticketID) {
		
		// Attempt to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		// Get comment information
		String timestamp = comment.getTimestamp();
		String commText = comment.getText();
		
		// Get ID of project and ticket the comment is associated with
		int pID = projectID;
		int tID = ticketID;
		
		// Connect to database
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	          
			// Inserts comment into comment table
			String insert = "INSERT INTO comment_table (projectID, ticketID, timeStamp, commentText) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	        	preparedStatement.setInt(1, pID);
	            preparedStatement.setInt(2, tID);
	            preparedStatement.setString(3, timestamp);
	            preparedStatement.setString(4, commText);
	              
	            preparedStatement.executeUpdate();
	            System.out.println("Data inserted");
	        
	        // Error message if insertion failed
	        } catch (SQLException e) {
	        	System.out.println("Failed to execute the SQL statement: " + e.getMessage());
	        }
		
		// Handles exceptions
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database: " + e.getMessage());
	    }
	}
	
	// Retrieves a list of comments from the comment table
	public static List<String> getComments(){
		List<String> comments = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return comments;
        }
		
		// Connects to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
				// Query to select all comment texts from comment table
				String selectSQL = "SELECT commentText FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves the ticket names one by one, adding to list
	            while (resultSet.next()) {
	                String commentText = resultSet.getString("projectName");
	                comments.add(commentText);
	            }
	            
	        // Handles exception
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve comments from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
	        System.out.println("Retrieved comments: " + comments);
	        return comments;
	}
	
	// Retrieves list of TicketIDs associated with comments
	public static List<Integer> getCommentTicketIDs(){
		List<Integer> commentTicketIDs = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentTicketIDs;
        }
		
		// Connect to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
				// Query to select ticket IDs associated with comments from comment table
				String selectSQL = "SELECT ticketID FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves ticket IDs associated with comments, adding each ID to list
	            while (resultSet.next()) {
	                int commentTicketID = resultSet.getInt("projectID");
	                commentTicketIDs.add(commentTicketID);
	            }
	        
	        // Error message if failed to retrieve comments
			} catch (SQLException e) {
	            System.out.println("Failed: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved comment ticket id's: " + commentTicketIDs);
		return commentTicketIDs;
		
	}
	
	// Retrieves a list of comment IDs from comment table
	public static List<Integer> getCommentIDs(){
		List<Integer> commentIDs = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
        	System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentIDs;
        }
		// Connects to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
				// Query to select all comment IDs from comment table
				String selectSQL = "SELECT id FROM comment_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves the comment IDs one by one, adding to list
	            while (resultSet.next()) {
	                int commentID = resultSet.getInt("id");
	                commentIDs.add(commentID);
	            }
	        
	        // Handle exceptions
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + commentIDs);
		return commentIDs;
		
	}
	
	// Retrieve the ticket ID associated with a comment from comment table
	public static int getCommentTicketByID(int commentID) {
        int commentTicket = -1;

        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentTicket;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticketID FROM comment_table WHERE id = ?")) {
            
        	// Query to select the ticketID associated with the comment with specified ID from comment table
        	preparedStatement.setInt(1, commentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means comment with specified ID was found
            if (resultSet.next()) {
            	commentTicket = resultSet.getInt("ticketID");
            }
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return commentTicket;
    }
	
	public static int getCommentID(int projectID, int ticketID, String commentText) {
        int commentId = -1;

        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentId;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM comment_table WHERE "
            		+ "projectID = ? AND ticketID = ? AND commentText = ?")) {
            
        	// Query to select the ticketID associated with the comment with specified ID from comment table
        	preparedStatement.setInt(1, projectID);
        	preparedStatement.setInt(2, ticketID);
        	preparedStatement.setString(3, commentText);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means comment with specified ID was found
            if (resultSet.next()) {
            	//commentTicket = resultSet.getInt("ticketID");
            }
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return commentId;
    }
	
	public static int getCommentProjectByID(int commentID) {
        int commentProject = -1;

        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return commentProject;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT projectID FROM comment_table WHERE id = ?")) {
            
        	// Query to select the ticketID associated with the comment with specified ID from comment table
        	preparedStatement.setInt(1, commentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means comment with specified ID was found
            if (resultSet.next()) {
            	commentProject = resultSet.getInt("projectID");
            }
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return commentProject;
    }
	
	// Retrieves the comment text from ticket table
	public static String getCommentByID(int commentID) {
        String comment = null;
        
        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return comment;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            
            // Query to select text of comment with the specified ID from comment table
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT commentText FROM comment_table WHERE id = ?")) {
            preparedStatement.setInt(1, commentID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means ticket with specified ID was found
            if (resultSet.next()) {
                comment = resultSet.getString("commentText");
            }
        
        // Error message if failed to retrieve comment text
        } catch (SQLException e) {
            System.out.println("Failed to retrieve comment text from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return comment;
    }
	
	public static void deleteComment(int commentID) {
		// Attempt to load sqlite driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC driver not found.");
			e.printStackTrace();
			return;
		}

		String query = "DELETE FROM comment_table WHERE id = ?";
		try(Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db");
				PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setInt(1, commentID);
			preparedStatement.execute();
		}
		catch (SQLException e) {
			System.out.println("Failed to delete comment from the database: " + e.getMessage());
			e.printStackTrace();
		}

		query = "DELETE FROM comment_table WHERE commentID = ?";
		try(Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db");
				PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setInt(1, commentID);
			preparedStatement.execute();
		}

		catch (SQLException e) {
			System.out.println("Failed to comment project from the database: " + e.getMessage());
			e.printStackTrace();
		}

		query = "DELETE FROM comment_table WHERE projectID = ?";
		try(Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db");
				PreparedStatement preparedStatement = connect.prepareStatement(query)){
			preparedStatement.setInt(1, commentID);
			preparedStatement.execute();
		}
		catch (SQLException e) {
			System.out.println("Failed to delete comment from the database: " + e.getMessage());
			e.printStackTrace();
		}
	} 
	
	public static void updateComment(int commentID, String updatedTime, String updatedText) {
		// Attempt to load driver
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC driver not found.");
			e.printStackTrace();
			return;
		}
  	  
		// Connect to database
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
          
  		  // Inserts project into project table
			String update = "UPDATE comment_table SET timeStamp = ?, commentText = ? WHERE id = ?";
			try (PreparedStatement preparedStatement = connect.prepareStatement(update)) {
				preparedStatement.setString(1, updatedTime);
				preparedStatement.setString(2, updatedText);
				preparedStatement.setInt(3, commentID);
				preparedStatement.executeUpdate();
				System.out.println("Data updated");
          
				// Error message if insertion failed
			} catch (SQLException e) {
				System.out.println("Failed to execute the SQL statement: " + e.getMessage());
			}
			// Handles exceptions
		} catch (SQLException e) {
			System.out.println("Failed to connect to the database: " + e.getMessage());
		}
	}
}
