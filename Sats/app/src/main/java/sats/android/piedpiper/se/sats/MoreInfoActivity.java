package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.ClassType;

import static sats.android.piedpiper.se.sats.R.layout.class_info_view;
import static sats.android.piedpiper.se.sats.R.layout.my_training_listview;

public class MoreInfoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayerView videoUrlV;
    private YouTubePlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info_view);
        Log.i("ACTIVITETEN", "Create !!!!!");

        Intent intent= getIntent();
        String classtypeId = intent.getStringExtra("classTypeId");


        ClassType classTypeObj = null;


        videoUrlV = (YouTubePlayerView) findViewById(R.id.VideoURL);
        videoUrlV.initialize("AIzaSyDUts-9-KshgP8Pj9KBaWvyjncDcY0gJ7s", this);


        //videoUrlV.getSettings().setJavaScriptEnabled(true);

        //videoUrlV.getSettings().setPluginState(WebSettings.PluginState.ON);
        //videoUrlV.loadUrl(classTypeObj.videoURL);
        //videoUrlV.setWebChromeClient(new WebChromeClient());



        //visa classType i layout

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //videoUrlV.getSettings().setJavaScriptEnabled(false);
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {
        this.player = youTubePlayer;
        APIResponseHandler handler = new APIResponseHandler(this);

        ArrayList<ClassType> classTypes = handler.getClassTypes();

        Intent intent= getIntent();

        String classtypeId = intent.getStringExtra("classTypeId");

        ClassType classTypeObj = null;

        for (ClassType classType : classTypes)
        {
            if (classType.id.equals(classtypeId))
            {
                classTypeObj = classType;
            }
        }
        //videoUrlV.loadVideo(classTypeObj.videoURL);
        youTubePlayer.loadVideo(classTypeObj.videoURL.substring(30, 41));
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {

    }
}