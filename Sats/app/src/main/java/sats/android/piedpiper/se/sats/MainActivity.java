package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
<<<<<<< HEAD
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.koushikdutta.ion.Ion;

public class MainActivity extends Activity
{
    private static final String TAG = "SATSMainActivity";

    ListView listView;
    static final String LOG = "Location";
=======

public final class MainActivity extends Activity
{
    private static final String TAG = "SATSMainActivity";
    private ListView listView;
>>>>>>> 55f550dc7d8e9df485211293e3b7a02b847c68f3

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_activity);
        getActionBar().setLogo(R.drawable.sats_logo);
        getActionBar().setDisplayShowCustomEnabled(true);

        listView = (ListView) findViewById(R.id.list_item1);

        listView.setAdapter(new AnotherAdapter(this, new String[]
                {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8"}));
    }

    /*public void ActivityCompleted(View v)
    {
       /* TextView avklarat = (TextView) findViewById(R.id.avklarat);
        CheckBox box = (CheckBox) v;

        if (box.isChecked()) {
            avklarat.setText("Avklarat!");
        } else {
            avklarat.setText("Avklarat?");
        }
    }*/
}