package sats.android.piedpiper.se.sats;

import sats.android.piedpiper.se.sats.models.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;


import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class CustomAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
    public static ArrayList<Activity> trainingList;
    private final android.app.Activity activity;
    private final LayoutInflater inflater;
    private final int numberOfPositions;
    private Calendar mCalendar = Calendar.getInstance();
    private final String[] swedish_days = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};
    private final String[] swedish_months = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};
    private Date date = new Date();
    private int NUMBER_OF_VIEWS_SERVED_BY_ADAPTER = 3;
    private int previous = 0;
    private int booked = 1;
    private int own = 2;

    public CustomAdapter(android.app.Activity activity, ArrayList<Activity> trainingList)
    {

        this.activity = activity;
        this.trainingList = trainingList;
        inflater = activity.getLayoutInflater();
        numberOfPositions = trainingList.size();
        date.setYear(113);
        for(Activity bom:trainingList){
            Log.e("vilken_typ", bom.subType + ", datum: " + bom.getDate());
        }

        Collections.sort(trainingList);
    }

    @Override
    public int getCount()
    {
        return numberOfPositions;
    }

    @Override
    public Object getItem(int position)
    {

        return trainingList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override
    public int getViewTypeCount()
    {
        return NUMBER_OF_VIEWS_SERVED_BY_ADAPTER;
    }

    @Override
    public int getItemViewType(int position)
    {
        Activity myTrainingActivityObj = (Activity) getItem(position);

        boolean isPreviousActivity;
        isPreviousActivity = (myTrainingActivityObj.status.equals("COMPLETED")) &&
                myTrainingActivityObj.date.before(date);

        if (isPreviousActivity)
        {                             //tidigare träning
            return previous;
        } else
        {
            if (myTrainingActivityObj.type.equals("GROUP"))
            { //SATSPass
                return booked;
            } else
            {                                          //egen träning
                return own;
            }
        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Activity myTrainingActivityObj = (Activity) getItem(position);

        boolean isPreviousActivity;
        isPreviousActivity = (//myTrainingActivityObj.status.equals("COMPLETED")) ||
                myTrainingActivityObj.date.before(date)); //TODO och/eller kolla om datum är innan dagens datum

        if (convertView == null)
        {
            if (isPreviousActivity)
            {                             //tidigare träning
                convertView = inflatePreviousActivity(parent);
                setupPreviousActivity(convertView, position);
            } else
            {
                if (myTrainingActivityObj.type.equals("GROUP"))
                { //SATSPass
                    convertView = inflateBookedActivity(parent);
                    setupBookedActivity(convertView, position);
                } else
                {                                          //egen träning
                    convertView = inflateOwnActivity(parent);
                    setupOwnActivity(convertView, position);
                }
            }
        }

        if(isPreviousActivity)
        {
            setupPreviousActivity(convertView, position);
        } else
        {
            if (myTrainingActivityObj.type.equals("GROUP"))
            {
                setupBookedActivity(convertView, position);
            } else
            {
                setupOwnActivity(convertView, position);
            }
        }

        return convertView;
    }

    private View inflateOwnActivity(ViewGroup parent)
    {
        View newView;

        OwnActivityHolder holder;
        holder = new OwnActivityHolder();

        newView = inflater.inflate(R.layout.own_activity_item, parent, false);

        //get
        holder.title = (TextView) newView.findViewById(R.id.own_activity_title);
        holder.totalTime = (TextView) newView.findViewById(R.id.own_activity_time);

        newView.setTag(holder);

        return newView;
    }

    private View inflateBookedActivity(ViewGroup parent)
    {
        View newView;

        BookedActivityHolder holder;
        holder = new BookedActivityHolder();

        newView = inflater.inflate(R.layout.booked_activity_item, parent,
                false);

        //get
        holder.bigClockHours = (TextView) newView.findViewById(R.id.hour);
        holder.bigClockMinutes = (TextView) newView.findViewById(R.id.minutes);
        holder.classTotalTime = (TextView) newView.findViewById(R.id.class_time);
        holder.title = (TextView) newView.findViewById(R.id.pass);
        holder.center = (TextView) newView.findViewById(R.id.center);
        holder.instructor = (TextView) newView.findViewById(R.id.instructor);
        holder.participants = (TextView) newView.findViewById(R.id.number_participants);

        newView.setTag(holder);

        return newView;
    }

    private View inflatePreviousActivity(ViewGroup parent)
    {
        View newView;

        PreviousActivityHolder previousActivityHolder;
        previousActivityHolder = new PreviousActivityHolder();

        newView = inflater.inflate(R.layout.previous_training_item, parent,
                false);
        //get
        previousActivityHolder.title = (TextView) newView.findViewById(R.id.title);
        previousActivityHolder.date = (TextView) newView.findViewById(R.id.date);
        previousActivityHolder.img = (ImageView) newView.findViewById(R.id.img);

        newView.setTag(previousActivityHolder);

        return newView;
    }

    private void setupOwnActivity(View view, int position)
    {
        OwnActivityHolder holder = (OwnActivityHolder) view.getTag();
        Activity ownActivityObj = (Activity) getItem(position);


        //set
        IonRequester.getName(activity, holder, ownActivityObj.subType);
        //holder.title.setText(ownActivityObj.subType);


        holder.totalTime.setText(String.valueOf(ownActivityObj.durationInMinutes) + " min");
    }

    private void setupBookedActivity(View view, int position)
    {
        BookedActivityHolder holder = (BookedActivityHolder) view.getTag();
        Activity bookedActivityObj = (Activity) getItem(position);

        //set

        //Integer hrs = Integer.parseInt(bookedActivityObj.date.getHours());
        Integer hrs = bookedActivityObj.date.getHours();
        //Integer min = Integer.parseInt(bookedActivityObj.date.getMinutes());
        Integer min = bookedActivityObj.date.getMinutes();
        String curHrs = String.format("%02d", hrs);
        String curMin = String.format("%02d", min);

        holder.bigClockHours.setText(curHrs);
        holder.bigClockMinutes.setText(curMin);
        holder.classTotalTime.setText(String.valueOf(bookedActivityObj.durationInMinutes) + " min");

        //
        holder.title.setText(bookedActivityObj.subType);
        IonRequester.getName(activity, holder, bookedActivityObj.subType);

        //

        Log.e("Subtype", "Subtype: " + bookedActivityObj.subType + " id: " + bookedActivityObj.id);
        if(bookedActivityObj.booking != null)
        {
            //holder.center.setText(bookedActivityObj.booking.center);
            IonRequester.getClass(activity, holder, bookedActivityObj.booking.center);

            //bookedActivityObj.booking.center
            holder.instructor.setText(bookedActivityObj.booking.aClass.instructorId);
            holder.participants.setText(String.valueOf(bookedActivityObj.booking.aClass.bookedPersonsCount));

            if (bookedActivityObj.booking.aClass.bookedPersonsCount == 0)
            {
                LinearLayout bookedPersons = (LinearLayout) view.findViewById(R.id.participants);
                bookedPersons.setVisibility(View.INVISIBLE);
            }
        }

        RelativeLayout lay = (RelativeLayout) view.findViewById(R.id.bottom_right_box);

        lay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent moreInfo = new Intent(CustomAdapter.this.activity, MoreInfoActivity.class);
                CustomAdapter.this.activity.startActivity(moreInfo);

            }
        });


    }

    private void setupPreviousActivity(View view, int position)
    {
        PreviousActivityHolder previousActivityHolder = (PreviousActivityHolder) view.getTag();
        Activity previousActivity = (Activity) getItem(position);
        mCalendar.setTime(trainingList.get(position).date);
        int month = mCalendar.get(Calendar.MONTH);
        String previousDateFormat = swedish_days[mCalendar.get(Calendar.DAY_OF_WEEK)-1] + " " + mCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (month+1);

        IonRequester.getName(activity, previousActivityHolder, previousActivity.subType);

        //previousActivityHolder.title.setText(previousActivity.subType);
        previousActivityHolder.date.setText(previousDateFormat);
        setActivityImage(previousActivityHolder, previousActivity);


        //checkbox
        CheckBox box = (CheckBox) view.findViewById(R.id.checkbox1);
        //sätt till checked/unchecked i början
        box.setChecked(previousActivity.status.equals("COMPLETED"));

        //Lyssnar på click fr. varje item i listan
        box.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked())
                {
                    cb.setText("Avklarat?");
                } else
                {
                    cb.setText("Avklarat!");
                }
            }
        });
    }

    private void setActivityImage(PreviousActivityHolder previousActivityHolder, Activity previousActivity)
    {
        /*  imageNumber
            0 - all_training = type(OTHER) & not subType(cycle, running, strength osv)
            1 - cycling = subtype(cycle)
            2 - running = subtype(walking/running)
            3 - strength = (GYM, gym)
            4 - group_training = type(GROUP but not if subType is cycling
         */

        int imageNo = 0;

        if (previousActivity.subType.equals("cycle"))
        {
            imageNo = 1;
        } else if (previousActivity.subType.equals("walking") || previousActivity.subType.equals("running"))
        {
            imageNo = 2;
        } else if (previousActivity.type.equals("GYM"))
        {
            imageNo = 3;
        } else if (previousActivity.type.equals("GROUP"))
        {
            imageNo = 4;
        } else
        {
            imageNo = 0;
        }

        switch (imageNo)
        {
            case 0:
                previousActivityHolder.img.setImageResource(R.drawable.all_training_icons);
                break;
            case 1:
                previousActivityHolder.img.setImageResource(R.drawable.cykling_icon);
                break;
            case 2:
                previousActivityHolder.img.setImageResource(R.drawable.running_icon);
                break;
            case 3:
                previousActivityHolder.img.setImageResource(R.drawable.strength_trainging_icon);
                break;
            case 4:
                previousActivityHolder.img.setImageResource(R.drawable.group_training_icon);
                break;
        }
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent)
    {
        HeaderViewHolder holder;
        if (convertView == null)
        {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.date_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.date_header);
            convertView.setTag(holder);
        } else
        {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        mCalendar.setTime(trainingList.get(position).date);
        String headerText = swedish_days[mCalendar.get(Calendar.DAY_OF_WEEK)-1] + " " + mCalendar.get(Calendar.DAY_OF_MONTH) + " " + swedish_months[mCalendar.get(Calendar.MONTH)];

        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int i)
    {
        return i;
    }

    private class HeaderViewHolder
    {
        TextView text;
    }

}


