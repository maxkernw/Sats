package sats.android.piedpiper.se.sats.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Center extends RealmObject
{
    @PrimaryKey
    private int id;
    private boolean availableForOnlineBooking, isElixia;
    private String description, name, url;
    private int filterId, lati, longi, regionId;

    public Center()
    {
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setAvailableForOnlineBooking(boolean availableForOnlineBooking) {
        this.availableForOnlineBooking = availableForOnlineBooking;
    }

    public void setIsElixia(boolean isElixia) {
        this.isElixia = isElixia;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFilterId(int filterId) {
        this.filterId = filterId;
    }

    public void setLati(int lati) {
        this.lati = lati;
    }

    public void setLongi(int longi) {
        this.longi = longi;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}