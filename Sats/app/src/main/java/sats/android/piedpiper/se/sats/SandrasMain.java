package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class SandrasMain extends ActionBarActivity
{
    private ArrayList<PreviousTraining> trainingList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateArray();

        //List to populate
        ListView listView = (ListView) findViewById(R.id.list);

        //Populate list with data
        listView.setAdapter(new SandrasAdapter(this, trainingList));
    }

    public void populateArray()
    {
        trainingList = new ArrayList<>();

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
        trainingList.add(activity);
    }
}