package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * Created by Max on 25/05/15.
 */
public class InfoWebPage extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_info);

        WebView webView = (WebView) findViewById(R.id.webview_info_page);
        String url = "http://www.google.com";
        webView.loadUrl(url);




    }



}
