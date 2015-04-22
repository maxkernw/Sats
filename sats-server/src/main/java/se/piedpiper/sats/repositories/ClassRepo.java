package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

public class ClassRepo
{
	static final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
	static final String USER = "AdminSATS";
	static final String PASSWORD = "WeAreTheCool";
	
	public static TreeSet<se.piedpiper.sats.models.Class> getClasses()
	{
		TreeSet<se.piedpiper.sats.models.Class> classes = new TreeSet<>();
		
		try (
    		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM class;");
				) {
    	  
			String id;
			String centerId;
			String centerFilterId;
			String classTypeId;
			int durationInMinutes;
			String instructorId;
			Date startTime;
			int bookedPersonsCount;
			int maxPersonsCount;
			String regionId;
			int waitingListCount;
			ArrayList<Integer> classCategories = new ArrayList<Integer>();
			int classCatIds;
			String name;
 
         System.out.println("The records selected are:");
         int rowCount = 0;
         
         while(rs.next()) {
            id = rs.getString("id");
            centerId = rs.getString("center_id");
            centerFilterId = rs.getString("center_filter_id");
            classTypeId = rs.getString("class_type_id");
            durationInMinutes = rs.getInt("duration_in_minutes");
            instructorId = rs.getString("instructor_id");
            startTime = rs.getDate("start_time");
            bookedPersonsCount = rs.getInt("booked_persons_count");
            maxPersonsCount = rs.getInt("max_persons_count");
            regionId = rs.getString("region_id");
            waitingListCount = rs.getInt("waiting_list_count");
            classCatIds = rs.getInt("class_category_ids");
            name = rs.getString("name");

            classCategories = getCategoryIds(classCatIds);
            
            System.out.println(id + ", " + centerId + ", " + centerFilterId + ", " + classTypeId + ", " + durationInMinutes + ", " + instructorId + ", " + startTime + ", " + bookedPersonsCount + ", " + maxPersonsCount + ", " + regionId + ", " + waitingListCount + ", " + classCategories + ", " + name);
            ++rowCount;
            
            se.piedpiper.sats.models.Class aClass = new se.piedpiper.sats.models.Class(centerId, centerFilterId, classTypeId, durationInMinutes, id, instructorId, name, startTime, bookedPersonsCount, maxPersonsCount, regionId, waitingListCount, classCategories);
            
            classes.add(aClass);

         }
         System.out.println("Total number of records = " + rowCount);
 
      } catch(SQLException ex) {
         ex.printStackTrace();
         System.out.println("Could not connect to 'class' table: " + ex);
      }
	 return classes;
	}

	private static ArrayList<Integer> getCategoryIds(int class_category_ids)
	{
		ArrayList<Integer> classCategories = new ArrayList<Integer>();
		try (
        		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    			Statement stmt = con.createStatement();
    			ResultSet rs = stmt.executeQuery("SELECT * FROM class_category_ids WHERE category_id = " + class_category_ids + ";");
    				) {
        	  
    			int relation_id;
             
             while(rs.next()) {
                relation_id = rs.getInt("relation_id");
                
                classCategories.add(relation_id);
             }             
          } catch(SQLException ex) {
             ex.printStackTrace();
             System.out.println("Could not connect to 'class_category_ids' table: " + ex);
          }
		return classCategories;
	}

	public static se.piedpiper.sats.models.Class getClass(String classId)
	{
		se.piedpiper.sats.models.Class aClass = null;
		
      try (
    		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM class WHERE id = '" + classId + "';");
    		  ) 
    {
		String centerId;
		String centerFilterId;
		String classTypeId;
		int durationInMinutes;
		String instructorId;
		Date startTime;
		int bookedPersonsCount;
		int maxPersonsCount;
		String regionId;
		int waitingListCount;
		ArrayList<Integer> classCategories = new ArrayList<Integer>();
		int classCatIds;
		String name;

       while(rs.next()) {
          centerId = rs.getString("center_id");
          centerFilterId = rs.getString("center_filter_id");
          classTypeId = rs.getString("class_type_id");
          durationInMinutes = rs.getInt("duration_in_minutes");
          instructorId = rs.getString("instructor_id");
          startTime = rs.getDate("start_time");
          bookedPersonsCount = rs.getInt("booked_persons_count");
          maxPersonsCount = rs.getInt("max_persons_count");
          regionId = rs.getString("region_id");
          waitingListCount = rs.getInt("waiting_list_count");
          classCatIds = rs.getInt("class_category_ids");
          name = rs.getString("name");

          classCategories = getCategoryIds(classCatIds);
          
          aClass = new se.piedpiper.sats.models.Class(centerId, centerFilterId, classTypeId, durationInMinutes, classId, instructorId, name, startTime, bookedPersonsCount, maxPersonsCount, regionId, waitingListCount, classCategories);
          
       }
      } catch(SQLException ex)
      {
         ex.printStackTrace();
         System.out.println("Could not connect to 'class' table: " + ex);
      }
      return aClass;
	}

}
