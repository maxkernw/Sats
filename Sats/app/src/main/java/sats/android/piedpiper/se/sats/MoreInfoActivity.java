package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
import sats.android.piedpiper.se.sats.models.Profile;

import static sats.android.piedpiper.se.sats.R.layout.class_info_view;
import static sats.android.piedpiper.se.sats.R.layout.my_training_listview;

public class MoreInfoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayerView videoUrlV;
    private YouTubePlayer player;
    private String GOOGLE_KEY = "AIzaSyDUts-9-KshgP8Pj9KBaWvyjncDcY0gJ7s";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info_view);
        Log.i("ACTIVITETEN", "Create !!!!!");

        Intent intent= getIntent();
        String classtypeId = intent.getStringExtra("classTypeId");

        ClassType classTypeObj = null;


        TextView classTitle = (TextView) findViewById(R.id.class_name);
        TextView duration = (TextView) findViewById(R.id.class_duration_time);
        TextView posInQueue = (TextView) findViewById(R.id.position_in_queue);
        TextView bookedPersons = (TextView) findViewById(R.id.booked_persons_count);
        TextView maxBookedPersons = (TextView) findViewById(R.id.max_persons_count);
        TextView center = (TextView) findViewById(R.id.center_name);
        TextView dateStartTime = (TextView) findViewById(R.id.date);
        TextView instructor = (TextView) findViewById(R.id.instructor);
        TextView description = (TextView) findViewById(R.id.class_information);

        ProgressBar kondition = (ProgressBar) findViewById(R.id.fitness_progress);
        ProgressBar strength = (ProgressBar) findViewById(R.id.strength_progress);
        ProgressBar flexibility = (ProgressBar) findViewById(R.id.flexibility_progress);
        ProgressBar balance = (ProgressBar) findViewById(R.id.balance_progress);
        ProgressBar elasticity = (ProgressBar) findViewById(R.id.elasticity_progress);


        videoUrlV = (YouTubePlayerView) findViewById(R.id.VideoURL);
        videoUrlV.initialize(GOOGLE_KEY, this);



        if (classTypeObj != null)
        {
            classTitle.setText(classTypeObj.getName());
            duration.setText(intent.getStringExtra("duration") + "min");

            posInQueue.setText(intent.getStringExtra("posInQueue")); //todo gör osunlig om 0

            bookedPersons.setText(intent.getStringExtra("bookedCount"));
            maxBookedPersons.setText(intent.getStringExtra("maxAttending"));

            center.setText(intent.getStringExtra("centerName"));
            dateStartTime.setText(intent.getStringExtra("startTime"));
            instructor.setText(intent.getStringExtra("instructor"));

            description.setText(classTypeObj.getDescription());

            ArrayList<Profile> profiles = classTypeObj.getProfile();
            for (Profile profile : profiles)
            {
                switch (profile.id) {
                    case "cardio":
                        kondition.setProgress(profile.value);
                        break;
                    case "strength":
                        strength.setProgress(profile.value);
                        break;
                    case "flexibility":
                        flexibility.setProgress(profile.value);
                        break;
                    case "balance":
                        balance.setProgress(profile.value);
                        break;
                    case "agility":
                        elasticity.setProgress(profile.value);
                        break;
                }
            }


        }else{
            Log.e("Info", "classtypeobj is empty");
        }
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