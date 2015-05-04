package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import sats.android.piedpiper.se.sats.models.TrainingActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SandrasMain extends ActionBarActivity
{
    private ArrayList<TrainingActivity> trainingActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);

        populateArray();

        //List to populate
        StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

        //Populate list with data
        listView.setAdapter(new SandrasAdapter(this, trainingActivityList));

    }

    public void populateArray()
    {
        trainingActivityList = new ArrayList<>();

        TrainingActivity activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Yoga", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GROUP", "GROUP");
        trainingActivityList.add(0, activity);

        activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Styrketräning", new Date(), 7, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GROUP", "gym");
        trainingActivityList.add(1, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "running");
        trainingActivityList.add(2, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Cykling", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "COMPLETED", "OTHER", "cycle");
        trainingActivityList.add(3, activity);

        activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "OTHER", "other");
        trainingActivityList.add(4, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträningg", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "other");
        trainingActivityList.add(5, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "other");
        trainingActivityList.add(6, activity);
    }
}