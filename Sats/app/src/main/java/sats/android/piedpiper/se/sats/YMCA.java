package sats.android.piedpiper.se.sats;

import com.google.android.gms.maps.model.LatLng;

public class YMCA
{
    LatLng coords;
    String url;

    public YMCA(String x, LatLng y){
        this.coords = y;
        this.url = x;
    }
}
