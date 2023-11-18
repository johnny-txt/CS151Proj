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

import application.ProjectBean;

public class ProjectDAO {
	
	// Create database table for projects
	public void createProjectTable() {
        
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
            
        		// Create table with 4 columns (id, name, date, desc) if doesn't exist
        		String createTableSQL = "CREATE TABLE IF NOT EXISTS project_table (" +
        				"id INTEGER PRIMARY KEY, " +
        				"projectName TEXT, " +
        				"projectDate DATE, " +
        				"projectDescription TEXT" +
        				")";
        		statement.executeUpdate(createTableSQL);
        		System.out.println("project_table created successfully.");
        
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to create project_table: " + e.getMessage());
        }
    }
	
	// Inserting a new project into the project table
	public void insertProject(ProjectBean proj) {
		// Attempt to load driver
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
		// Get project information
  	  	String projName = proj.getName();
  	  	LocalDate projDate = proj.getDate();
  	  	String projDesc = proj.getDescription();
  	  
  	  // Connect to database
  	  try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
          
  		  // Inserts project into project table
  		  String insert = "INSERT INTO project_table (projectName, projectDate, projectDescription) VALUES (?, ?, ?)";
          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
              preparedStatement.setString(1, projName);
              preparedStatement.setDate(2, java.sql.Date.valueOf(projDate));
              preparedStatement.setString(3, projDesc);
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
	
	// Retrives a list of project names from the project table
	public static List<String> getProjectNames() {
        List<String> projectNames = new ArrayList<>();
        
        // Attempts to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectNames;
        }
        
        // Connects to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement()) {
        	
        	// Query to select all project names from project table
        	String selectSQL = "SELECT projectName FROM project_table";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            
            // Iterates through set and retrieves the project names one by one, adding to list
            while (resultSet.next()) {
                String projectName = resultSet.getString("projectName");
                projectNames.add(projectName);
            }
        
        // Handles exception
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project names from the database: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Retrieved project names: " + projectNames);
        return projectNames;
    }
	
	// Retrives a list of project descriptions from the project table
	public static List<String> getProjectDesc() {
        List<String> projectDesc = new ArrayList<>();
        
        // Attempts to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectDesc;
        }
        
        // Connects to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement()) {
            
        	// Query to select all project descriptions from project table
        	String selectSQL = "SELECT projectDescription FROM project_table";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            
         // Iterates through set and retrieves the project descriptions one by one, adding to list
            while (resultSet.next()) {
                String pDesc = resultSet.getString("projectDescription");
                projectDesc.add(pDesc);
            }
        
        // Handles exception
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project descriptions from the database: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Retrieved project descriptions: " + projectDesc);
        return projectDesc;
    }
	
	// Retrieve ID of a project from project table
	public static int getProjectIDByName(String projectName) {
        int projectID = -1;
        
        // Attempt to load driver
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectID;
        }
        
        // Connects to database
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            // Query to select ID of the project with specified name from project table
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM project_table WHERE projectName = ?")) {
        	preparedStatement.setString(1, projectName);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // If set contains row, means project was found and retrieves 'id'
            if (resultSet.next()) {
                projectID = resultSet.getInt("id");
            }
       
        // Handles exceptions
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return projectID;
    }
	
	// Retrieves a list of project IDs from ticket table
		public static List<Integer> getProjectIDs(){
			List<Integer> projectIDs = new ArrayList<>();
			
			// Attempts to load driver
			try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return projectIDs;
	        }
			
			// Connects to database
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		            Statement statement = connection.createStatement()) {
		            
	        		// Query to select all project IDs from ticket table
					String selectSQL = "SELECT id FROM project_table";
		            ResultSet resultSet = statement.executeQuery(selectSQL);
		            
		            // Iterates through set and retrieves the project IDs one by one, adding to list
		            while (resultSet.next()) {
		                int ticketID = resultSet.getInt("id");
		                projectIDs.add(ticketID);
		            }
		        
			// Handles exception
			} catch (SQLException e) {
		            System.out.println("Failed to retrieve project names from the database: " + e.getMessage());
		            e.printStackTrace();
		        }
			System.out.println("Retrieved project id's: " + projectIDs);
			return projectIDs;
		}
		
		// Retrieve the name of project from project table based on project ID
		public static String getProjectNameByID(int projectID) {
	        String projectName = null;
	        
	        // Attempt to load driver
	        try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return projectName;
	        }
	        
	        // Connect to database
	        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            
	        	// Query to select name of ticket with the specified ID from ticket table
	        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT projectName FROM project_table WHERE id = ?")) {
	            preparedStatement.setInt(1, projectID);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            
	            // If set contains a row, means ticket with specified ID was found
	            if (resultSet.next()) {
	                projectName = resultSet.getString("projectName");
	            }
	        
	        // Error message if failed to retrieve project name
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve project name from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return projectName;
	    }
		
		// Retrieve the desc of project from ticket table based on project ID
		public static String getProjectDescByID(int projectID) {
			String projDesc = null;
			
			// Attempt to load driver
	        try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return projDesc;
	        }
	        
	        // Connects to database
	        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	        	// Query to select description of ticket with the specified ID from ticket table
	        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT projectDescription FROM project_table WHERE id = ?")) {
	            preparedStatement.setInt(1, projectID);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            
	            // If set contains a row, means ticket with specified ID was found
	            if (resultSet.next()) {
	                projDesc = resultSet.getString("projectDescription");
	            }
	            
	        // Error message if failed to retrieve ticket description
	        } catch (SQLException e) {
	            System.out.println("Failed to retrieve project description from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
	        return projDesc;
		}
		public static void deleteProj(int projectID) {
			// Attempt to load sqlite driver
			try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return;
	        }
			
			String query = "DELETE FROM project_table WHERE id = ?";
			try(Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db");
				PreparedStatement preparedStatement = connect.prepareStatement(query)){
			    preparedStatement.setInt(1, projectID);
			    preparedStatement.execute();
			}

			catch (SQLException e) {
	            System.out.println("Failed to delete project from the database: " + e.getMessage());
	            e.printStackTrace();
	        }
		} 
		
		public static void updateDesc(int projectID, String updatedDesc) {// Attempt to load sqlite driver
			try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return;
	        }
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		        	// Query to select description of ticket with the specified ID from ticket table
		        	PreparedStatement preparedStatement = connection.prepareStatement("UPDATE project_table SET projectDescription = ? WHERE projectID = ?")) {
				    preparedStatement.setString(1, updatedDesc);
		            preparedStatement.setInt(2, projectID);		           		          		  
		        } catch (SQLException e) {
		            System.out.println("Failed to retrieve project description from the database: " + e.getMessage());
		            e.printStackTrace();
		        }	
		}
		
		public static void updateName(int projectID, String newName) {// Attempt to load sqlite driver
			try {
	            Class.forName("org.sqlite.JDBC");
	        } catch (ClassNotFoundException e) {
	            System.err.println("SQLite JDBC driver not found.");
	            e.printStackTrace();
	            return;
	        }
			try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		        	// Query to select description of ticket with the specified ID from ticket table
		        	PreparedStatement preparedStatement = connection.prepareStatement("UPDATE project_table SET projectName = ? WHERE projectID = ?")) {
				    preparedStatement.setString(1, newName);
		            preparedStatement.setInt(2, projectID);		           		          		  
		        } catch (SQLException e) {
		            System.out.println("Failed to retrieve project description from the database: " + e.getMessage());
		            e.printStackTrace();
		        }	
		
		}
}

