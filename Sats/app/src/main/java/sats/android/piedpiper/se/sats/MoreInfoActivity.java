package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
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
            if (classType.getId().equals(classtypeId)){
                classTypeObj = classType;
            }
        }

        //TODO set&get video

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



        }else{
            Log.e("Info", "classtypeobj is empty");
        }
        //visa classType i layout

    }
}