package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.ClassType;

import static sats.android.piedpiper.se.sats.R.layout.class_info_view;
import static sats.android.piedpiper.se.sats.R.layout.my_training_listview;

public class MoreInfoActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info_view);

        Intent intent= getIntent();
        String classtypeId = intent.getStringExtra("classTypeId");

        APIResponseHandler handler = new APIResponseHandler(this);
        ArrayList<ClassType> classTypes = handler.getClassTypes();

        ClassType classTypeObj = null;
        for (ClassType classType : classTypes){
            if (classType.id.equals(classtypeId)){
                classTypeObj = classType;
            }
        }

        WebView videoUrlV = (WebView) findViewById(R.id.VideoURL);

        videoUrlV.getSettings().setJavaScriptEnabled(true);

        videoUrlV.getSettings().setPluginState(WebSettings.PluginState.ON);
        videoUrlV.loadUrl(classTypeObj.videoURL);
        videoUrlV.setWebChromeClient(new WebChromeClient());

        
        //visa classType i layout

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebView videoUrlV = (WebView) findViewById(R.id.VideoURL);
        videoUrlV.destroy();
    }
}