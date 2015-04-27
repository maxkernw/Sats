package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MainActivity extends Activity
{
    private static final String TAG = "SATSMainActivity";
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
//        getActionBar().setLogo(R.drawable.sats_logo);
//        getActionBar().setDisplayShowCustomEnabled(true);
        try {
            CenterStorage.getCenters();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listan);

        listView.setAdapter(new TestAdapter(this, new String[]
                {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8"}));
    }

    public void ActivityCompleted(View v)
    {
        TextView avklarat = (TextView) findViewById(R.id.avklarat);
        CheckBox box = (CheckBox) v;

        if (box.isChecked()) {
            avklarat.setText("Avklarat!");
        } else {
            avklarat.setText("Avklarat?");
        }
    }
}