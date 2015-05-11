package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import se.piedpiper.sats.errors.DatabaseException;
import se.piedpiper.sats.models.Activity;
import se.piedpiper.sats.models.Booking;

public final class ActivityRepo
{

	public static TreeSet<Activity> getActivities(Date dateFrom, Date dateTo)
	{
		java.sql.Date fromDate = new java.sql.Date(dateFrom.getTime());
		java.sql.Date toDate = new java.sql.Date(dateTo.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		StringBuilder builderFrom = new StringBuilder(sdf.format(fromDate));
		StringBuilder builderTo = new StringBuilder(sdf.format(toDate));
		builderFrom.replace(11, 13, "00");
		builderTo.replace(11, 13, "00");
		
		TreeSet<Activity> activities = new TreeSet<>();
		Booking booking = null;

		try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM training_activities WHERE date between '" + builderFrom + "' and + '" + builderTo + "';");)
		{

			String bookingId = "";
			String comment;
			Date date;
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
				Timestamp timestamp = rs.getTimestamp("date");
			    date = new Date(timestamp.getTime());
			    
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				if(bookingId != null){					
					booking = BookingRepo.getBooking(id);
				}

				Activity activity = new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
				activities.add(activity);
			}

		}
		catch (SQLException ex)
		{
			throw new DatabaseException(ex.getMessage());
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
			Date date;
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
					//System.out.println(bookingId);
				}
				comment = rs.getString("comment");
				Timestamp timestamp = rs.getTimestamp("date");
			    date = new Date(timestamp.getTime());
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
			throw new DatabaseException(ex.getMessage());
		}
		 return activity;
	}
	
	private static Connection getConnection() throws SQLException
	{
//		final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
//		final String USER = "AdminSATS";
//		final String PASSWORD = "WeAreTheCool";
		
		final String DB_URL = "jdbc:mysql://127.0.0.1:3306/sats";
		final String USER = "root";
		final String PASSWORD = "";
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, USER, PASSWORD);
		}
		catch(SQLException | ClassNotFoundException e)
		{
			throw new DatabaseException(e.getMessage());
		}
	}
	
}
