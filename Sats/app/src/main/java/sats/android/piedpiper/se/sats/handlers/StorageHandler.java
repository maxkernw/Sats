package sats.android.piedpiper.se.sats.handlers;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import sats.android.piedpiper.se.sats.adapters.CustomAdapter;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Center;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class StorageHandler
{
    private static final String TAG = "StorageHandler";
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;
    private HashMap<String, String> centerNamesMap;
    private static Realm realm;
    //public static int[] weekPosition = new int[53];
    public static int week = 0;
    //public static int[] activitesPerWeek = new int[53];
    public static HashMap<Integer,Integer> weekPosition = new HashMap<>();
    public static HashMap<Integer,Integer> activitesPerWeek = new HashMap<>();

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
        getCenterNames();

        for (int i = 0; i < result.size(); i++)
        {
            myActivities.add(result.get(i));
        }
        week = new DateTime(myActivities.get(0).getDate()).getWeekOfWeekyear()-1;
        for (int i = 0; i < myActivities.size(); i++) {
            DateTime joda = new DateTime(myActivities.get(i).getDate());

            if(joda.getWeekOfWeekyear() != week){
                weekPosition.put(joda.getWeekOfWeekyear(), i);
                week = joda.getWeekOfWeekyear();
            }
            if(joda.getWeekOfWeekyear() == week){
                if(activitesPerWeek.containsKey(joda.getWeekOfWeekyear() +1)){
                    int value = activitesPerWeek.get(joda.getWeekOfWeekyear() +1);
                    value = value+1;
                    activitesPerWeek.put(joda.getWeekOfWeekyear() + 1, value);
                }else{
                    activitesPerWeek.put(joda.getWeekOfWeekyear() + 1,1);
                }
            }
        }
        //Log.e("Storage", String.valueOf(myActivities));
        listView.setAdapter(new CustomAdapter(activity, myActivities));
    }

    private void getCenterNames()
    {
        for(Booking booking : realm.where(Booking.class).findAll())
        {
            for(Center center : realm.where(Center.class).findAll())
            {
                if(String.valueOf(center.getId()) == booking.getCenter())
                {
                    booking.setCenter(center.getName());
                    Log.i(TAG, booking.getCenter());
                }
            }
        }
    }
}
