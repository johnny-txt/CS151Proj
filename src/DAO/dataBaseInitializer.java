package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import application.ProjectBean;


public class dataBaseInitializer {

	public static void initializer() {
      
	
		Connection connection = null;
      
      try {
      connection = DriverManager.getConnection("jdbc:sqlite:database.db");
      Statement statement = connection.createStatement();
      String createTable = "CREATE TABLE IF NOT EXISTS project_table ("
    		  + "projectName STRING PRIMARY KEY,"
    		  + "projectDescription STRING,"
    		  + "projectDate LOCALDATE"
    		  + ")";
      statement.executeUpdate(createTable);
      } catch (SQLException exception) {
    	  System.out.println("Something went wrong " + exception.getMessage());
    	  
      } finally {
    	  try{
    		  if(connection != null) 
    			  connection.close();  
      } catch(SQLException exception){
    	  System.out.println("Could not close the data base " + exception.getMessage());
      }
      
    	  
      }
	  }
}