package application.data_access_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
}
