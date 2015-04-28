package sats.android.piedpiper.se.sats;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.TrainingActivity;

import java.util.ArrayList;
import java.util.Date;


import org.json.JSONException;

public final class MainActivity extends ActionBarActivity

{
    private static final String TAG = "SATSMainActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Toolbar will now take on default actionbar characteristics
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        try {
            CenterStorage.getCenters();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        ArrayList<Booking> user_activities = new ArrayList<>();
        ArrayList<Integer> classCat = new ArrayList<>();
        TrainingActivity aTrainingActivity = new TrainingActivity("1","2","3",4,"5","6","7",now,8,9,"10",11,classCat);
        classCat.add(10);

        user_activities.add(new Booking("CONFIRMED", aTrainingActivity,"Ullholmen", "2", 22));
        listView.setAdapter(new BookedClassAdapter(this, user_activities));
    }

    public void ActivityCompleted(View v)
    {
        TextView avklarat = (TextView) findViewById(R.id.avklarat);
        CheckBox box = (CheckBox) v;

        if (box.isChecked())
        {
            avklarat.setText("Avklarat!");
        } else
        {
            avklarat.setText("Avklarat?");
        }
    }
}