package sats.android.piedpiper.se.sats;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import org.joda.time.DateTime;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    ViewPager graph;
    ViewPagerAdapter graphAdapter;
    private DateTime date = new DateTime();
    private static android.app.Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final TextView txtStatus = (TextView) findViewById(R.id.tidigare);
        graph = (ViewPager) findViewById(R.id.graph);
        graphAdapter = new ViewPagerAdapter();
        graph.setAdapter(graphAdapter);
        graph.setPageMargin(1);


        graph.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                Log.e("Page", "Page selected: " + position);


            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                //Adjustment
                Log.e("Page" , "State selected: " + state);

            }
        });

        activity = this;
        date.withYear(2013);

        final StickyListHeadersListView listView = (StickyListHeadersListView) findViewById(R.id.listan);

        IonRequester.getBooking(this, listView);

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
                if (date.isAfter(CustomAdapter.trainingList.get(i).date))
                {
                    txtStatus.setText("TIDIGARE TRÄNING");
                } else
                {
                    txtStatus.setText("KOMMANDE TRÄNING");
                }
            }
        });
    }

}