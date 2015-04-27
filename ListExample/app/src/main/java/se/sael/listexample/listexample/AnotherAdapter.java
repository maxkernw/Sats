package se.sael.listexample.listexample;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Sandra on 2015-04-27.
 */
public class AnotherAdapter extends BaseAdapter{

    private Context mContext;
    Cursor cursor;
    public AnotherAdapter(Context context,Cursor cur)
    {
        super();
        mContext=context;
        cursor=cur;

    }

    public int getCount()
    {
        // return the number of records in cursor
        return cursor.getCount();
    }

    // getView method is called for each item of ListView
    public View getView(int position,  View view, ViewGroup parent)
    {
        // inflate the layout for each item of listView
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.previous_training_fragment, null);

        // move the cursor to required position
        cursor.moveToPosition(position);

        // fetch the sender number and sms body from cursor
        String checboxText = cursor.getString(cursor.getColumnIndex("address"));
        String smsBody=cursor.getString(cursor.getColumnIndex("body"));

        // get the reference of textViews
        TextView textViewConatctNumber=(TextView)view.findViewById(R.id.textViewSMSSender);
        TextView textViewSMSBody=(TextView)view.findViewById(R.id.textViewMessageBody);

        // Set the Sender number and smsBody to respective TextViews
        textViewConatctNumber.setText(senderNumber);
        textViewSMSBody.setText(smsBody);


        return view;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
