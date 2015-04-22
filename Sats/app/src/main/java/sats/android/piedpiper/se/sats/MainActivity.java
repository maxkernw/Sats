package sats.android.piedpiper.se.sats;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements GestureDetector.OnGestureListener
{
    ListView listView;
    GestureDetectorCompat gesture;
    Context context;
    static final String LOG = "Location";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_training_listview);
        getActionBar().setLogo(R.drawable.sats_logo);
        getActionBar().setDisplayShowCustomEnabled(true);


        listView = (ListView) findViewById(R.id.listan);

        listView.setAdapter(new TestAdapter(this, new String[]
                {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8"}));


        this.gesture = new GestureDetectorCompat(this, this);

    }

    public void expand(final View v)
    {

        final int initialHeight = v.getMeasuredHeight();
        final RelativeLayout r = (RelativeLayout) findViewById(R.id.minträning);

        final RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        p.addRule(RelativeLayout.ALIGN_TOP, R.id.graph);


        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(1000 * interpolatedTime);
                r.setLayoutParams(p);
            }

            @Override
            public boolean willChangeBounds()
            {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void collapse(final View v)
    {
        final int initialHeight = v.getMeasuredHeight();
        final RelativeLayout r = (RelativeLayout) findViewById(R.id.minträning);

        final RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        p.addRule(RelativeLayout.BELOW, R.id.graph);



        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {

                v.getLayoutParams().height = interpolatedTime == 2
                        ? RelativeLayout.LayoutParams.WRAP_CONTENT
                        : (int)(2000 * interpolatedTime);
                r.setLayoutParams(p);

            }

            @Override
            public boolean willChangeBounds()
            {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void ActivityCompleted(View v)
    {
        TextView avklarat = (TextView) findViewById(R.id.avklarat);
        CheckBox box = (CheckBox) v;
        if (box.isChecked())
        {
            avklarat.setText("Avklarat!");
        } else
        {
            avklarat.setText("Avklarat?");
        }
    }


    @Override
    public boolean onDown(MotionEvent motionEvent)
    {


        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent)
    {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent)
    {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2)
    {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent)
    {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v2)
    {
        RelativeLayout r = (RelativeLayout) findViewById(R.id.minträning);

        float dY = e1.getY() - e2.getY();
        if (dY < 0)
        {

            collapse(r);


        } else
        {
            r.getLayoutParams().height = (int)e1.getY();
           // expand(r);
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        this.gesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
