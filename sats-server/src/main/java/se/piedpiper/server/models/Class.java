package se.piedpiper.server.models;

import java.util.ArrayList;
import java.util.Date;

public final class Class {
	
	private final int centerId;
	private final int centerFilterId;
	private final int classTypeId;
	private final int durationInMinutes;
	private final int id;
	private final int instructorId;
	private final String name;
	private final Date startTime;
	private final int bookedPersonsCount;
	private final int maxPersonsCount;
	private final int regionId;
	private final int waitingListCount;
	private final ArrayList<Integer> classCategories;
	
	public Class(int centerId, int centerFilterId, int classTypeId,
			int durationInMinutes, int id, int instructorId, String name,
			Date startTime, int bookedPersonsCount, int maxPersonsCount,
			int regionId, int waitingListCount,
			ArrayList<Integer> classCategories) {
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
	
	public int getCenterId() {
		return centerId;
	}
	public int getCenterFilterId() {
		return centerFilterId;
	}
	public int getClassTypeId() {
		return classTypeId;
	}
	public int getDurationInMinutes() {
		return durationInMinutes;
	}
	public int getId() {
		return id;
	}
	public int getInstructorId() {
		return instructorId;
	}
	public String getName() {
		return name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public int getBookedPersonsCount() {
		return bookedPersonsCount;
	}
	public int getMaxPersonsCount() {
		return maxPersonsCount;
	}
	public int getRegionId() {
		return regionId;
	}
	public int getWaitingListCount() {
		return waitingListCount;
	}
	public ArrayList<Integer> getClassCategories() {
		return classCategories;
	}
		
}
