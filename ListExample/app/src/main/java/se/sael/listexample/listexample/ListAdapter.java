package se.sael.listexample.listexample;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Checkbox>
{
    Context context;
    String data[];
    int counter = 0;
    boolean isChecked = false;

    private ArrayList<Checkbox> trainingList;

    public ListAdapter(Context context, int textViewResourceId, ArrayList<Checkbox> trainingList)
    {
        super(context, textViewResourceId, trainingList);

        this.trainingList = new ArrayList<Checkbox>();
        this.trainingList.addAll(trainingList);

        this.context = context;
        this.data = data;
    }

    private class ViewHolder
    {
        CheckBox checkBox;
        TextView checkboxText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Log.v("ConvertView", String.valueOf(position));
        LayoutInflater inflater;
        ViewHolder holder = null;

        if (convertView == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // convertView = inflater.inflate(R.layout.previous_training_fragment, parent, false); //null?
            convertView = inflater.inflate(R.layout.previous_training_fragment, null);

            holder = new ViewHolder();
            holder.checkboxText = (TextView) convertView.findViewById(R.id.checkbox_text);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);

            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Checkbox myCheckbox = (Checkbox) cb.getTag();
                    Toast.makeText(context.getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    myCheckbox.setSelected(cb.isChecked());
                }
            });

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Checkbox checkbox = trainingList.get(position);
        if(checkbox.isSelected()){
            holder.checkboxText.setText("Avklarat!");
        }else {
            holder.checkboxText.setText("Avklarat?");
        }
        holder.checkBox.setText(checkbox.getCheckBox());
        holder.checkBox.setChecked(checkbox.isSelected());
        holder.checkBox.setTag(checkbox);

        if(position == 2){
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText("Cykla");
            date.setText("Onsdag 23/4");
            ImageView i1 = (ImageView) convertView.findViewById(R.id.img);
            i1.setImageResource(R.drawable.cykling_icon);
        }

        counter++;
        return convertView;
    }

}
