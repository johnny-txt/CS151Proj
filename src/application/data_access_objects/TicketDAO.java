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
	
	public void createTicketTable() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("SQLite JDBC driver not found.");
			e.printStackTrace();
			return;
		}
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            String createTableSQL = "CREATE TABLE IF NOT EXISTS ticket_table (" +
	            		"id INTEGER PRIMARY KEY, " +
	                    "projectID INTEGER, " +
	                    "ticketName TEXT, " +
	                    "ticketDescription TEXT" +
	                    ")";
	            statement.executeUpdate(createTableSQL);
	            System.out.println("ticket_table created successfully.");
	    } catch (SQLException e) {
	    	System.out.println("Failed to create ticket_table: " + e.getMessage());
	    }
	}
	public void insertTicket(TicketBean ticket, int projectID) {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		String tName = ticket.getTicketName();
		String tDesc = ticket.getDescription();
		int id = projectID;
		
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	          String insert = "INSERT INTO ticket_table (projectID, ticketName, ticketDescription) VALUES (?, ?, ?)";
	          				   
	          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	        	  preparedStatement.setInt(1, id);
	        	  preparedStatement.setString(2, tName);
	              preparedStatement.setString(3, tDesc);

	              preparedStatement.executeUpdate();
	              System.out.println("Data inserted");
	          } catch (SQLException e) {
	              System.out.println("Failed to execute the SQL statement: " + e.getMessage());
	          }
	      } catch (SQLException e) {
	          System.out.println("Failed to connect to the database: " + e.getMessage());
	      }
	}
	
	public static List<String> getTicketNames(){
		List<String> ticketNames = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketNames;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	             Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT ticketName FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                String ticketName = resultSet.getString("ticketName");
	                ticketNames.add(ticketName);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket names: " + ticketNames);
		return ticketNames;
		
	}
	
	public static List<Integer> getTicketProjectIDs(){
		List<Integer> ticketProjectIDs = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketProjectIDs;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT projectID FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                int ticketProjectID = resultSet.getInt("projectID");
	                ticketProjectIDs.add(ticketProjectID);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + ticketProjectIDs);
		return ticketProjectIDs;
		
	}
	
	public static List<Integer> getTicketIDs(){
		List<Integer> ticketIDs = new ArrayList<>();
		
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketIDs;
        }
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            Statement statement = connection.createStatement()) {
	            String selectSQL = "SELECT id FROM ticket_table";
	            ResultSet resultSet = statement.executeQuery(selectSQL);

	            while (resultSet.next()) {
	                int ticketID = resultSet.getInt("id");
	                ticketIDs.add(ticketID);
	            }
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve ticket names from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		System.out.println("Retrieved ticket project id's: " + ticketIDs);
		return ticketIDs;
		
	}
	
	public static int getTicketIDByName(String ticketName) {
        int ticketID = -1;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketID;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM ticket_table WHERE ticketName = ?")) {
            preparedStatement.setString(1, ticketName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticketID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketID;
    }
	
	public static String getTicketNameByID(int ticketID) {
        String ticketName = null;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketName;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT ticketName FROM ticket_table WHERE id = ?")) {
            preparedStatement.setInt(1, ticketID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ticketName = resultSet.getString("ticketName");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketName;
    }
	
	public static int getTicketProjectByID(int ticketID) {
        int ticketProject = -1;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return ticketProject;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT projectID FROM ticket_table WHERE id = ?")) {
            preparedStatement.setInt(1, ticketID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
            	ticketProject = resultSet.getInt("projectID");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return ticketProject;
    }
	
}
