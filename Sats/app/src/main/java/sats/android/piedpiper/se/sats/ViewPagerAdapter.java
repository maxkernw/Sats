package sats.android.piedpiper.se.sats;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup.LayoutParams;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.Center;


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

        TableLayout views = new TableLayout(container.getContext());
        TextView text = new TextView(container.getContext());
        TextView text2 = new TextView(container.getContext());
        TextView text3 = new TextView(container.getContext());
        TextView text4 = new TextView(container.getContext());
        TextView text5 = new TextView(container.getContext());
        TextView text6 = new TextView(container.getContext());
        TextView text7 = new TextView(container.getContext());


        text.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("27/11-1/12");
        text7.setTextSize(10);
        GradientDrawable drawable = (GradientDrawable) container.getResources().getDrawable(R.drawable.shape);

        text.setBackground(drawable);
        text2.setBackground(drawable);
        text3.setBackground(drawable);
        text4.setBackground(drawable);
        text5.setBackground(drawable);
        text6.setBackground(drawable);


        views.addView(text);
        views.addView(text2);
        views.addView(text3);
        views.addView(text4);
        views.addView(text5);
        views.addView(text6);
        views.addView(text7);

        TableLayout layout = new TableLayout(container.getContext());


        TableLayout.LayoutParams tableRowParams=
                new TableLayout.LayoutParams
                        (TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);

        int leftMargin=65;
        int topMargin=10;
        int rightMargin=10;
        int bottomMargin=2;
        int bottomMarginDate = 25;
        text.setPadding(leftMargin, topMargin, 0, bottomMargin);
        text2.setPadding(leftMargin,topMargin,0,bottomMargin);
        text3.setPadding(leftMargin,topMargin,0,bottomMargin);
        text4.setPadding(leftMargin,topMargin,0,bottomMargin);
        text5.setPadding(leftMargin,topMargin,0,bottomMargin);
        text6.setPadding(leftMargin,topMargin,0,bottomMargin);
        text7.setPadding(25,topMargin,0,bottomMarginDate);


        layout.setBackgroundColor(container.getResources().getColor(R.color.white));
        //layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(tableRowParams);

        layout.addView(views);


        if(position % 2 == 0){
            layout.setBackgroundColor(container.getResources().getColor(R.color.calendar_background));
        }
        if(position > 1){

        }
        text7.setBackgroundColor(container.getResources().getColor(R.color.white));

        final int page = position;


        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((LinearLayout) object);
    }
    @Override
    public float getPageWidth(int position) {
        float nbPages = 5; // You could display partial pages using a float value
        return (1 / nbPages);
    }


}

