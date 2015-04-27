package se.sael.listexample.listexample;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    int myPosition = 0;
    ListView listView;
    //Array list of countries
    ArrayList<Checkbox> trainingList;

    String data[] = new String[] {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trainingList = new ArrayList<Checkbox>();
        Checkbox activity = new Checkbox("0Avklarat?","Bajs",false, 0);
        trainingList.add(activity);
        activity = new Checkbox("1Avklarat?","Bajs",false, 1);
        trainingList.add(activity);
        activity = new Checkbox("2Avklarat?","Bajs",false, 2);
        trainingList.add(activity);
        activity = new Checkbox("3Avklarat?","Bajs",false, 3);
        trainingList.add(activity);
        activity = new Checkbox("4Avklarat?","Bajs",false, 4);
        trainingList.add(activity);
        activity = new Checkbox("5Avklarat?","Bajs",false, 5);
        trainingList.add(activity);
        activity = new Checkbox("6Avklarat?","Bajs",false, 6);
        trainingList.add(activity);
        activity = new Checkbox("7Avklarat?","Bajs",false, 7);
        trainingList.add(activity);
        activity = new Checkbox("8Avklarat?","Bajs",false, 8);
        trainingList.add(activity);
        activity = new Checkbox("9Avklarat?","Bajs",false, 9);


        listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new ListAdapter(this,
                R.layout.previous_training_fragment, trainingList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Checkbox checkbox = (Checkbox) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + checkbox.getCheckboxText(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class ListAdapter extends ArrayAdapter<Checkbox>
    {
        private ArrayList<Checkbox> trainingList;

        public ListAdapter(Context context, int textViewResourceId, ArrayList<Checkbox> trainingList)
        {
            super(context, textViewResourceId, trainingList);

            this.trainingList = new ArrayList<Checkbox>();
            this.trainingList.addAll(trainingList);
        }

        private class ViewHolder
        {
            CheckBox checkBox;
            TextView checkboxText;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            Log.v("ConvertView", String.valueOf(position));
            LayoutInflater inflater;
            ViewHolder holder = null;

            if (convertView == null)
            {
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // convertView = inflater.inflate(R.layout.previous_training_fragment, parent, false); //null?
                convertView = inflater.inflate(R.layout.previous_training_fragment, null);

                holder = new ViewHolder();
                holder.checkboxText = (TextView) convertView.findViewById(R.id.checkbox_text);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox1);
                convertView.setTag(holder);



            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Checkbox myCheckbox = (Checkbox) cb.getTag();
                    Toast.makeText(getApplicationContext(),
                            "Clicked on Checkbox: " + position + " " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    myCheckbox.setSelected(cb.isChecked());
                    if(myCheckbox.isSelected()){
                        trainingList.get(position).setCheckboxText("Avklarat!");
                    }else {
                        trainingList.get(position).setCheckboxText("Avklarat?");
                    }
                }
            });

            Checkbox checkbox = trainingList.get(myPosition);
            holder.checkboxText.setText(checkbox.getCheckboxText());
            holder.checkBox.setChecked(checkbox.isSelected());
            holder.checkBox.setTag(checkbox);

            /*if(position == 2){
                TextView date = (TextView) convertView.findViewById(R.id.date);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                title.setText("Cykla");
                date.setText("Onsdag 23/4");
                ImageView i1 = (ImageView) convertView.findViewById(R.id.img);
                i1.setImageResource(R.drawable.cykling_icon);
            }*/

            return convertView;
        }
    }
}

