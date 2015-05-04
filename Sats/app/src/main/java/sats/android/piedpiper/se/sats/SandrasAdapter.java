package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;

import sats.android.piedpiper.se.sats.models.TrainingActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class SandrasAdapter extends BaseAdapter implements StickyListHeadersAdapter
{
    private ArrayList<TrainingActivity> trainingList;
    private final Activity activity;
    private final LayoutInflater inflater;
    private final int numberOfPositions;
    private Calendar mCalendar = Calendar.getInstance();


    public SandrasAdapter(Activity activity, ArrayList<TrainingActivity> trainingList)
    {
        this.activity = activity;
        this.trainingList = trainingList;
        inflater = activity.getLayoutInflater();
        numberOfPositions = trainingList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        TrainingActivity myTrainingActivityObj = (TrainingActivity) getItem(position);

        boolean isPreviousActivity;
        isPreviousActivity = (myTrainingActivityObj.satus.equals("COMPLETED")); //TODO och/eller kolla om datum är innan dagens datum

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
        holder.pass = (TextView) newView.findViewById(R.id.pass);
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
        TrainingActivity ownActivityObj = (TrainingActivity) getItem(position);

        //set
        holder.title.setText(ownActivityObj.name);
        holder.totalTime.setText(String.valueOf(ownActivityObj.durationInMinutes));
    }

    private void setupBookedActivity(View view, int position)
    {
        BookedActivityHolder holder = (BookedActivityHolder) view.getTag();
        TrainingActivity bookedActivityObj = (TrainingActivity) getItem(position);

        //set
        holder.bigClockHours.setText("12"); //TODO (formatera startTime)
        holder.bigClockMinutes.setText("30");  //TODO
        holder.classTotalTime.setText(String.valueOf(bookedActivityObj.durationInMinutes));
        holder.pass.setText(bookedActivityObj.name);
        holder.center.setText(bookedActivityObj.centerId);
        holder.instructor.setText(bookedActivityObj.instructorId);
        holder.participants.setText(String.valueOf(bookedActivityObj.bookedPersonsCount));

        if(bookedActivityObj.bookedPersonsCount == 0){
            LinearLayout bookedPersons = (LinearLayout) view.findViewById(R.id.participants);
            bookedPersons.setVisibility(View.INVISIBLE);
        }
    }

    private void setupPreviousActivity(View view, int position)
    {
        PreviousActivityHolder previousActivityHolder = (PreviousActivityHolder) view.getTag();
        TrainingActivity previousActivity = (TrainingActivity) getItem(position);

        //set
        previousActivityHolder.title.setText(previousActivity.name);
        previousActivityHolder.date.setText("Fredag 12/7"); //TODO (formatera startTime)
        setActivityImage(previousActivityHolder, previousActivity);

        //checkbox
        CheckBox box = (CheckBox) view.findViewById(R.id.checkbox1);
        //sätt till checked/unchecked i början TODO funkar ej
        box.setSelected(previousActivity.satus.equals("COMPLETED"));

        //Lyssnar på click fr. varje item i listan
        box.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                CheckBox cb = (CheckBox) v;
                if (cb.isChecked())
                {
                    cb.setText("Avklarat!");
                } else
                {
                    cb.setText("Avklarat?");
                }
            }
        });
    }

    private void setActivityImage(PreviousActivityHolder previousActivityHolder, TrainingActivity previousActivity)
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
        mCalendar.setTime(trainingList.get(position).startTime);
        String headerText = mCalendar.get(Calendar.DAY_OF_MONTH) +"/"+ (mCalendar.get(Calendar.MONTH)+1);
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int i)
    {
        return i;
    }

    private class OwnActivityHolder
    {

        TextView title;
        TextView totalTime;
    }

    private class BookedActivityHolder
    {
        TextView bigClockHours;
        TextView bigClockMinutes;
        TextView classTotalTime;
        TextView pass;
        TextView center;
        TextView instructor;
        TextView participants;
    }

    private class HeaderViewHolder
    {
        TextView text;
    }

    private class PreviousActivityHolder
    {
        CheckBox checkbox;
        TextView title;
        TextView date;
        ImageView img;
    }
}


