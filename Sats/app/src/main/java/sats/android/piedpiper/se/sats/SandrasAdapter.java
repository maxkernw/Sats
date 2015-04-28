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

public class SandrasAdapter extends BaseAdapter{

    ArrayList<PreviousTraining> trainingList;
    private final Activity activity;
    private final LayoutInflater inflater;
    private static final int NUMBER_OF_VIEWS_SERVED_BY_ADAPTER = 2;

    private final int numberOfPositions;

    public SandrasAdapter(Activity activity, ArrayList<PreviousTraining> trainingList)
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

        PreviousTraining myPreviousTrainingObj = (PreviousTraining) getItem(position);

        if(convertView == null){
            convertView = inflatePreviousTraining(parent);
            setupPreviousTraining(convertView, position);
        }

        return convertView;
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

    public void setupPreviousTraining(View view, int position){

        PreviousTrainingHolder previousTrainingHolder = (PreviousTrainingHolder) view.getTag();
        PreviousTraining boxObj = (PreviousTraining) getItem(position);

        //set
        previousTrainingHolder.title.setText(boxObj.getTitle());
        previousTrainingHolder.date.setText(boxObj.getDate());

        //set img
        switch (boxObj.getImageNo()){
            case 0:
                previousTrainingHolder.img.setImageResource(R.drawable.strength_trainging_icon);
                break;
            case 1:
                previousTrainingHolder.img.setImageResource(R.drawable.cykling_icon);
                break;
            case 2:
                previousTrainingHolder.img.setImageResource(R.drawable.running_icon);
                break;
        }

        //checkbox
        CheckBox box = (CheckBox) view.findViewById(R.id.checkbox1);
        //sätt till checked/unchecked i början
        box.setSelected(boxObj.isSelected());

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

    class PreviousTrainingHolder{
        CheckBox checkbox;
        TextView title;
        TextView date;
        ImageView img;
    }
}

