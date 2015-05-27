package sats.android.piedpiper.se.sats.models;

import com.google.android.gms.maps.model.LatLng;

public class CenterInfo
{
    public LatLng coords;
    public String url;

    public CenterInfo(String x, LatLng y){
        this.coords = y;
        this.url = x;
    }
}
