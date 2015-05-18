package sats.android.piedpiper.se.sats;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import sats.android.piedpiper.se.sats.models.Activity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class StorageHandler
{
    private static final String TAG = "StorageHandler";
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;
    private HashMap<String, String> centerNamesMap;
    private static Realm realm;

    public StorageHandler(android.app.Activity activity)
    {
        this.activity = activity;
        myActivities = new ArrayList<>();
        centerNamesMap = new HashMap<>();
        realm = Realm.getInstance(activity);
    }

    public void getAllActivities(final StickyListHeadersListView listView)
    {
        RealmQuery<Activity> query = realm.where(Activity.class);
        RealmResults<Activity> result = query.findAll();
        result.sort("date");

        for (int i = 0; i < result.size(); i++)
        {
            myActivities.add(result.get(i));
        }

        listView.setAdapter(new CustomAdapter(activity, myActivities));
    }
}
