package sats.android.piedpiper.se.sats;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;

public class MyLocationListener implements LocationListener{

    public static LatLng myPosition;

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();

        LatLng myP = new LatLng(location.getLatitude(), location.getLongitude());

        myPosition = myP;


    }
}
