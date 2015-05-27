package sats.android.piedpiper.se.sats.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.joda.time.DateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmResults;
import sats.android.piedpiper.se.sats.activities.MainActivity;
import sats.android.piedpiper.se.sats.activities.MoreInfoActivity;
import sats.android.piedpiper.se.sats.R;
import sats.android.piedpiper.se.sats.adapters.holders.ActivityHolder;
import sats.android.piedpiper.se.sats.adapters.holders.BookedActivityHolder;
import sats.android.piedpiper.se.sats.adapters.holders.OwnActivityHolder;
import sats.android.piedpiper.se.sats.adapters.holders.PreviousActivityHolder;
import sats.android.piedpiper.se.sats.handlers.APIResponseHandler;
import sats.android.piedpiper.se.sats.handlers.StorageHandler;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Center;
import sats.android.piedpiper.se.sats.models.BookingClass;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class CustomAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
    private final android.app.Activity activity;
    private final LayoutInflater inflater;
    public static ArrayList<Activity> trainingList;
    private Calendar mCalendar = Calendar.getInstance();
    private final String[] swedish_days = {"Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag", "Söndag"};
    private final String[] swedish_months = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};
    private Date dateToday;
    private static final int NUMBER_OF_VIEWS_SERVED_BY_ADAPTER = 3;
    private static final int PREVIOUS = 0;
    private static final int BOOKED = 1;
    private static final int OWN = 2;
    private int[] weeks;
    private String[] mWeeks;

    public CustomAdapter(android.app.Activity activity, ArrayList<Activity> trainingList)
    {
        this.activity = activity;
        this.trainingList = trainingList;
        inflater = activity.getLayoutInflater();
        dateToday = MainActivity.today.toDate();
        weeks = getWeeks();
        mWeeks = getHeaderText();
    }

    public int[] getWeeks()
    {
        if (!trainingList.isEmpty())
        {
            DateTime firstActivityDate = new DateTime(trainingList.get(0).getDate());
            int week = firstActivityDate.getWeekOfWeekyear();
            ArrayList<Integer> sectionIndices = new ArrayList<>();

            for (int i = 0; i < trainingList.size(); i++)
            {
                if (trainingList.get(i).getDate().before(dateToday))
                {
                    DateTime activityDate = new DateTime(trainingList.get(i).getDate());
                    int activityWeek = activityDate.getWeekOfWeekyear();
                    if (activityWeek != week)
                    {
                        week = activityWeek;
                        sectionIndices.add(i);
                    }
                } else
                {
                    sectionIndices.add(i);
                }
            }
            int[] sections = new int[sectionIndices.size()];
            for (int i = 0; i < sectionIndices.size(); i++)
            {
                sections[i] = sectionIndices.get(i);
            }
            return sections;
        }
        return new int[]{1};
    }

    public String[] getHeaderText()
    {
        String[] weekdate = new String[trainingList.size()];

        for (int i = 0; i < trainingList.size(); i++)
        {
            Date activityDate = trainingList.get(i).getDate();
            mCalendar.setTime(activityDate);
            weekdate[i] = String.valueOf(mCalendar.get(Calendar.WEEK_OF_YEAR));
        }
        return weekdate;
    }

    @Override
    public int getCount()
    {
        if (trainingList == null || trainingList.isEmpty())
        {
            return 0;
        }
        return trainingList.size();
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
        isPreviousActivity = (myTrainingActivityObj.getStatus().equals("COMPLETED")) ||
                myTrainingActivityObj.getDate().before(dateToday);

        if (isPreviousActivity)
        {
            return PREVIOUS;
        } else
        {
            if (myTrainingActivityObj.getType().equals("GROUP"))
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
        isPreviousActivity = (myTrainingActivityObj.getStatus().equals("COMPLETED")) || (myTrainingActivityObj.getDate().before(dateToday));

        if (convertView == null)
        {
            if (isPreviousActivity)
            {
                convertView = inflatePreviousActivity(parent);
            } else
            {
                if (myTrainingActivityObj.getType().equals("GROUP"))
                {
                    convertView = inflateBookedActivity(parent);
                } else
                {
                    convertView = inflateOwnActivity(parent);
                }
            }
        }

        if (isPreviousActivity)
        {
            setupPreviousActivity(convertView, position);
        } else
        {
            if (myTrainingActivityObj.getType().equals("GROUP"))
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

        final OwnActivityHolder holder;
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

        final BookedActivityHolder holder;
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

        final PreviousActivityHolder holder;
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
        final OwnActivityHolder holder = (OwnActivityHolder) view.getTag();
        Activity ownActivityObj = (Activity) getItem(position);

        holder.title.setText(ownActivityObj.getSubType());
        holder.totalTime.setText(String.valueOf(ownActivityObj.getDurationInMinutes()) + " min");
    }

    private void setupBookedActivity(View view, int position)
    {
        final BookedActivityHolder holder = (BookedActivityHolder) view.getTag();

        final Activity bookedActivityObj = (Activity) getItem(position);
        Integer hrs = bookedActivityObj.getDate().getHours();
        Integer min = bookedActivityObj.getDate().getMinutes();
        String curHrs = String.format("%02d", hrs);
        String curMin = String.format("%02d", min);
        String centerName = "ej hämtat";

        holder.bigClockHours.setText(curHrs);
        holder.bigClockMinutes.setText(curMin);
        holder.classTotalTime.setText(String.valueOf(bookedActivityObj.getDurationInMinutes()) + " min");
        holder.title.setText(bookedActivityObj.getSubType());


        if (!bookedActivityObj.isValid())
        {
            if (bookedActivityObj.getBooking() != null)
            {
                holder.instructor.setText(bookedActivityObj.getBooking().getaBookingClass().getInstructorId());
                holder.participants.setText(String.valueOf(bookedActivityObj.getBooking().getPositionInQueue()));
                holder.center.setText(bookedActivityObj.getBooking().getCenter());
                if (bookedActivityObj.getBooking().getPositionInQueue() == 0)
                {
                    RelativeLayout bookedPersons = (RelativeLayout) view.findViewById(R.id.participants);
                    bookedPersons.setVisibility(View.INVISIBLE);
                }
            }
        }
        else
        {
            Realm realm = Realm.getInstance(activity);
            final Booking realmBooking = bookedActivityObj.getBookings().first();
            if (realmBooking != null)
            {
                BookingClass realmClass = realmBooking.getBookingClasses().first();

                int realmCenterId = Integer.valueOf(realmBooking.getCenter());
                RealmResults<Center> realmCenters = realm.where(Center.class).findAll();
                for (Center center : realmCenters) {
                    if(center.getId() == realmCenterId){
                        centerName = center.getName();
                    }
                }

                holder.instructor.setText(realmClass.getInstructorId());
                holder.participants.setText(String.valueOf(realmBooking.getPositionInQueue()));
                holder.center.setText(centerName);

                if (realmBooking.getPositionInQueue() == 0)
                {

                    RelativeLayout bookedPersons = (RelativeLayout) view.findViewById(R.id.participants);
                    bookedPersons.setVisibility(View.INVISIBLE);
                }
            }
        }

        RelativeLayout lay = (RelativeLayout) view.findViewById(R.id.bottom_right_box_booked);

        final String finalCenterName = centerName;
        lay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent moreInfo = new Intent(CustomAdapter.this.activity, MoreInfoActivity.class);

                if (!bookedActivityObj.isValid())
                {
                    Booking bookingObj = bookedActivityObj.getBooking();

                    if (bookingObj != null)
                    {
                        if (bookingObj.getaBookingClass() != null)
                        {
                            BookingClass classObj = bookingObj.getaBookingClass();

                            moreInfo.putExtra("classTypeId", classObj.getClassTypeId());
                            moreInfo.putExtra("instructor", classObj.getInstructorId());
                            moreInfo.putExtra("centerName", holder.center.getText());
//                            if(APIResponseHandler.activitesPerWeek.size() != 0){
//                                moreInfo.putExtra("centerName", APIResponseHandler.centerNamesMap.get(classObj.getCenterId()));
//                            }else{
//                                moreInfo.putExtra("centerName", StorageHandler.centerNamesMap.get(classObj.getCenterId()));
//                            }


                            moreInfo.putExtra("duration", String.valueOf(classObj.getDurationInMinutes()));
                            moreInfo.putExtra("bookedCount", String.valueOf(classObj.getBookedPersonsCount()));
                            moreInfo.putExtra("maxAttending", String.valueOf(classObj.getMaxPersonsCount()));
                            moreInfo.putExtra("posInQueue", String.valueOf(bookingObj.getPositionInQueue()));
                            moreInfo.putExtra("startTime", String.valueOf(classObj.getStartTime()));

                        }

                        CustomAdapter.this.activity.startActivity(moreInfo, null);
                    }
                }
                else
                {
                    Booking realmBooking = bookedActivityObj.getBookings().first();

                    if (realmBooking != null)
                    {
                        if (realmBooking.getBookingClasses().first() != null)
                        {
                            BookingClass classObj = realmBooking.getBookingClasses().first();

                            moreInfo.putExtra("classTypeId", classObj.getClassTypeId());
                            moreInfo.putExtra("instructor", classObj.getInstructorId());
                            //moreInfo.putExtra("centerName", finalCenterName);
                            moreInfo.putExtra("centerName", holder.center.getText());
//                            if(APIResponseHandler.activitesPerWeek.size() != 0){
//                                moreInfo.putExtra("centerName", APIResponseHandler.centerNamesMap.get(classObj.getCenterId()));
//                            }else{
//                                moreInfo.putExtra("centerName", StorageHandler.centerNamesMap.get(classObj.getCenterId()));
//                            }
                            moreInfo.putExtra("duration", String.valueOf(classObj.getDurationInMinutes()));
                            moreInfo.putExtra("bookedCount", String.valueOf(classObj.getBookedPersonsCount()));
                            moreInfo.putExtra("maxAttending", String.valueOf(classObj.getMaxPersonsCount()));
                            moreInfo.putExtra("posInQueue", String.valueOf(realmBooking.getPositionInQueue()));
                            moreInfo.putExtra("startTime", String.valueOf(classObj.getStartTime()));

                        }
                        else
                        {
                            Toast.makeText(activity, "Kan inte hitta class",
                                    Toast.LENGTH_LONG).show();
                        }
                        CustomAdapter.this.activity.startActivity(moreInfo, null);
                    }
                    else
                    {
                        Toast.makeText(activity, "Kan inte visa mer om passet",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void setupPreviousActivity(View view, int position)
    {
        final PreviousActivityHolder holder = (PreviousActivityHolder) view.getTag();
        Activity previousActivity = (Activity) getItem(position);
        mCalendar.setTime(trainingList.get(position).getDate());
        int month = mCalendar.get(Calendar.MONTH);
        String previousDateFormat = swedish_days[mCalendar.get(Calendar.DAY_OF_WEEK) - 1] + " " + mCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (month + 1);

        holder.title.setText(previousActivity.getSubType());
        holder.date.setText(previousDateFormat);
        setActivityImage(holder, previousActivity);

        ImageView box = (ImageView) view.findViewById(R.id.checkbox_img);
        TextView text = (TextView) view.findViewById(R.id.checkbox_text);
        if (previousActivity.getStatus().equals("COMPLETED"))
        {
            box.setImageResource(R.drawable.checkbox_filled);
            text.setText("Avklarat!");
        } else
        {
            box.setImageResource(R.drawable.checkbox_empty);
            text.setText("Avklarat?");
        }
    }

    private void setActivityImage(PreviousActivityHolder previousActivityHolder, Activity previousActivity)
    {

        int imageNo = 0;

        if (previousActivity.getSubType().equals("cycle"))
        {
            imageNo = 1;
        } else if (previousActivity.getSubType().equals("walking") || previousActivity.getSubType().equals("running"))
        {
            imageNo = 2;
        } else if (previousActivity.getType().equals("GYM"))
        {
            imageNo = 3;
        } else if (previousActivity.getType().equals("GROUP"))
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
    public View getHeaderView(int position, View convertView, final ViewGroup parent)
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
        int weekNumber = new DateTime(trainingList.get(position).getDate()).getWeekOfWeekyear();

        int weekStartDate = new DateTime().withWeekOfWeekyear(Integer.valueOf(mWeeks[position])).dayOfMonth().getMaximumValue();
        int weekStartDate2 = new DateTime().withWeekOfWeekyear(weekNumber).getDayOfMonth() - 2;
        weekStartDate = ((weekStartDate + weekStartDate2) % weekStartDate);
        if (weekStartDate == 29)
        {
            weekStartDate = 30;
        }

        int weekEndDate = new DateTime().withWeekOfWeekyear(weekNumber).dayOfMonth().getMaximumValue();
        int weekEndDate2 = new DateTime().withWeekOfWeekyear(weekNumber + 1).getDayOfMonth() - 2;
        weekEndDate = ((weekEndDate + weekEndDate2) % weekEndDate);

        int month = new DateTime().withWeekOfWeekyear(weekNumber + 1).getMonthOfYear();

        if (weekEndDate2 < 0)
        {
            month--;
        }

        String headerText;
        if (trainingList.get(position).getDate().before(dateToday))
        {
            headerText = "Vecka " + mWeeks[position] + " (" + weekStartDate + "-" + weekEndDate + "/" + month + ")";
        }
        else
        {
            mCalendar.setTime(trainingList.get(position).getDate());
            month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_WEEK) - 2;
            int dayinweek = (day+7)%7;
            String previousDateFormat = swedish_days[dayinweek] + " " + mCalendar.get(Calendar.DAY_OF_MONTH) + " " + swedish_months[mCalendar.get(Calendar.MONTH)];

            headerText = previousDateFormat;

        }
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int i)
    {
        if (trainingList.get(i).getDate().before(dateToday))
        {
            Date activityDate = trainingList.get(i).getDate();
            mCalendar.setTime(activityDate);
            return mCalendar.get(Calendar.WEEK_OF_YEAR);
        } else
        {
            return i;
        }
    }

    private class HeaderViewHolder
    {
        TextView text;
    }
}