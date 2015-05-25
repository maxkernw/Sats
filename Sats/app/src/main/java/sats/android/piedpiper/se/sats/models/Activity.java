package sats.android.piedpiper.se.sats.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Activity extends RealmObject
{
    @PrimaryKey
    private String id;
    private String comment,source, status, subType, type;
    private int distanceInKm, durationInMinutes;
    private RealmList<Booking> bookings;
    private Date date;
    @Ignore
    private Booking booking;

    public Activity()
    {
    }

    public Activity(Booking booking, String comment, Date date2, int distance,
                    int durationInMinutes, String id, String source, String status,
                    String subType, String type)
    {
        this.booking = booking;
        this.comment = comment;
        this.date = date2;
        this.distanceInKm = distance;
        this.durationInMinutes = durationInMinutes;
        this.id = id;
        this.source = source;
        this.status = status;
        this.subType = subType;
        this.type = type;
    }

    public Booking getBooking()
    {
        return booking;
    }

    public void setBooking(Booking booking)
    {
        this.booking = booking;
    }

    public RealmList<Booking> getBookings()
    {
        return bookings;
    }

    public void setBookings(RealmList<Booking> bookings)
    {
        this.bookings = bookings;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getDistanceInKm()
    {
        return distanceInKm;
    }

    public void setDistanceInKm(int distanceInKm)
    {
        this.distanceInKm = distanceInKm;
    }

    public int getDurationInMinutes()
    {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes)
    {
        this.durationInMinutes = durationInMinutes;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getSubType()
    {
        return subType;
    }

    public void setSubType(String subType)
    {
        this.subType = subType;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}