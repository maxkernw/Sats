package sats.android.piedpiper.se.sats.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.maps.model.Marker;
import sats.android.piedpiper.se.sats.R;

public class CenterDetailView extends ActionBarActivity
{
    public static Marker marker;

    public static void setMarker(Marker marker)
    {
        CenterDetailView.marker = marker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_detail_view);

        WebView web = (WebView) findViewById(R.id.webview_detail);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }

        });
        web.loadUrl(MainActivity.markers.get(marker.getTitle()).getUrl());



    }
}
