package sats.android.piedpiper.se.sats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter
{
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public TestAdapter(Context context, String[] data) {

        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount()
    {
        return data.length;
    }

    @Override
    public Object getItem(int i)
    {
        return data[i];
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup)
    {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.list_items, null);

        return vi;
    }
}
