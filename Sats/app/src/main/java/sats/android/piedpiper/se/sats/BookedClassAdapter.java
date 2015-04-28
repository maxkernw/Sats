package sats.android.piedpiper.se.sats;

import sats.android.piedpiper.se.sats.models.Booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;


public class BookedClassAdapter extends BaseAdapter
{
    public Context mContext;
    public LayoutInflater mLayoutInflater;
    public ArrayList<Booking> bookings;

    private static class ViewHolder
    {
        TextView bigClockHours;
        TextView bigClockMinutes;
        TextView classTotalTime;
        TextView pass;
        TextView center;
        TextView instructor;
        TextView participants;
    }

    public BookedClassAdapter(Context context, ArrayList<Booking> bookings)
    {
        this.mContext = context;
        this.bookings = bookings;
    }

    @Override
    public int getCount()
    {
        return bookings.size();
    }

    @Override
    public Booking getItem(int i)
    {
        return bookings.get(i);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Booking booking = getItem(position);
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext)
                    .inflate(R.layout.booked_activity, parent, false);
            holder = new ViewHolder();

            holder.bigClockHours = (TextView) convertView.findViewById(R.id.hour);
            holder.bigClockMinutes = (TextView) convertView.findViewById(R.id.minutes);
            holder.classTotalTime = (TextView) convertView.findViewById(R.id.class_time);
            holder.pass = (TextView) convertView.findViewById(R.id.pass);
            holder.center = (TextView) convertView.findViewById(R.id.center);
            holder.instructor = (TextView) convertView.findViewById(R.id.instructor);
            holder.participants = (TextView) convertView.findViewById(R.id.number_participants);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bigClockHours.setText("12");
        holder.bigClockMinutes.setText("30");
        holder.classTotalTime.setText("30 min");
        holder.pass.setText("SATS Core");
        holder.center.setText(booking.aClass.centerId);
        holder.instructor.setText(booking.aClass.instructorId);
        holder.participants.setText(String.valueOf(booking.aClass.waitingListCount));

        return convertView;
    }
}