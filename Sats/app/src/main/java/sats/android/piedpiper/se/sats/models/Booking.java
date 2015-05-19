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
    private RealmList<Klass> klasses;
    @Ignore
    private Klass aKlass;

    public Booking()
    {
    }

    public Booking(String status, Klass aKlass, String center, String id,
                   int positionInQueue)
    {
        this.status = status;
        this.aKlass = aKlass;
        this.center = center;
        this.id = id;
        this.positionInQueue = positionInQueue;
    }

    public String getStatus()
    {
        return status;
    }

    public Klass getaKlass()
    {
        return aKlass;
    }

    public RealmList<Klass> getKlasses()
    {
        return klasses;
    }

    public void setKlasses(RealmList<Klass> klasses)
    {
        this.klasses = klasses;
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

    public void setaKlass(Klass aKlass)
    {
        this.aKlass = aKlass;
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