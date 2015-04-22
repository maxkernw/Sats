package se.piedpiper.sats.models;

import java.util.ArrayList;
import java.util.Date;

public final class Class implements Comparable<Object>
{

	private final String centerId;
	private final String centerFilterId;
	private final String classTypeId;
	private final int durationInMinutes;
	private final String id;
	private final String instructorId;
	private final String name;
	private final Date startTime;
	private final int bookedPersonsCount;
	private final int maxPersonsCount;
	private final String regionId;
	private final int waitingListCount;
	private final ArrayList<Integer> classCategories;

	public Class(String centerId, String centerFilterId, String classTypeId,
			int durationInMinutes, String id, String instructorId, String name,
			Date startTime, int bookedPersonsCount, int maxPersonsCount,
			String regionId, int waitingListCount,
			ArrayList<Integer> classCategories)
	{
		this.centerId = centerId;
		this.centerFilterId = centerFilterId;
		this.classTypeId = classTypeId;
		this.durationInMinutes = durationInMinutes;
		this.id = id;
		this.instructorId = instructorId;
		this.name = name;
		this.startTime = startTime;
		this.bookedPersonsCount = bookedPersonsCount;
		this.maxPersonsCount = maxPersonsCount;
		this.regionId = regionId;
		this.waitingListCount = waitingListCount;
		this.classCategories = classCategories;
	}

	public String getCenterId()
	{
		return centerId;
	}

	public String getCenterFilterId()
	{
		return centerFilterId;
	}

	public String getClassTypeId()
	{
		return classTypeId;
	}

	public int getDurationInMinutes()
	{
		return durationInMinutes;
	}

	public String getId()
	{
		return id;
	}

	public String getInstructorId()
	{
		return instructorId;
	}

	public String getName()
	{
		return name;
	}

	public Date getStartTime()
	{
		return startTime;
	}

	public int getBookedPersonsCount()
	{
		return bookedPersonsCount;
	}

	public int getMaxPersonsCount()
	{
		return maxPersonsCount;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public int getWaitingListCount()
	{
		return waitingListCount;
	}

	public ArrayList<Integer> getClassCategories()
	{
		return classCategories;
	}
	
	@Override
	public int compareTo(Object obj)
	{
		return this.id.compareTo( ((Class) obj).getId() );
	}
}
