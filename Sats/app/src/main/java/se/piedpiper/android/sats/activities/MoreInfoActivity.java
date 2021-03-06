package se.piedpiper.android.sats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.ArrayList;
import se.piedpiper.android.sats.handlers.APIResponseHandler;
import se.piedpiper.android.sats.R;
import se.piedpiper.android.sats.models.ClassType;
import se.piedpiper.android.sats.models.Profile;

public final class MoreInfoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener
{
    private YouTubePlayerView videoUrlV;
    private String GOOGLE_KEY = "AIzaSyDUts-9-KshgP8Pj9KBaWvyjncDcY0gJ7s";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_info_view);
        Intent intent = getIntent();
        String classtypeId = intent.getStringExtra("classTypeId");

        APIResponseHandler handler = new APIResponseHandler(this);
        ArrayList<ClassType> types = handler.getClassTypes();
        ClassType classTypeObj = null;

        for (ClassType type : types)
        {
            if (type.getId().equals(classtypeId))
            {
                classTypeObj = type;
            }
        }

        TextView classTitle = (TextView) findViewById(R.id.class_name);
        TextView duration = (TextView) findViewById(R.id.class_duration_time);
        TextView posInQueue = (TextView) findViewById(R.id.position_in_queue);
        TextView bookedPersons = (TextView) findViewById(R.id.booked_persons_count);
        TextView maxBookedPersons = (TextView) findViewById(R.id.max_persons_count);
        TextView center = (TextView) findViewById(R.id.center_name);
        TextView dateStartTime = (TextView) findViewById(R.id.date);
        TextView instructor = (TextView) findViewById(R.id.instructor);
        TextView description = (TextView) findViewById(R.id.class_information);

        ProgressBar condition = (ProgressBar) findViewById(R.id.fitness_progress);
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

            if(Integer.valueOf(intent.getStringExtra("posInQueue")) == 0){
                ImageView peopleImage = (ImageView) findViewById(R.id.people);
                peopleImage.setVisibility(View.INVISIBLE);
                posInQueue.setVisibility(View.INVISIBLE);
            }else{
                posInQueue.setText(intent.getStringExtra("posInQueue"));
            }

            bookedPersons.setText(intent.getStringExtra("bookedCount"));
            maxBookedPersons.setText(intent.getStringExtra("maxAttending"));

            center.setText(intent.getStringExtra("centerName"));
            dateStartTime.setText(intent.getStringExtra("startTime"));
            instructor.setText(intent.getStringExtra("instructor"));

            description.setText(classTypeObj.getDescription());

            ArrayList<Profile> profiles = classTypeObj.getProfile();
            for (Profile profile : profiles)
            {
                switch (profile.id)
                {
                    case "cardio":
                        condition.setProgress(profile.value);
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
        }
        else
        {
            Log.e("MoreInfoActivity", "classtypeobj is empty");
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
    {
        APIResponseHandler handler = new APIResponseHandler(this);
        ArrayList<ClassType> classTypes = handler.getClassTypes();
        Intent intent = getIntent();
        String classtypeId = intent.getStringExtra("classTypeId");
        ClassType classTypeObj = null;

        for (ClassType classType : classTypes)
        {
            if (classType.id.equals(classtypeId))
            {
                classTypeObj = classType;
            }
        }
        if (classTypeObj != null)
        {
            if (classTypeObj.getName().contains("Hot MOJO®"))
            {
                youTubePlayer.cueVideo("QWzlBfhE-qw");
            }
            else
            {
                youTubePlayer.cueVideo(classTypeObj.videoURL.substring(30, 41));
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
    {
    }
}