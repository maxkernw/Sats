package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import se.piedpiper.sats.models.Booking;

public class BookingRepo
{
	
	static final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
	static final String USER = "AdminSATS";
	static final String PASSWORD = "WeAreTheCool";
	
	public static TreeSet<Booking> getBookings()
	{
		TreeSet<Booking> bookings = new TreeSet<>();
		
		try (
    		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM booking;");
				) {
    	  
    	  String id;
    	  String status;
    	  se.piedpiper.sats.models.Class aClass = null;
    	  String classId;
    	  String center;
    	  int positionInQueue;
 
         System.out.println("The records selected are:");
         int rowCount = 0;
         
         while(rs.next()) {
            id = rs.getString("id");
            status = rs.getString("status");
            classId = rs.getString("class");
            center = rs.getString("center");
            positionInQueue = rs.getInt("position_in_queue");
        	 
            if(classId != null)
            {
            	aClass = ClassRepo.getClass(classId);
            }
            
            System.out.println(id + ", " + status + ", " + aClass + ", " + center + ", " + positionInQueue);
            ++rowCount;
            
            Booking booking = new Booking(status, aClass, center, id, positionInQueue);
            
            bookings.add(booking);

         }
         System.out.println("Total number of records = " + rowCount);
 
      } catch(SQLException ex) {
         ex.printStackTrace();
         System.out.println("Could not connect to 'booking' table: " + ex);
      }
	 return bookings;
	}

	public static Booking getBooking(String bookingId)
	{
		Booking booking = null;
      try (
    		Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM booking WHERE id = '" + bookingId + "';");
    		  ) 
    {
    	  
    	  String status;
    	  se.piedpiper.sats.models.Class aClass = null;
    	  String classId;
    	  String center;
    	  int positionInQueue;
 
         while(rs.next())
         {
             status = rs.getString("status");
             classId = rs.getString("class");
             center = rs.getString("center");
             positionInQueue = rs.getInt("position_in_queue");
            
             if(classId != null)
             {
             	aClass = ClassRepo.getClass(classId);
             }
             
             System.out.println(bookingId + ", " + status + ", " + aClass + ", " + center + ", " + positionInQueue);
             
             booking = new Booking(status, aClass, center, bookingId, positionInQueue);
             
         } 
      } catch(SQLException ex)
      {
         ex.printStackTrace();
         System.out.println("Could not connect to 'booking' table: " + ex);
      }
	return booking;
	}

}
