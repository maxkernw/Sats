package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

			while (rs.next())
			{
				id = rs.getString("id");
				bookingId = rs.getString("booking");
				comment = rs.getString("comment");
				date = rs.getDate("date");
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				booking = BookingRepo.getBooking(bookingId);

				Activity activity = new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
				activities.add(activity);
			}

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
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
				
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
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
