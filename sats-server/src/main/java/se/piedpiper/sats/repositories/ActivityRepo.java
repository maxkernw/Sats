package se.piedpiper.sats.repositories;

import java.sql.Connection;
//import java.sql.Date;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.management.RuntimeErrorException;

import se.piedpiper.sats.models.Activity;
import se.piedpiper.sats.models.Booking;

public final class ActivityRepo
{

	public static TreeSet<Activity> getActivities()
	{
		TreeSet<Activity> activities = new TreeSet<>();
		Booking booking = null;

		try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM training_activities;");)
		{

			String bookingId = "";
			String comment;
			java.sql.Date date;
			int distanceInKm;
			int durationInMinutes;
			String id;
			String source;
			String status;
			String subType;
			String type;

			System.out.println("The records selected are:");
			int rowCount = 0;

			while (rs.next())
			{
				id = rs.getString("id");
				if(rs.getString("booking") != null){
					bookingId = rs.getString("booking");
				}
				comment = rs.getString("comment");
				date = rs.getDate("date");
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				if ("".compareTo(bookingId) != 0)
				{
					booking = BookingRepo.getBooking(bookingId);
				}else{
					booking = new Booking("", new se.piedpiper.sats.models.Class("", "", "", 0, "", "", "",new Date(), 0, 0, "", 0, new ArrayList<Integer>()), "", "", 0);
				}

				System.out.println(date + ", " + id + ", " + booking + ", " + comment + ", " + distanceInKm + ", " + durationInMinutes + ", " + source + ", " + status
						+ ", " + subType + ", " + type);
				++rowCount;

				Activity activity = new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
				activities.add(activity);
			}
			System.out.println("Total number of records = " + rowCount);

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println("Could not connect to 'training_activities' table: " + ex);
		}
		 return activities;
	}

	public static Activity getActivity(String id)
	{
		Activity activity = null;
		Booking booking = null;

		try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM training_activities WHERE id = '"
						+ id + "';");)
		{

			String bookingId = "";
			String comment;
			java.sql.Date date;
			int distanceInKm;
			int durationInMinutes;
			String source;
			String status;
			String subType;
			String type;

			while (rs.next())
			{
				if(rs.getString("booking") != null){
					bookingId = rs.getString("booking");
				}
				comment = rs.getString("comment");
				date = rs.getDate("date");
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				if (bookingId != "")
				{
					booking = BookingRepo.getBooking(bookingId);
				}

				activity = new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
				
				System.out.println(booking + ", " + comment + ", " + date + ", " + distanceInKm + ", " + durationInMinutes + ", " + id + ", " + source + ", " + status + ", " + subType + ", " + type);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			System.out.println("Could not connect to 'training_activities' table: " + ex);
		}
		 return activity;
	}
	
	private static Connection getConnection() throws SQLException
	{
		final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
		final String USER = "AdminSATS";
		final String PASSWORD = "WeAreTheCool";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			throw new RuntimeErrorException(null, e.toString());
		}
	}
	
}
