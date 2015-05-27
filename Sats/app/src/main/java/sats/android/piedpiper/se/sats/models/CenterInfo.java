package sats.android.piedpiper.se.sats.models;

import com.google.android.gms.maps.model.LatLng;

import io.realm.RealmObject;

public class CenterInfo extends RealmObject
{
    private double lati;
    private double longi;
    private String url;

    public CenterInfo()
    {
    }

    public CenterInfo(String x, double lati, double longi){
        this.lati = lati;
        this.longi = longi;
        this.url = x;
    }

    public double getLati()
    {
        return lati;
    }

    public void setLati(double lati)
    {
        this.lati = lati;
    }

    public double getLongi()
    {
        return longi;
    }

    public void setLongi(double longi)
    {
        this.longi = longi;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
