package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.joda.time.DateTime;
import org.json.JSONException;
import java.util.Date;
import io.realm.Realm;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.storage.CenterStorage;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    ViewPager graph;
    ViewPagerAdapter graphAdapter;
    //private Date date = new Date();
    public static DateTime dateView = new DateTime().minusWeeks(26).minusYears(1).minusDays(3);

    public static int pos;
    private Date date = new Date();

    private static android.app.Activity activity;
    private static ImageView leftMarker = null;
    private static ImageView rightMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);
        final TextView statusText = (TextView) findViewById(R.id.activity_status);
        final Animation animRot = AnimationUtils.loadAnimation(this, R.anim.rotate);
        final ImageView im = (ImageView) findViewById(R.id.logo_refresh);
        graph = (ViewPager) findViewById(R.id.graph);
        date.setYear(115);

        activity = this;

        leftMarker = new ImageView(activity);
        leftMarker.setImageResource(R.drawable.back_to_now_right);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainRelative);
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
        rl.addView(leftMarker, lp);

        rightMarker = new ImageView(activity);
        rightMarker.setImageResource(R.drawable.back_to_now_left);
        RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.mainRelative);
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
        rl2.addView(rightMarker, lp2);


        APIResponseHandler responseHandler = new APIResponseHandler(this);
        final SlidingUpPanelLayout slide = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        graphAdapter = new ViewPagerAdapter();
        graph.setAdapter(graphAdapter);
        graph.setCurrentItem(18);

        try
        {
            CenterStorage.populateCenters();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        graph.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            final StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < 21 && position > 14)
                {
                    leftMarker.setVisibility(View.INVISIBLE);
                    rightMarker.setVisibility(View.INVISIBLE);
                }
                if (position < 16)
                {
                    leftMarker.setVisibility(View.VISIBLE);

                }
                if (position > 19)
                {
                    rightMarker.setVisibility(View.VISIBLE);
                }
                rightMarker.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        graph.setCurrentItem(18);
                    }
                });
                leftMarker.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        graph.setCurrentItem(18);
                    }
                });

            }

            @Override
            public void onPageSelected(int position)
            {
                pos = position;
                if (position <= 6)
                {
                    listView.smoothScrollToPosition(0);
                } else if (StorageHandler.weekPosition[position - 3] != 0)
                {
                    listView.smoothScrollToPosition(StorageHandler.weekPosition[position - 3]);
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


        int realmObjects;


        //System.out.println("RADERA rEALm?! :: --> " + Realm.deleteRealmFile(this));

        Realm realm = Realm.getInstance(this);
        realmObjects = realm.allObjects(Activity.class).size();
        realm.close();

        if(realmObjects == 0)
        {
            responseHandler = new APIResponseHandler(this);
            responseHandler.getAllActivities(listView);
        }
        else if(realmObjects > 0)
        {
            StorageHandler storageHandler = new StorageHandler(this);
            storageHandler.getAllActivities(listView);
        }


        im.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                im.startAnimation(animRot);
            }
        });

        listView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener() {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View header, int i, long l) {
                TextView txt = (TextView) findViewById(R.id.date_header);


                if (date.after(CustomAdapter.trainingList.get(i).getDate())) {

                    statusText.setText("TIDIGARE TRÄNING");

                } else {
                    statusText.setText("KOMMANDE TRÄNING");
                }
            }
        });
    }
}