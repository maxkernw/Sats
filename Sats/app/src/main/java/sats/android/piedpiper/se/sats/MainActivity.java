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

import org.joda.time.DateTime;
import org.json.JSONException;

import java.util.Date;

import io.realm.Realm;
import sats.android.piedpiper.se.sats.storage.CenterStorage;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    ViewPager graph;
    ViewPagerAdapter graphAdapter;
    //private Date date = new Date();
    public static DateTime dateView = new DateTime().minusWeeks(26).minusYears(1).minusDays(1);

    public static int pos;
    private Date date = new Date(2013, 4, 18, 10, 10);
    private static android.app.Activity activity;
    private static ImageView leftMarker = null;
    private static ImageView rightMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final TextView statusText = (TextView) findViewById(R.id.activity_status);
        graph = (ViewPager) findViewById(R.id.graph);

        activity = this;


        final StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

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
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
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
            }

            @Override
            public void onPageSelected(int position)
            {
                pos = position;
                Log.e("Pos", "Position: " + position);
                if (position <= 6)
                {
                    listView.smoothScrollToPosition(0);
                } else if (APIResponseHandler.weekPosition[position - 3] != 0)
                {
                    listView.smoothScrollToPosition(APIResponseHandler.weekPosition[position - 3]);
                }
/*
                if (position == 15)
                {
                    leftMarker.setVisibility(View.VISIBLE);
                }

                if (position == 19)
                {
                    rightMarker.setVisibility(View.VISIBLE);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        activity = this;

        System.out.println("RADERA rEALm?! :: --> " + Realm.deleteRealmFile(this));

        responseHandler.getAllActivities(listView);

        activity = this;

        final ImageView im = (ImageView) findViewById(R.id.logo_refresh);
        final Animation animRot = AnimationUtils.loadAnimation(this, R.anim.rotate);

        im.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                im.startAnimation(animRot);
            }
        });

        listView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener()
        {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View header, int i, long l)
            {
                TextView txt = (TextView) findViewById(R.id.date_header);

                if (date.after(CustomAdapter.trainingList.get(i).getDate()))
                {

                    statusText.setText("TIDIGARE TRÄNING");
                } else
                {
                    statusText.setText("KOMMANDE TRÄNING");
                }
            }
        });
    }
}