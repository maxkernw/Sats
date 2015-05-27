package sats.android.piedpiper.se.sats.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Booking extends RealmObject
{
    @PrimaryKey
    private String id;
    private String status, center;
    private int positionInQueue;
    private RealmList<BookingClass> bookingClasses;
    @Ignore
    private BookingClass aBookingClass;

    public Booking()
    {
    }

    public Booking(String status, BookingClass aBookingClass, String center, String id,
                   int positionInQueue)
    {
        this.status = status;
        this.aBookingClass = aBookingClass;
        this.center = center;
        this.id = id;
        this.positionInQueue = positionInQueue;
    }

    public String getStatus()
    {
        return status;
    }

    public BookingClass getaBookingClass()
    {
        return aBookingClass;
    }

    public RealmList<BookingClass> getBookingClasses()
    {
        return bookingClasses;
    }

    public void setBookingClasses(RealmList<BookingClass> bookingClasses)
    {
        this.bookingClasses = bookingClasses;
    }

    public String getCenter()
    {
        return center;
    }

    public String getId()
    {
        return id;
    }

    public int getPositionInQueue()
    {
        return positionInQueue;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setaBookingClass(BookingClass aBookingClass)
    {
        this.aBookingClass = aBookingClass;
    }

    public void setCenter(String center)
    {
        this.center = center;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setPositionInQueue(int positionInQueue)
    {
        this.positionInQueue = positionInQueue;
    }
}