package sats.android.piedpiper.se.sats;

/**
 * Created by Osama on 2015-04-24.
 */

public final class Center {
    private boolean availableForOnlineBooking, isElixia;
    private String description, name, url;
    private int filterId, id, lati, longi, regionId;

    public Center(boolean availableForOnlineBooking, boolean isElixia, String description, String name, String url, int filterId, int id, int lati, int longi, int regionId) {
        this.availableForOnlineBooking = availableForOnlineBooking;
        this.isElixia = isElixia;
        this.description = description;
        this.name = name;
        this.url = url;
        this.filterId = filterId;
        this.id = id;
        this.lati = lati;
        this.longi = longi;
        this.regionId = regionId;
    }

    public boolean isAvailableForOnlineBooking() {
        return availableForOnlineBooking;
    }

    public boolean isElixia() {
        return isElixia;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getFilterId() {
        return filterId;
    }

    public int getId() {
        return id;
    }

    public int getLati() {
        return lati;
    }

    public int getLongi() {
        return longi;
    }

    public int getRegionId() {
        return regionId;
    }
}