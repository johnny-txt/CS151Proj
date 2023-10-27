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
	                    //"projectName TEXT, " +
	                    "ticketName TEXT, " +
	                    "ticketDescription TEXT" +
	                    ")";
	            statement.executeUpdate(createTableSQL);
	            System.out.println("ticket_table created successfully.");
	        } catch (SQLException e) {
	            System.out.println("Failed to create ticket_table: " + e.getMessage());
	        }
	}
	public void insertTicket(TicketBean ticket) {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		String ticketName = ticket.getTicketName();
		String ticketDesc = ticket.getDescription();
		
		try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
	          String insert = "INSERT INTO ticket_table (ticketName, ticketDescription) VALUES (?, ?)";

	          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
	        	  //preparedStatement.setString();
	        	  preparedStatement.setString(1, ticketName);
	              preparedStatement.setString(2, ticketDesc);

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
}
