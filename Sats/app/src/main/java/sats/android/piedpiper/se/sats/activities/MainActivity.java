package sats.android.piedpiper.se.sats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import io.realm.Realm;
import io.realm.RealmResults;
import sats.android.piedpiper.se.sats.handlers.APIResponseHandler;
import sats.android.piedpiper.se.sats.R;
import sats.android.piedpiper.se.sats.handlers.StorageHandler;
import sats.android.piedpiper.se.sats.adapters.CustomAdapter;
import sats.android.piedpiper.se.sats.adapters.ViewPagerAdapter;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.CenterInfo;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    ViewPager graph;
    public static ViewPagerAdapter graphAdapter;
    //public static DateTime startTime = new DateTime().minusYears(1).minusWeeks(21).minusDays(2);//minusDays(3);
    //public static DateTime today = new DateTime().minusWeeks(6).minusDays(2);//minusDays(3);

    public static DateTime startTime = new DateTime().withDate(2013,12,29);
    public static DateTime today = new DateTime().withDate(2015, 4, 7);
    
    public StickyListHeadersListView listView;
    public static int pos;
    //private Date date = startTime.toDate();
    private Date todaydate = today.toDate();
    private static android.app.Activity activity;
    private static ImageView leftMarker = null;
    private static ImageView rightMarker = null;
    public static HashMap<String, CenterInfo> markers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        listView = (StickyListHeadersListView) findViewById(R.id.listan);
        final TextView statusText = (TextView) findViewById(R.id.activity_status);
        final Animation animRot = AnimationUtils.loadAnimation(this, R.anim.rotate);
        final ImageView im = (ImageView) findViewById(R.id.logo_refresh);
        final ImageView findCenter = (ImageView) findViewById(R.id.map_marker);
        graph = (ViewPager) findViewById(R.id.graph);
        activity = this;

        ArrayList<Activity> activitiesList = new ArrayList<>();
        final Realm realm = Realm.getInstance(this);
        RealmResults<Activity> realmActivities = realm.allObjects(Activity.class);

        if(realmActivities.size() == 0){ //om realm inte har data men mst kolla om uppdaterat?
            APIResponseHandler responseHandler = new APIResponseHandler(this);
            responseHandler.getAllActivities(listView); //sparar i realm
        }
        else
        {
            StorageHandler sh = new StorageHandler(activity);
            sh.getAllActivities(listView);

            for (Activity activity : realmActivities)
            {
                activitiesList.add(activity);
            }
            int x = activitiesList.size();
            int y;
            for (int m = x; m >= 0; m--) {
                for (int i = 0; i < x - 1; i++) {
                    y = i + 1;
                    if (activitiesList.get(i).getDate().getTime() > activitiesList.get(y).getDate().getTime()) {
                        Activity temp;
                        temp = activitiesList.get(i);
                        activitiesList.set(i, activitiesList.get(y));
                        activitiesList.set(y, temp);
                    }
                }
            }
            realm.close();
            listView.setAdapter(new CustomAdapter(activity, activitiesList));
        }

        leftMarker = new ImageView(activity);
        leftMarker.setImageResource(R.drawable.back_to_now_right);
        RelativeLayout rlLeft = (RelativeLayout) findViewById(R.id.mainRelative);
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, R.id.toolbar);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        leftMarker.setScaleX(0.6f);
        leftMarker.setScaleY(0.6f);
        leftMarker.setX(26);
        leftMarker.setY(-19);
        leftMarker.setVisibility(View.INVISIBLE);
        rlLeft.addView(leftMarker, lp);

        rightMarker = new ImageView(activity);
        rightMarker.setImageResource(R.drawable.back_to_now_left);
        RelativeLayout rlRight = (RelativeLayout) findViewById(R.id.mainRelative);
        final RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.BELOW, R.id.toolbar);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_START);
        rightMarker.setScaleX(0.6f);
        rightMarker.setScaleY(0.6f);
        rightMarker.setX(-20);
        rightMarker.setY(-19);
        rightMarker.setVisibility(View.INVISIBLE);
        rlRight.addView(rightMarker, lp2);

        final SlidingUpPanelLayout slide = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        graphAdapter = new ViewPagerAdapter(activity);
        graph.setAdapter(graphAdapter);
        graph.setCurrentItem(12);

        graph.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < 15  && position > 9)
                {
                    leftMarker.setVisibility(View.INVISIBLE);
                    rightMarker.setVisibility(View.INVISIBLE);
                }
                if (position < 9)
                {
                    leftMarker.setVisibility(View.VISIBLE);
                }
                if (position >= 15)
                {
                    rightMarker.setVisibility(View.VISIBLE);
                }
                rightMarker.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        graph.setCurrentItem(12);
                    }
                });
                leftMarker.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        graph.setCurrentItem(12);
                    }
                });
            }

            @Override
            public void onPageSelected(int position)
            {
                int thePosition = position+3;

                if(APIResponseHandler.activitesPerWeek.size() == 0)
                {
                    if(StorageHandler.weekPosition.containsKey(thePosition)){
                        listView.smoothScrollToPosition(StorageHandler.weekPosition.get(thePosition));
                    }
                }
                else
                {
                    if(APIResponseHandler.weekPosition.containsKey(thePosition))
                    {
                        listView.smoothScrollToPosition(APIResponseHandler.weekPosition.get(thePosition));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        slide.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener()
        {
            @Override
            public void onPanelSlide(View view, float v)
            {

            }

            @Override
            public void onPanelCollapsed(View view)
            {

            }

            @Override
            public void onPanelExpanded(View view)
            {
                rightMarker.setVisibility(View.INVISIBLE);
                leftMarker.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPanelAnchored(View view)
            {

            }

            @Override
            public void onPanelHidden(View view)
            {

            }
        });

        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                im.startAnimation(animRot);
                APIResponseHandler responseHandler = new APIResponseHandler(activity);

                responseHandler.clear(listView);

            }
        });

        listView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener()
        {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View header, int i, long l)
            {
                TextView txt = (TextView) findViewById(R.id.date_header);

                if (todaydate.after(CustomAdapter.trainingList.get(i).getDate()))
                {
                    statusText.setText("TIDIGARE TRÄNING");

                } else
                {
                    statusText.setText("KOMMANDE TRÄNING");
                }
            }
        });

        findCenter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent moreInfo = new Intent(MainActivity.this.activity, CenterMapsActivity.class);
                MainActivity.this.activity.startActivity(moreInfo, null);
            }
        });
    }

    public boolean realmExists(android.app.Activity activity)
    {
        int realmObjects = 0;
        Realm realm = Realm.getInstance(activity);
        realmObjects = realm.allObjects(Activity.class).size();
        realm.close();

        if(realmObjects > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}