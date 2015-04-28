package sats.android.piedpiper.se.sats.models;

public final class Booking
{

    public final String status;
    public final Class aClass;
    public final String center;
    public final String id;
    public final int positionInQueue;

    public Booking(String status, Class aClass, String center, String id,
                   int positionInQueue)
    {
        this.status = status;
        this.aClass = aClass;
        this.center = center;
        this.id = id;
        this.positionInQueue = positionInQueue;
    }
}

