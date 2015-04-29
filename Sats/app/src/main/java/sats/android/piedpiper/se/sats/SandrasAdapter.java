package sats.android.piedpiper.se.sats;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.TrainingActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class SandrasAdapter extends BaseAdapter implements StickyListHeadersAdapter{

    ArrayList<TrainingActivity> trainingList;
    private final Activity activity;
    private final LayoutInflater inflater;
    private static final int NUMBER_OF_VIEWS_SERVED_BY_ADAPTER = 2;

    private final int numberOfPositions;

    public SandrasAdapter(Activity activity, ArrayList<TrainingActivity> trainingList)
    {
        this.activity = activity;
        this.trainingList = trainingList;
        inflater = activity.getLayoutInflater();

        numberOfPositions = trainingList.size();
    }

    @Override
    public int getCount() {
        return numberOfPositions;
    }

    @Override
    public Object getItem(int position) {
        return trainingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        TrainingActivity myTrainingActivityObj = (TrainingActivity) getItem(position);

        boolean isPreviousActivity;
        isPreviousActivity = (myTrainingActivityObj.satus.equals("COMPLETED"));

        //PreviousTraining myPreviousTrainingObj = (PreviousTraining) getItem(position);

        if(convertView == null){
            if(isPreviousActivity){
                convertView = inflatePreviousTraining(parent);
                setupPreviousTraining(convertView, position);
            }else{
                //fix. kolla om egenTräning eller SATSPass
                convertView = inflateBookedActivities(parent);
                setupBookedActivity(convertView, position);
            }

        }

        return convertView;
    }

    private View inflateBookedActivities(ViewGroup parent){
        View newView;

        BookedActivitiesHolder holder;
        holder = new BookedActivitiesHolder();

        newView = inflater.inflate(R.layout.booked_activity_row, parent,
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

    public View inflatePreviousTraining(ViewGroup parent){
        View newView;

        PreviousTrainingHolder previousTrainingHolder;
        previousTrainingHolder = new PreviousTrainingHolder();

        newView = inflater.inflate(R.layout.previous_training_fragment, parent,
                false);
        //get
        previousTrainingHolder.title = (TextView) newView.findViewById(R.id.title);
        previousTrainingHolder.date = (TextView) newView.findViewById(R.id.date);
        previousTrainingHolder.img = (ImageView) newView.findViewById(R.id.img);


        newView.setTag(previousTrainingHolder);

        return newView;
    }

    public void setupBookedActivity(View view, int position)
    {
        BookedActivitiesHolder holder = (BookedActivitiesHolder) view.getTag();
        TrainingActivity bookedActivityObj = (TrainingActivity) getItem(position);

        String duration = String.valueOf(bookedActivityObj.durationInMinutes);

        //set
        holder.bigClockHours.setText("12"); //fix (formatera startTime)
        holder.bigClockMinutes.setText("30");  //fix
        holder.classTotalTime.setText(String.valueOf(bookedActivityObj.durationInMinutes));
        holder.pass.setText(bookedActivityObj.name);
        holder.center.setText(bookedActivityObj.centerId);
        holder.instructor.setText(bookedActivityObj.instructorId);
        holder.participants.setText(String.valueOf(bookedActivityObj.bookedPersonsCount));
    }

    public void setupPreviousTraining(View view, int position){

        PreviousTrainingHolder previousTrainingHolder = (PreviousTrainingHolder) view.getTag();
        TrainingActivity previousActivity = (TrainingActivity) getItem(position);

        //set
        previousTrainingHolder.title.setText(previousActivity.name);
        previousTrainingHolder.date.setText("Fredag 12/7"); //fix (formatera startTime)

        //set img (flytta till separat metod)
        /*
        0 - all_training = type(OTHER) & not subType(cycle, running, strength osv)
        1 - cycling = subtype(cycle)
        2 - running = subtype(walking/running)
        3 - strength = (GYM, gym)
        4 - group_training = type(GROUP but not if subType is cycling
         */

        int imageNo = 0;

        if(previousActivity.subType.equals("cycle")){
            imageNo = 1;
        }else if(previousActivity.subType.equals("walking") || previousActivity.subType.equals("running")){
            imageNo = 2;
        }else if(previousActivity.type.equals("GYM")){
            imageNo = 3;
        }else if(previousActivity.type.equals("GROUP")){
            imageNo = 4;
        }else{
            imageNo = 0;
        }

        switch (imageNo){
            case 0:
                previousTrainingHolder.img.setImageResource(R.drawable.all_training_icons);
                break;
            case 1:
                previousTrainingHolder.img.setImageResource(R.drawable.cykling_icon);
                break;
            case 2:
                previousTrainingHolder.img.setImageResource(R.drawable.running_icon);
                break;
            case 3:
                previousTrainingHolder.img.setImageResource(R.drawable.strength_trainging_icon);
                break;
            case 4:
                previousTrainingHolder.img.setImageResource(R.drawable.group_training_icon);
                break;
        }

        //checkbox
        CheckBox box = (CheckBox) view.findViewById(R.id.checkbox1);
        //sätt till checked/unchecked i början
        box.setSelected(previousActivity.satus.equals("COMPLETED"));

        //Lyssnar på click fr. varje item i listan
        box.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                if(cb.isChecked()){
                    cb.setText("Avklarat!");
                }else {
                    cb.setText("Avklarat?");
                }
            }
        });
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent)
    {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.date_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.date_header);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set date_header text as first char in name
        String headerText = trainingList.get(position).startTime.toString();
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int i)
    {
        return i;
    }

    class PreviousTrainingHolder{
        CheckBox checkbox;
        TextView title;
        TextView date;
        ImageView img;
    }

    private class BookedActivitiesHolder
    {
        TextView bigClockHours;
        TextView bigClockMinutes;
        TextView classTotalTime;
        TextView pass;
        TextView center;
        TextView instructor;
        TextView participants;
    }
    private class HeaderViewHolder{
        TextView text;
    }
}

