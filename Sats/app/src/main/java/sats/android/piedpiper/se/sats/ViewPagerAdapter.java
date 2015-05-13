package sats.android.piedpiper.se.sats;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
    DateTime date = new DateTime();

    int NumberOfPages = 30;


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

        if(position == 2){
            MyView text = new MyView(container.getContext());

            text.setId(View.generateViewId());

            views.addView(text);

        }else if(position == NumberOfPages / 2){
            ImageView top = new ImageView(container.getContext());
            top.setImageResource(R.drawable.now_marker);
            MyView text = new MyView(container.getContext());
            top.setScaleX(0.6f);
            top.setScaleY(0.6f);
            top.setPadding(60,-40,2,2);
            top.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            text.setId(View.generateViewId());

            views.addView(top);
            pager.addView(text);

        }
        else
        {
            MyView text = new MyView(container.getContext());
            text.bringToFront();
           views.addView(text);
        }






        RelativeLayout layout = new RelativeLayout(container.getContext());
        RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        views.setLayoutParams(x);
        layout.setLayoutParams(x);

        layout.setBackground(container.getResources().getDrawable(R.drawable.caldark, null));

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

