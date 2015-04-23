package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import se.piedpiper.sats.errors.DatabaseException;
import se.piedpiper.sats.models.Type;

public final class TypeRepo
{

	static final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
	static final String USER = "AdminSATS";
	static final String PASSWORD = "WeAreTheCool";

	public static ArrayList<Type> getTypes()
	{
		ArrayList<Type> types = new ArrayList<>();

		try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM types;");)
		{

			String name;
			String subType;
			String type;

			while (rs.next())
			{
				name = rs.getString("name");
				subType = rs.getString("sub_type");
				type = rs.getString("type");

				Type thisType = new Type(name, type, subType);
				types.add(thisType);
			}

		}
		catch (SQLException ex)
		{
			throw new DatabaseException(ex.getMessage());
		}
		return types;
	}

	public static Type getType(String subType)
	{
		Type thisType = null;

		try (
				Connection con = getConnection();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM types WHERE sub_type = '" + subType + "';");)
		{

			String name;
			String type;

			while (rs.next())
			{
				name = rs.getString("name");
				type = rs.getString("type");

				thisType = new Type(name, type, subType);
			}
		}
		catch (SQLException ex)
		{
			throw new DatabaseException(ex.getMessage());
		}
		return thisType;
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
			throw new DatabaseException(e.getMessage());
		}
	}
}