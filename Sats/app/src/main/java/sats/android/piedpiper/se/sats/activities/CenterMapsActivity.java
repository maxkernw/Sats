package sats.android.piedpiper.se.sats.activities;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;
import sats.android.piedpiper.se.sats.R;
import sats.android.piedpiper.se.sats.models.CenterInfo;

public class CenterMapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private GoogleMap map;
    private GoogleApiClient gapi;
    public static HashMap<String, CenterInfo> markers = new HashMap();
    private View mGhost;
    public static double longitude;
    public static double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_maps);
        mGhost = new View(this);
        mGhost.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        mGhost.setVisibility(View.GONE);
        gapi = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        gapi.connect();
        markers = MainActivity.markers;
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded()
    {
        if (map == null)
        {
            map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            map.setMyLocationEnabled(true);

            if (map != null)
            {
                setUpMap();
            }
        }
    }

    private void setUpMap()
    {
        for (HashMap.Entry<String, CenterInfo> entry : markers.entrySet())
        {
            final String center = entry.getKey();
            final CenterInfo why = entry.getValue();
            LatLng coords = new LatLng(why.getLati(), why.getLongi());

            map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.sats_pin_small))
                            .position(coords).title(center).flat(true)
            );
            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    CenterDetailView.setMarker(marker);
                    Intent moreInfo = new Intent(CenterMapsActivity.this, CenterDetailView.class);
                    CenterMapsActivity.this.startActivity(moreInfo, null);
                }
            });
        }
        map.setMyLocationEnabled(true);
        if (latitude != 0 && longitude != 0)
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
        }
        else
        {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(59.293761, 18.0785327), 8));
        }
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gapi);
        longitude = loc.getLongitude();
        latitude = loc.getLatitude();
    }

    @Override
    public void onConnectionSuspended(int i)
    {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {

    }
}


