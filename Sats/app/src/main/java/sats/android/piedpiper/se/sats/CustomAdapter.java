package sats.android.piedpiper.se.sats;

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

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sats.android.piedpiper.se.sats.holders.BookedActivityHolder;
import sats.android.piedpiper.se.sats.holders.OwnActivityHolder;
import sats.android.piedpiper.se.sats.holders.PreviousActivityHolder;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.ClassType;
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
    private DateTime myDate = new DateTime();
    private static final int NUMBER_OF_VIEWS_SERVED_BY_ADAPTER = 3;
    private static final int PREVIOUS = 0;
    private static final int BOOKED = 1;
    private static final int OWN = 2;

    public CustomAdapter(android.app.Activity activity, ArrayList<Activity> trainingList)
    {
        this.activity = activity;
        this.trainingList = trainingList;
        inflater = activity.getLayoutInflater();
        numberOfPositions = trainingList.size();
        myDate = myDate.withYear(2013);
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
        isPreviousActivity = (myTrainingActivityObj.status.equals("COMPLETED")) ||
                myTrainingActivityObj.date.isBefore(myDate);

        if (isPreviousActivity)
        {
            return PREVIOUS;
        } else
        {
            if (myTrainingActivityObj.type.equals("GROUP"))
            {
                return BOOKED;
            } else
            {
                return OWN;
            }
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Activity myTrainingActivityObj = (Activity) getItem(position);

        boolean isPreviousActivity;
        isPreviousActivity = (myTrainingActivityObj.status.equals("COMPLETED")) || (myTrainingActivityObj.date.isBefore(myDate));

        if (convertView == null)
        {
            if (isPreviousActivity)
            {
                convertView = inflatePreviousActivity(parent);
            } else
            {
                if (myTrainingActivityObj.type.equals("GROUP"))
                {
                    convertView = inflateBookedActivity(parent);
                } else
                {
                    convertView = inflateOwnActivity(parent);
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

        newView = inflater.inflate(R.layout.booked_activity_item, parent, false);

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

        PreviousActivityHolder holder;
        holder = new PreviousActivityHolder();

        newView = inflater.inflate(R.layout.previous_training_item, parent, false);

        holder.title = (TextView) newView.findViewById(R.id.title);
        holder.date = (TextView) newView.findViewById(R.id.date);
        holder.img = (ImageView) newView.findViewById(R.id.img);

        newView.setTag(holder);

        return newView;
    }

    private void setupOwnActivity(View view, int position)
    {
        OwnActivityHolder holder = (OwnActivityHolder) view.getTag();
        Activity ownActivityObj = (Activity) getItem(position);

        holder.title.setText(ownActivityObj.subType);
        holder.totalTime.setText(String.valueOf(ownActivityObj.durationInMinutes) + " min");
    }

    private void setupBookedActivity(View view, int position)
    {
        BookedActivityHolder holder = (BookedActivityHolder) view.getTag();
        final Activity bookedActivityObj = (Activity) getItem(position);
        Integer hrs = bookedActivityObj.date.getHourOfDay();
        Integer min = bookedActivityObj.date.getMinuteOfHour();
        String curHrs = String.format("%02d", hrs);
        String curMin = String.format("%02d", min);

        holder.bigClockHours.setText(curHrs);
        holder.bigClockMinutes.setText(curMin);
        holder.classTotalTime.setText(String.valueOf(bookedActivityObj.durationInMinutes) + " min");
        holder.title.setText(bookedActivityObj.subType);

        if(bookedActivityObj.booking != null)
        {
            holder.instructor.setText(bookedActivityObj.booking.aClass.instructorId);
            holder.participants.setText(String.valueOf(bookedActivityObj.booking.aClass.bookedPersonsCount));
            //set text center
            holder.center.setText(bookedActivityObj.booking.center);

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
                if(bookedActivityObj.booking != null)
                {
                    Intent moreInfo = new Intent(CustomAdapter.this.activity, MoreInfoActivity.class);

                    moreInfo.putExtra("classTypeId", bookedActivityObj.booking.aClass.classTypeId);

                    moreInfo.putExtra("instructor", bookedActivityObj.booking.aClass.instructorId);
                    moreInfo.putExtra("duration", bookedActivityObj.booking.aClass.durationInMinutes);
                    moreInfo.putExtra("numberAttending", bookedActivityObj.booking.aClass.bookedPersonsCount);
                    moreInfo.putExtra("maxAttending", bookedActivityObj.booking.aClass.maxPersonsCount);
                    moreInfo.putExtra("centerName", bookedActivityObj.booking.center);

                    CustomAdapter.this.activity.startActivity(moreInfo);
                }
                //moreInfo.putExtra("classname", bookedActivityObj.booking.aClass.name);


                /*moreInfo.putExtra("CenterName", IonRequester.centerName);
                moreInfo.putExtra("videoURL", klassen.videoURL);
                moreInfo.putExtra("description", klassen.description);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                moreInfo.putExtra("date", bookedActivityObj.date.toString());

                moreInfo.putExtra("kondition", klassen.getValue(0));
                moreInfo.putExtra("styrka", klassen.getValue(1));
                moreInfo.putExtra("rorlighet", klassen.getValue(2));
                moreInfo.putExtra("balans", klassen.getValue(3));
                moreInfo.putExtra("spenst", klassen.getValue(4));*/
            }
        });
    }

    private void setupPreviousActivity(View view, int position)
    {
        PreviousActivityHolder holder = (PreviousActivityHolder) view.getTag();
        Activity previousActivity = (Activity) getItem(position);
        mCalendar.setTime(trainingList.get(position).date.toDate());
        int month = mCalendar.get(Calendar.MONTH);
        String previousDateFormat = swedish_days[mCalendar.get(Calendar.DAY_OF_WEEK)-1] + " " + mCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (month+1);

        holder.title.setText(previousActivity.subType);
        holder.date.setText(previousDateFormat);
        setActivityImage(holder, previousActivity);

        CheckBox box = (CheckBox) view.findViewById(R.id.checkbox1);
        box.setChecked(previousActivity.status.equals("COMPLETED"));

        box.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    cb.setText("Avklarat!");
                } else {
                    cb.setText("Avklarat?");
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
        mCalendar.setTime(trainingList.get(position).date.toDate());
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