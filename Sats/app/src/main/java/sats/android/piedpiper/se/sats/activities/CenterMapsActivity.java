package sats.android.piedpiper.se.sats.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
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

public final class CenterMapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    private GoogleMap map;
    private GoogleApiClient gapi;
    public static HashMap<String, CenterInfo> markers = new HashMap();
    private View mGhost;
    private TextView txt;

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
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter()
            {
                @Override
                public View getInfoWindow(Marker marker)
                {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker)
                {
                    View v = getLayoutInflater().inflate(R.layout.info_window_map, null);
                    txt = (TextView) v.findViewById(R.id.text_map);
                    txt.setText("SATS " + marker.getTitle());

                    return v;
                }
            });

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
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gapi);

        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);

        map.animateCamera(cameraUpdate);
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


