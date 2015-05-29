package se.piedpiper.android.sats.models;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class BookingClass extends RealmObject
{
    @PrimaryKey
    private String id;
    private String centerId, centerFilterId, classTypeId, instructorId, name;
    private int durationInMinutes, bookedPersonsCount, maxPersonsCount;
    private Date startTime;
    private int waitingListCount;
    @Ignore
    private ArrayList<Integer> classCategoryIds;

    public BookingClass()
    {
    }

    public BookingClass(String centerId, String centerFilterId, String classTypeId,
                        int durationInMinutes, String id, String instructorId, String name,
                        Date startTime, int bookedPersonsCount, int maxPersonsCount,
                        int waitingListCount, ArrayList<Integer> classCategoryIds)
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
        this.waitingListCount = waitingListCount;
        this.classCategoryIds = classCategoryIds;
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

    public int getWaitingListCount()
    {
        return waitingListCount;
    }

    public ArrayList<Integer> getClassCategoryIds()
    {
        return classCategoryIds;
    }

    public void setCenterId(String centerId)
    {
        this.centerId = centerId;
    }

    public void setCenterFilterId(String centerFilterId)
    {
        this.centerFilterId = centerFilterId;
    }

    public void setClassTypeId(String classTypeId)
    {
        this.classTypeId = classTypeId;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setInstructorId(String instructorId)
    {
        this.instructorId = instructorId;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public void setBookedPersonsCount(int bookedPersonsCount)
    {
        this.bookedPersonsCount = bookedPersonsCount;
    }

    public void setMaxPersonsCount(int maxPersonsCount)
    {
        this.maxPersonsCount = maxPersonsCount;
    }

    public void setWaitingListCount(int waitingListCount)
    {
        this.waitingListCount = waitingListCount;
    }

    public void setClassCategoryIds(ArrayList<Integer> classCategoryIds)
    {
        this.classCategoryIds = classCategoryIds;
    }
}