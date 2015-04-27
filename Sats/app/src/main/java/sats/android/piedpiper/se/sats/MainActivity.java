package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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


        listView = (ListView) findViewById(R.id.listan);

        listView.setAdapter(new TestAdapter(this, new String[]
                {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8"}));
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