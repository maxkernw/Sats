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

        /*ImageView line1 = new ImageView(container.getContext());

        line1.setImageResource(R.drawable.thin_horisontal_divider);

        line1.setScaleType(ImageView.ScaleType.FIT_XY);

        RelativeLayout.LayoutParams parameters = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        parameters.width = LayoutParams.MATCH_PARENT;
        parameters.topMargin = 1;
        parameters.height = 1;

        line1.setLayoutParams(parameters);

        pager.addView(line1);*/

        //GradientDrawable drawable = (GradientDrawable) container.getResources().getDrawable(R.drawable.shape);

        int leftMargin=65;
        int topMargin=10;
        int rightMargin=10;
        int bottomMargin=2;
        int bottomMarginDate = 25;
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
        TextView date = new TextView(container.getContext());




        //GradientDrawable drawable = (GradientDrawable) container.getResources().getDrawable(R.drawable.shape);

//        text2.setBackground(drawable);
//        text3.setBackground(drawable);
//        text4.setBackground(drawable);
//        text5.setBackground(drawable);
//        text6.setBackground(drawable);


        RelativeLayout layout = new RelativeLayout(container.getContext());
        RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //x.addRule(RelativeLayout.INVISIBLE, R.id.highline);
        views.setLayoutParams(x);
        layout.setLayoutParams(x);
//        TableLayout.LayoutParams tableRowParams=
//                new TableLayout.LayoutParams(20,20);


        /*int leftMargin=65;
        int topMargin=10;
        int rightMargin=10;
        int bottomMargin=2;
        int bottomMarginDate = 25;*/
        //text.setPadding(leftMargin, topMargin, 0, bottomMargin);



        //layout.setBackgroundColor(container.getResources().getColor(R.color.primary_calendar));
        layout.setBackground(container.getResources().getDrawable(R.drawable.caldark));

        layout.addView(views);


        if(position % 2 == 0){
            layout.setBackground(container.getResources().getDrawable(R.drawable.callightright));
            //layout.setBackgroundResource(R.drawable.shape);
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

