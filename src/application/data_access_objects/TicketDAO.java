package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import application.TicketBean;

public class TicketDAO {
	
	// Create database table for tickets
	public void createTicketTable() {
		
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
	           	
				// Create table with 4 columns (id, projectID, name, desc) if doesn't exist
				String createTableSQL = "CREATE TABLE IF NOT EXISTS ticket_table (" +
	            		"id INTEGER PRIMARY KEY, " +
	                    "projectID INTEGER, " +
	                    "ticketName TEXT, " +
	                    "ticketDescription TEXT" +
	                    ")";
	            statement.executeUpdate(createTableSQL);
	            System.out.println("ticket_table created successfully.");
	    
	    // Handles exceptions
		} catch (SQLException e) {
	    	System.out.println("Failed to create ticket_table: " + e.getMessage());
	    }
	}
	
	// Inserting a new ticket into the ticket table
	public void insertTicket(TicketBean ticket, int projectID) {
		
		// Attempt to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		// Get ticket information
		String tName = ticket.getTicketName();
		String tDesc = ticket.getDescription();
		
		// Get ID of project the ticket is associated with
		int id = projectID;
		
		// Connect to database
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	        
			// Inserts ticket into ticket table
			String insert = "INSERT INTO ticket_table (projectID, ticketName, ticketDescription) VALUES (?, ?, ?)";		   
	        try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	        	preparedStatement.setInt(1, id);
	        	preparedStatement.setString(2, tName);
	            preparedStatement.setString(3, tDesc);

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
	
	// Retrieves a list of ticket names from the ticket table
	public static List<String> getTicketNames(){
		List<String> ticketNames = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketNames;
        }
		
		// Connects to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
				// Query to select all ticket names from ticket table
				String selectSQL = "SELECT ticketName FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves the ticket names one by one, adding to list
	            while (resultSet.next()) {
	                String ticketName = resultSet.getString("ticketName");
	                ticketNames.add(ticketName);
	            }
	            
	        // Handles exception
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket names: " + ticketNames);
		return ticketNames;
	}
	
	// Retrieves list of projectIDs associated with tickets
	public static List<Integer> getTicketProjectIDs(){
		List<Integer> ticketProjectIDs = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketProjectIDs;
        }
		
		// Connect to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
				// Query to select project IDs associated with tickets from ticket table
				String selectSQL = "SELECT projectID FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves project IDs associated with tickets, adding each ID to list
	            while (resultSet.next()) {
	                int ticketProjectID = resultSet.getInt("projectID");
	                ticketProjectIDs.add(ticketProjectID);
	            }
	        
	        // Error message if failed to retrieve ticket names
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + ticketProjectIDs);
		return ticketProjectIDs;
		
	}
	
	// Retrieves a list of ticket IDs from ticket table
	public static List<Integer> getTicketIDs(){
		List<Integer> ticketIDs = new ArrayList<>();
		
		// Attempts to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketIDs;
        }
		
		// Connects to database
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            
        		// Query to select all ticket IDs from ticket table
				String selectSQL = "SELECT id FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);
	            
	            // Iterates through set and retrieves the ticket IDs one by one, adding to list
	            while (resultSet.next()) {
	                int ticketID = resultSet.getInt("id");
	                ticketIDs.add(ticketID);
	            }
	        
		// Handles exception
		} catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + ticketIDs);
		return ticketIDs;
	}
	
	// Retrieve ID of a ticket from ticket table
	public static int getTicketIDByName(String ticketName) {
        int ticketID = -1;
        
        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketID;
        }
        
        // Connects to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
                // Query to select ID of the ticket with specified name from ticket table
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM ticket_table WHERE ticketName = ?")) {
            preparedStatement.setString(1, ticketName);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains row, means ticket was found and retrieves 'id'
            if (resultSet.next()) {
                ticketID = resultSet.getInt("id");
            }
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketID;
    }
	
	// Retrieve the name of ticket from ticket table based on ticket ID
	public static String getTicketNameByID(int ticketID) {
        String ticketName = null;
        
        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketName;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            
        	// Query to select name of ticket with the specified ID from ticket table
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticketName FROM ticket_table WHERE id = ?")) {
            preparedStatement.setInt(1, ticketID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means ticket with specified ID was found
            if (resultSet.next()) {
                ticketName = resultSet.getString("ticketName");
            }
        
        // Error message if failed to retrieve ticket name
        } catch (SQLException e) {
            System.out.println("Failed to retrieve ticket name from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketName;
    }
	
	// Retrieve the project ID associated with a ticket from ticket table
	public static int getTicketProjectByID(int ticketID) {
        int ticketProject = -1;
        
        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketProject;
        }
        
        // Connect to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT projectID FROM ticket_table WHERE id = ?")) {
            
        	// Query to select the projectID associated with the ticket with specified ID from ticket table
        	preparedStatement.setInt(1, ticketID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means ticket with specified ID was found
            if (resultSet.next()) {
            	ticketProject = resultSet.getInt("projectID");
            }
            
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketProject;
    }
	
	// Retrieve the description of ticket from ticket table based on ticket ID
	public static String getTicketDescByID(int ticketID) {
		String ticketDesc = null;
		
		// Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketDesc;
        }
        
        // Connects to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        	// Query to select description of ticket with the specified ID from ticket table
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticketDescription FROM ticket_table WHERE id = ?")) {
            preparedStatement.setInt(1, ticketID);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains a row, means ticket with specified ID was found
            if (resultSet.next()) {
                ticketDesc = resultSet.getString("ticketDescription");
            }
            
        // Error message if failed to retrieve ticket description
        } catch (SQLException e) {
            System.out.println("Failed to retrieve ticket description from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketDesc;
	}
}
