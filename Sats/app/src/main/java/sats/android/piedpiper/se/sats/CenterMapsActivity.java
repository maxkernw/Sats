package sats.android.piedpiper.se.sats;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.HashMap;

import sats.android.piedpiper.se.sats.models.Center;


public class CenterMapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    private GoogleMap map;
    private GoogleApiClient gapi ;
    HashMap<String, YMCA> markers = new HashMap();

    private View mGhost;
    public static double longitude = 18.0785538;
    public static double latitude = 59.2937625;

    //WebView webView = new WebView(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_maps);
        APIResponseHandler handler = new APIResponseHandler(this);
        Location location = new Location("My location");
        mGhost = new View(this);
        mGhost.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
        mGhost.setVisibility(View.GONE);
        gapi = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();



        markers = handler.markers;
        Log.e("Log", "Markers: " + markers.size());
        //handler.getCenterLocations();

        //markers = handler.getMarkers();

        setUpMapIfNeeded();
        gapi.connect();






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
            map.setMyLocationEnabled(true);

            if (map != null) {
                setUpMap();
            }
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude) ,14) );

        }
    }

    private void setUpMap()
    {


        for (HashMap.Entry<String, YMCA> entry : markers.entrySet())
        {
            final String center = entry.getKey();
            final YMCA why = entry.getValue();

            map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.sats_pin_small))
                            .position(why.coords).title(center).flat(true)
            );
            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
            {
                @Override
                public void onInfoWindowClick(Marker marker)
                {
                    //setContentView(webView);
                    WebView webview = new WebView(CenterMapsActivity.this);


                    webview.loadUrl(markers.get(marker.getTitle()).url);


                }
            });
        }
                map.setMyLocationEnabled(true);





    }


    @Override
    public void onConnected(Bundle bundle)
    {
        Location loc = LocationServices.FusedLocationApi.getLastLocation(gapi);
        //Log.e("Loc", "Location: " + loc.getLatitude() + "long: " + loc.getLongitude());
        //longitude = loc.getLongitude();
        //latitude = loc.getLatitude();


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


