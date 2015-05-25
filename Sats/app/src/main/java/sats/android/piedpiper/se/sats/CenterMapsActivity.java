package sats.android.piedpiper.se.sats;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.HashMap;


public class CenterMapsActivity extends FragmentActivity {

    private GoogleMap map;
    HashMap<String, LatLng> markers = new HashMap();
    APIResponseHandler handler = new APIResponseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_maps);

        handler.getCenterLocations();

        markers = handler.getMarkers();

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (map != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        map.setMyLocationEnabled(true);

        Location userLocation = map.getMyLocation();
        LatLng myLocation = null;


        for (HashMap.Entry<String,LatLng> entry : markers.entrySet()) {
            String name = entry.getKey();
            LatLng kord = entry.getValue();

            map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.sats_pin_normal))
                    .position(kord)
                    .title(name));
        }
        if (userLocation != null) {
            myLocation = new LatLng(userLocation.getLatitude(),
                    userLocation.getLongitude());
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    map.getMaxZoomLevel() - 5));
        }
    }

}
