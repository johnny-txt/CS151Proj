package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import application.ProjectBean;


public class projectDAO {

	
	public void insertProjectValues(ProjectBean proj) {
		
  	  String projName = proj.getProjectName();
  	  String projDesc = proj.getDescription();
  	  LocalDate projDate = proj.getDate();
  	  
  	  try(Connection connect = DriverManager.getConnection("jdbc:sqlite:database.db")){
  		  
  		  Statement state = connect.createStatement();
  		  String insert = "INSERT INTO project_table (projectName, projectDescription, projectDate) VALUES"
  		  		+ " (' " + projName + " ', " + projDesc + "', " + projDate + ")";
  		  state.executeUpdate(insert);
  		  System.out.println("Data inserted");
  		  
  	  } catch(SQLException exception){
  		System.out.println("Something went wrong " + exception.getMessage());
  	  }
  	  
  	  
    }
	
	
	
	
}
