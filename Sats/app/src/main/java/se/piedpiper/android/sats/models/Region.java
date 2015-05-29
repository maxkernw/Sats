package se.piedpiper.android.sats.models;

import java.util.ArrayList;

public final class Region {
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
