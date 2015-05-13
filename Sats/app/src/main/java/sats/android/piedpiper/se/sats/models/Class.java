package sats.android.piedpiper.se.sats.models;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

public final class Class
{
    public final String centerId;
    public final String centerFilterId;
    public final String classTypeId;
    public final int durationInMinutes;
    public final String id;
    public final String instructorId;
    public final String name;
    public final DateTime startTime;
    public final int bookedPersonsCount;
    public final int maxPersonsCount;
    public final int waitingListCount;
    public final ArrayList<Integer> classCategoryIds;

    public Class(String centerId, String centerFilterId, String classTypeId,
                 int durationInMinutes, String id, String instructorId, String name,
                 DateTime startTime, int bookedPersonsCount, int maxPersonsCount,
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
}