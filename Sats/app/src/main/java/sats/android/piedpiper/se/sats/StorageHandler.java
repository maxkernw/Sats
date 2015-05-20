package sats.android.piedpiper.se.sats;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;
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
    public static int[] weekPosition = new int[53];
    public static int week = 0;
    public static int[] activitesPerWeek = new int[53];

    public StorageHandler(android.app.Activity activity)
    {
        this.activity = activity;
        myActivities = new ArrayList<>();
        centerNamesMap = new HashMap<>();
        realm = Realm.getInstance(activity);
    }

    public void getAllActivities(final StickyListHeadersListView listView)
    {
        try
        {
            RealmQuery<Activity> query = realm.where(Activity.class);

            RealmResults<Activity> result = query.findAll();
            result.sort("date");
            getCenterNames();

            for (int i = 0; i < result.size(); i++)
            {
                myActivities.add(result.get(i));
            }
            for (int i = 0; i < myActivities.size(); i++) {
                DateTime joda = new DateTime(myActivities.get(i).getDate());

                if(joda.getWeekOfWeekyear() != week){
                    weekPosition[joda.getWeekOfWeekyear()] = i;
                    Log.e("DENNA VECKAN: ", String.valueOf(joda.getWeekOfWeekyear()));
                    week = joda.getWeekOfWeekyear();
                }
                if(joda.getWeekOfWeekyear() == week){
                    activitesPerWeek[joda.getWeekOfWeekyear()]++;
                }
            }
        }
        catch (RealmMigrationNeededException e)
        {
            APIResponseHandler responseHandler = new APIResponseHandler(activity);

            responseHandler.getAllActivities(listView);

            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
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
