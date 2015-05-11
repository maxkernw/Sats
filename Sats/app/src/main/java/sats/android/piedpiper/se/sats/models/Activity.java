package sats.android.piedpiper.se.sats.models;

import org.joda.time.DateTime;

import java.util.Date;

public final class Activity implements Comparable<Object>
{
    public final Booking booking;
    public final String comment;
    public final DateTime date;
    public final int distanceInKm;
    public final int durationInMinutes;
    public final String id;
    public final String source;
    public final String status;
    public final String subType;
    public final String type;

    public Activity(Booking booking, String comment, DateTime date2, int distance,
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

    @Override
    public int compareTo(Object obj)
    {
        return this.date.compareTo( ((Activity) obj).date );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((booking == null) ? 0 : booking.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + distanceInKm;
        result = prime * result + durationInMinutes;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((subType == null) ? 0 : subType.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Activity other = (Activity) obj;
        if (booking == null) {
            if (other.booking != null)
                return false;
        } else if (!booking.equals(other.booking))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (distanceInKm != other.distanceInKm)
            return false;
        if (durationInMinutes != other.durationInMinutes)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (subType == null) {
            if (other.subType != null)
                return false;
        } else if (!subType.equals(other.subType))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
