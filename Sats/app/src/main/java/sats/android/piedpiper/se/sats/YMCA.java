package sats.android.piedpiper.se.sats;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Max on 25/05/15.
 */
public class YMCA
{
    LatLng coords;
    String url;

    public YMCA(String x, LatLng y){
        this.coords = y;
        this.url = x;
    }
}
