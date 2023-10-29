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

	public void createProjectTable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement statement = connection.createStatement()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS project_table (" +
                    "id INTEGER PRIMARY KEY, " +
                    "projectName TEXT, " +
                    "projectDate DATE, " +
                    "projectDescription TEXT" +
                    ")";
            statement.executeUpdate(createTableSQL);
            System.out.println("project_table created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create project_table: " + e.getMessage());
        }
    }
	
	public void insertProject(ProjectBean proj) {
		try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return;
        }
		
  	  	String projName = proj.getName();
  	  	LocalDate projDate = proj.getDate();
  	  	String projDesc = proj.getDescription();
  	  
  	  try (Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")) {
          String insert = "INSERT INTO project_table (projectName, projectDate, projectDescription) VALUES (?, ?, ?)";

          try (PreparedStatement preparedStatement = connect.prepareStatement(insert)) {
              preparedStatement.setString(1, projName);
              preparedStatement.setDate(2, java.sql.Date.valueOf(projDate));
              preparedStatement.setString(3, projDesc);

              preparedStatement.executeUpdate();
              System.out.println("Data inserted");
          } catch (SQLException e) {
              System.out.println("Failed to execute the SQL statement: " + e.getMessage());
          }
      } catch (SQLException e) {
          System.out.println("Failed to connect to the database: " + e.getMessage());
      }
    }
	
	public static List<String> getProjectNames() {
        List<String> projectNames = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectNames;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement statement = connection.createStatement()) {
            String selectSQL = "SELECT projectName FROM project_table";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                String projectName = resultSet.getString("projectName");
                projectNames.add(projectName);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project names from the database: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Retrieved project names: " + projectNames);
        return projectNames;
    }
	
	public static List<String> getProjectDesc() {
        List<String> projectDesc = new ArrayList<>();

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectDesc;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             Statement statement = connection.createStatement()) {
            String selectSQL = "SELECT projectDescription FROM project_table";
            ResultSet resultSet = statement.executeQuery(selectSQL);

            while (resultSet.next()) {
                String pDesc = resultSet.getString("projectDescription");
                projectDesc.add(pDesc);
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project descriptions from the database: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Retrieved project descriptions: " + projectDesc);
        return projectDesc;
    }
	
	public static int getProjectIDByName(String projectName) {
        int projectID = -1;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found.");
            e.printStackTrace();
            return projectID;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM project_table WHERE projectName = ?")) {
            preparedStatement.setString(1, projectName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                projectID = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve project ID from the database: " + e.getMessage());
            e.printStackTrace();
        }
        return projectID;
    }
}
