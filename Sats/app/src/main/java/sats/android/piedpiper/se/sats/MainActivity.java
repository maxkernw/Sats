package sats.android.piedpiper.se.sats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sats.android.piedpiper.se.sats.models.Activity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class MainActivity extends ActionBarActivity
{
    private Date date = new Date();
    private static android.app.Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        final TextView txtStatus = (TextView) findViewById(R.id.tidigare);

        activity = this;
        date.setYear(113);

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
                if(date.after(CustomAdapter.trainingList.get(i).date)){
                    txtStatus.setText("TIDIGARE TRÄNING");
                }
                else{
                    txtStatus.setText("KOMMANDE TRÄNING");
                }
            }
        });
    }
}