package se.piedpiper.android.sats.handlers;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import se.piedpiper.android.sats.activities.MainActivity;
import se.piedpiper.android.sats.adapters.CustomAdapter;
import se.piedpiper.android.sats.models.Activity;
import se.piedpiper.android.sats.models.Center;
import se.piedpiper.android.sats.models.CenterInfo;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class StorageHandler
{
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;
    public static HashMap<String, String> centerNamesMap;
    private static Realm realm;
    public static int week = 0;
    public static HashMap<Integer, Integer> weekPosition = new HashMap<>();
    public static HashMap<Integer, Integer> activitesPerWeek = new HashMap<>();

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
        week = new DateTime(myActivities.get(0).getDate()).getWeekOfWeekyear() - 1;

        for (int i = 0; i < myActivities.size(); i++)
        {
            DateTime joda = new DateTime(myActivities.get(i).getDate());

            if (joda.getWeekOfWeekyear() != week)
            {
                weekPosition.put(joda.getWeekOfWeekyear(), i);
                week = joda.getWeekOfWeekyear();
            }
            if (joda.getWeekOfWeekyear() == week)
            {
                if (activitesPerWeek.containsKey(joda.getWeekOfWeekyear()))
                {
                    int value = activitesPerWeek.get(joda.getWeekOfWeekyear());
                    value = value + 1;
                    activitesPerWeek.put(joda.getWeekOfWeekyear(), value);
                }
                else
                {
                    activitesPerWeek.put(joda.getWeekOfWeekyear(), 1);
                }
            }
        }
        listView.setAdapter(new CustomAdapter(activity, myActivities));
        realm.close();
    }

    private void getCenterNames()
    {
        for (Center center : realm.where(Center.class).findAll())
        {
            String centerName = center.getName();
            String url = center.getUrl();
            double lati = center.getLati();
            double longi = center.getLongi();
            MainActivity.markers.put(centerName, new CenterInfo(url, lati, longi));
        }
    }
}
