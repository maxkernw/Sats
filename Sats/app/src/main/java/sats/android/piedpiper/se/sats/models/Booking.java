package sats.android.piedpiper.se.sats.models;

public final class Booking
{

    public final String status;
    public final TrainingActivity aTrainingActivity;
    public final String center;
    public final String id;
    public final int positionInQueue;

    public Booking(String status, TrainingActivity aTrainingActivity, String center, String id,
                   int positionInQueue)
    {
        this.status = status;
        this.aTrainingActivity = aTrainingActivity;
        this.center = center;
        this.id = id;
        this.positionInQueue = positionInQueue;
    }
}

