package sats.android.piedpiper.se.sats;

import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import sats.android.piedpiper.se.sats.models.TrainingActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    private ArrayList<TrainingActivity> trainingActivityList;
    private Date date = new Date();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final TextView txtStatus = (TextView) findViewById(R.id.tidigare);

        populateArray();

        //List to populate
        StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

        //Populate list with data
        listView.setAdapter(new CustomAdapter(this, trainingActivityList));

        final ImageView im = (ImageView) findViewById(R.id.logo_refresh);
        final Animation animRot = AnimationUtils.loadAnimation(this, R.anim.rotate);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im.startAnimation(animRot);
            }
        });
        listView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener()
        {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View header, int i, long l)
            {
                TextView txt = (TextView) findViewById(R.id.date_header);

                if(date.after(CustomAdapter.trainingList.get(i).startTime)){
                    txtStatus.setText("TIDIGARE TRÄNING");
                }
                else{
                    txtStatus.setText("KOMMANDE TRÄNING");
                }

            }
        });

    }

    public void populateArray()
    {
        Date fuck = new Date();
        fuck.setYear(2015);
        fuck.setMonth(1);
        fuck.setDate(2);
        fuck.setHours(13);
        fuck.setMinutes(37);

        Date fuck2 = new Date();
        fuck2.setYear(2016);
        fuck2.setMonth(9);
        fuck2.setDate(29);
        fuck2.setHours(13);
        fuck2.setMinutes(37);

        trainingActivityList = new ArrayList<>();

        TrainingActivity activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Yoga", fuck, 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GROUP", "GROUP");

        trainingActivityList.add(0, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "Brittmarie Ek", "Styrketräning", new Date(), 7, 0, "", 0, new ArrayList<Integer>(), "SATS", "COMPLETED", "GYM", "gym");
        trainingActivityList.add(1, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "", "Löpträning1", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "PLANNED", "OTHER", "running");
        trainingActivityList.add(2, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "", "Cykling", fuck2, 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GYM", "gym");
        trainingActivityList.add(3, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "Brittmarie Ek", "Löpträning2", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GYM", "gym");
        trainingActivityList.add(4, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "", "Löpträningg3", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "PLANNED", "OTHER", "other");
        trainingActivityList.add(5, activity);

        activity = new TrainingActivity("Hornstull", "", "", 0, "", "", "Löpträning4", fuck2, 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "PLANNED", "OTHER", "other");
        trainingActivityList.add(6, activity);
    }

}