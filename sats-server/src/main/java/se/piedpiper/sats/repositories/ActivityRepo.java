package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.piedpiper.sats.models.Activity;
import se.piedpiper.sats.models.Booking;

public final class ActivityRepo
{

	static final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
	static final String USER = "AdminSATS";
	static final String PASSWORD = "WeAreTheCool";

	public static ArrayList<Activity> getTypes()
	{
		ArrayList<Activity> activities = new ArrayList<>();
		Booking booking = null;

		try (
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM training_activities;");)
		{

			String bookingId = null;
			String comment;
			Date date;
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
				bookingId = rs.getString("booking");
				comment = rs.getString("comment");
				date = rs.getDate("date");
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				if (bookingId != null)
				{
					booking = BookingRepo.getBooking(bookingId);
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
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM training_activities WHERE id = '"
						+ id + "';");)
		{

			String bookingId = null;
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
				bookingId = rs.getString("booking");
				comment = rs.getString("comment");
				date = rs.getDate("date");
				distanceInKm = rs.getInt("distance_in_km");
				durationInMinutes = rs.getInt("duration_in_minutes");
				source = rs.getString("source");
				status = rs.getString("status");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				if (bookingId != null)
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
}
