package sats.android.piedpiper.se.sats.adapters;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.Realm;
import sats.android.piedpiper.se.sats.handlers.APIResponseHandler;
import sats.android.piedpiper.se.sats.activities.MainActivity;
import sats.android.piedpiper.se.sats.MyView;
import sats.android.piedpiper.se.sats.R;
import sats.android.piedpiper.se.sats.handlers.StorageHandler;
import sats.android.piedpiper.se.sats.models.Activity;

public class ViewPagerAdapter extends PagerAdapter
{
    public ViewPagerAdapter(android.app.Activity activity) {
        this.activity = activity;
    }

    int NumberOfPages = 52;
    android.app.Activity activity;

    @Override
    public int getCount()
    {
        return NumberOfPages;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {

        RelativeLayout views = new RelativeLayout(container.getContext());
        //ViewPager pager = (ViewPager) container.findViewById(R.id.graph);
        TextView week = new TextView(container.getContext());
        week.setBackgroundColor(container.getResources().getColor(R.color.white));

        //Find the relativelayout and get height for week
        //RelativeLayout parent = (RelativeLayout) container.findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, week.getId());

        week.setLayoutParams(params);
        LayoutParams heightParam = week.getLayoutParams();

        heightParam.height = 90;
        week.setText(MainActivity.dateView.plusWeeks(position).getDayOfMonth() + "-" + MainActivity.dateView.plusWeeks(position + 1).getDayOfMonth() + "/" + MainActivity.dateView.plusWeeks(position + 1).getMonthOfYear());

        int thisWeek = MainActivity.dateView.plusWeeks(position + 1).getWeekOfWeekyear();

        week.setGravity(Gravity.CENTER);
        //Log.e("pos", "Position in viewpager: " + position);
        views.addView(week);

        MyView text;
        int one,two,three;
        if(APIResponseHandler.activitesPerWeek.size() == 0)
        {
            Log.e("viewPagerAdapter", "storage: " + String.valueOf(StorageHandler.activitesPerWeek.size()));
            Log.e("viewPagerAdapter", "apiresp: " + String.valueOf(APIResponseHandler.activitesPerWeek.size()));
            if(StorageHandler.activitesPerWeek.containsKey(thisWeek)){
                one = StorageHandler.activitesPerWeek.get(thisWeek);
            }else{
                one = 0;
            }

            if(StorageHandler.activitesPerWeek.containsKey(thisWeek-1)){
                two = StorageHandler.activitesPerWeek.get(thisWeek-1);
            }else{
                two = 0;
            }

            if(StorageHandler.activitesPerWeek.containsKey(thisWeek+1)){
                three = StorageHandler.activitesPerWeek.get(thisWeek+1);
            }else{
                three = 0;
            }
        }
        else
        {
            Log.e("viewPagerAdapter", "storage: " + String.valueOf(StorageHandler.activitesPerWeek.size()));
            Log.e("viewPagerAdapter", "apiresp: " + String.valueOf(APIResponseHandler.activitesPerWeek.size()));
            if(APIResponseHandler.activitesPerWeek.containsKey(thisWeek)){
                one = APIResponseHandler.activitesPerWeek.get(thisWeek);
            }else{
                one = 0;
            }

            if(APIResponseHandler.activitesPerWeek.containsKey(thisWeek-1)){
                two = APIResponseHandler.activitesPerWeek.get(thisWeek-1);
            }else{
                two = 0;
            }

            if(APIResponseHandler.activitesPerWeek.containsKey(thisWeek+1)){
                three = APIResponseHandler.activitesPerWeek.get(thisWeek+1);
            }else{
                three = 0;
            }
        }


        if(position == 14)
        {
            text = new MyView(container.getContext(), false, one, two, three);

            ImageView top = new ImageView(container.getContext());
            top.setImageResource(R.drawable.now_marker);
            top.setScaleX(0.6f);
            top.setScaleY(0.6f);
            top.setX(52);
            top.setY(-20);

            top.setScaleType(ImageView.ScaleType.CENTER);

            views.addView(top);
        }
        else if(position < 52)
        {
            if(position > 14)
            {
                text = new MyView(container.getContext(), false, one, two, three);
            }
            else
            {
                if(position == 13)
                {
                    text = new MyView(container.getContext(), true, one, two, -1);
                }
                else
                {
                    text = new MyView(container.getContext(), true, one, two, three);
                }
            }

        }
        else
        {
            text = new MyView(container.getContext(), true, one, 0, three);
        }

        views.addView(text);
        text.bringToFront();

        RelativeLayout layout = new RelativeLayout(container.getContext());
        RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        views.setLayoutParams(x);
        layout.setLayoutParams(x);

        layout.setBackground(container.getResources().getDrawable(R.drawable.cal_dark, null));

        layout.addView(views);

        if(position % 2 == 0){
            layout.setBackground(container.getResources().getDrawable(R.drawable.callightright, null));
        }

        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        float nbPages = 5; // You could display partial pages using a float value
        return (1 / nbPages);
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
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
