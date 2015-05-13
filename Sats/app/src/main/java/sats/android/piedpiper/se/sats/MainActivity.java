package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import org.joda.time.DateTime;
import org.json.JSONException;


import sats.android.piedpiper.se.sats.storage.CenterStorage;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.Activity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    ViewPager graph;
    ViewPagerAdapter graphAdapter;
    //private Date date = new Date();
    private DateTime date = new DateTime(2015, 4, 18, 10, 10);
    private static android.app.Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final TextView txtStatus = (TextView) findViewById(R.id.activity_status);
        graph = (ViewPager) findViewById(R.id.graph);
        graphAdapter = new ViewPagerAdapter();
        graph.setAdapter(graphAdapter);
        try
        {
            CenterStorage.populateCenters();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        graph.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {


            }
        });

        activity = this;


        final StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

        IonRequester.getBooking(this, listView);
        final TextView statusText = (TextView) findViewById(R.id.activity_status);
        APIResponseHandler responseHandler = new APIResponseHandler(this);

        activity = this;
        //date = date.withYear(2013);


        responseHandler.getAllActivities(listView);


        final ImageView im = (ImageView) findViewById(R.id.logo_refresh);
        final Animation animRot = AnimationUtils.loadAnimation(this, R.anim.rotate);


        im.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                IonRequester.clear(activity, listView);
                im.startAnimation(animRot);
            }
        });

        listView.setOnStickyHeaderChangedListener(new StickyListHeadersListView.OnStickyHeaderChangedListener()
        {
            @Override
            public void onStickyHeaderChanged(StickyListHeadersListView stickyListHeadersListView, View header, int i, long l)
            {
                TextView txt = (TextView) findViewById(R.id.date_header);

                if(date.isAfter(CustomAdapter.trainingList.get(i).date)){
                    statusText.setText("TIDIGARE TRÄNING");
                }
                else{
                    statusText.setText("KOMMANDE TRÄNING");
                }
            }
        });
    }

}