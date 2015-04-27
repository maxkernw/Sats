package se.piedpiper.sats.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.TreeSet;

import se.piedpiper.sats.models.Type;

public final class TypeRepo
{

	static final String DB_URL = "jdbc:mysql://80.217.172.201:3306/SATS";
	static final String USER = "AdminSATS";
	static final String PASSWORD = "WeAreTheCool";

	public static void main(String[] args) // Ska heta "getTypes()" och
											// returnera "TreeSet<Type>"
	{
		TreeSet<Type> types = new TreeSet<>();

		try (
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM types;");)
		{

			String name;
			String subType;
			String type;

			System.out.println("The records selected are:");
			int rowCount = 0;
			while (rs.next())
			{
				name = rs.getString("name");
				subType = rs.getString("sub_type");
				type = rs.getString("type");
				System.out.println(name + ", " + subType + ", " + type);
				++rowCount;

				Type thisType = new Type(name, type, subType);
				types.add(thisType);
			}
			System.out.println("Total number of records = " + rowCount);

		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		// return types;
	}

	public static Type getType(String subType)
	{
		Type thisType = null;

		try (
				Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
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
			ex.printStackTrace();
		}
		return thisType;
	}
}