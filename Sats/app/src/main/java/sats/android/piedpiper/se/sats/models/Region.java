package sats.android.piedpiper.se.sats.models;

import java.util.ArrayList;

import sats.android.piedpiper.se.sats.models.Center;

/**
 * Created by Osama on 2015-04-27.
 */
public class Region {
    private final ArrayList<Center> centerList;

    public Region(ArrayList<Center> centerList) {
        this.centerList = centerList;
    }

    public ArrayList<Center> getCenterList() {
        return centerList;
    }

    public void addCenter(Center center) {
        centerList.add(center);
    }
}
