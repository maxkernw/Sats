package sats.android.piedpiper.se.sats;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;


public class MainActivity extends Activity
{
    ListView listView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        ActionBar ac = getActionBar();
        ac.setDisplayUseLogoEnabled(true);
        ac.setLogo(R.drawable.sats_logo);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.min_traning);
        listView = (ListView)findViewById(R.id.listan);

        listView.setAdapter(new TestAdapter(this, new String[]
                { "data1","data2","data3","data4","data5","data6","data7","data8" }));


    }

}
