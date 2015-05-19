package sats.android.piedpiper.se.sats;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.joda.time.DateTime;


public class ViewPagerAdapter extends PagerAdapter
{
    private View mCurrentView;
    DateTime date = new DateTime(2013,12,20,0,0);
    DateTime date2 = new DateTime(2013,12,27,0,0);

    int NumberOfPages = 52;


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
        ViewPager pager = (ViewPager) container.findViewById(R.id.graph);
        TextView week = new TextView(container.getContext());
        week.setBackgroundColor(container.getResources().getColor(R.color.white));


        //Find the relativelayout and get height for week
        RelativeLayout parent = (RelativeLayout) container.findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, week.getId());

        week.setLayoutParams(params);
        LayoutParams heightParam = week.getLayoutParams();

        heightParam.height = 90;
        week.setText(date.getDayOfMonth() + "-" + date2.getDayOfMonth() + "/" + date2.getMonthOfYear());
        week.setGravity(Gravity.CENTER);

        views.addView(week);
        if(position == 2){
            MyView text = new MyView(container.getContext(), true, 6);

            views.addView(text);

        }else if(position == 5){

            ImageView top = new ImageView(container.getContext());
            top.setImageResource(R.drawable.now_marker);
            MyView text = new MyView(container.getContext(), false, 5);

            top.setScaleX(0.6f);
            top.setScaleY(0.6f);
            top.setPadding(65,-20,0,0);



            top.setScaleType(ImageView.ScaleType.CENTER);

            views.addView(top);
            views.addView(text);

        }
        else
        {
            MyView text = new MyView(container.getContext(), false, 5);
            text.bringToFront();
           views.addView(text);
        }






        RelativeLayout layout = new RelativeLayout(container.getContext());
        RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        views.setLayoutParams(x);
        layout.setLayoutParams(x);

        layout.setBackground(container.getResources().getDrawable(R.drawable.cal_dark, null));

        layout.addView(views);


        if(position % 2 == 0){
            layout.setBackground(container.getResources().getDrawable(R.drawable.callightright, null));
        }
        if(position > 1){

        }

        final int page = position;


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


}

