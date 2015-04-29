package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import sats.android.piedpiper.se.sats.models.TrainingActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class SandrasMain extends ActionBarActivity
{
    private ArrayList<PreviousTraining> trainingList;

    private ArrayList<TrainingActivity> trainingActivityList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        Log.v("wat", "wat");
        populateArray();

        //List to populate
        StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

        //Populate list with data
        listView.setAdapter(new SandrasAdapter(this, trainingActivityList));
    }

    public void populateArray()
    {
        trainingActivityList = new ArrayList<>();

        TrainingActivity activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Yoga", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "COMPLETED", "GROUP", "GROUP");
        trainingActivityList.add(0, activity);

        activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Styrketräning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "GYM", "gym");
        trainingActivityList.add(1, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "running");
        trainingActivityList.add(2, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "COMPLETED", "OTHER", "cycle");
        trainingActivityList.add(3, activity);

        activity = new TrainingActivity("", "", "", 0, "", "Brittmarie Ek", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "SATS", "PLANNED", "OTHER", "other");
        trainingActivityList.add(4, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "other");
        trainingActivityList.add(5, activity);

        activity = new TrainingActivity("", "", "", 0, "", "", "Löpträning", new Date(), 0, 0, "", 0, new ArrayList<Integer>(), "OTHER", "COMPLETED", "OTHER", "other");
        trainingActivityList.add(6, activity);
       /* trainingList = new ArrayList<>();

        PreviousTraining activity = new PreviousTraining("Styrketräning","Måndag 4/5", false, 0, "GYM");
        trainingList.add(activity);
        activity = new PreviousTraining("Cyckling","Fredag 5/5",true, 1, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("Löpträning","Onsdag 26/4",true, 2, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("Cyckling","Bajs", false, 1, "GROUP");
        trainingList.add(activity);
        activity = new PreviousTraining("4Avklarat?","Bajs", true, 4, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("5Avklarat?","Bajs",false, 5, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("6Avklarat?","Bajs",false, 6, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("7Avklarat?","Bajs",false, 7, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("8Avklarat?","Bajs",false, 8, "OTHER");
        trainingList.add(activity);
        activity = new PreviousTraining("9Avklarat?","Bajs",false, 9, "OTHER");
        trainingList.add(activity);*/
    }
}