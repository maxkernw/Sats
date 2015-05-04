package sats.android.piedpiper.se.sats.models;

public final class PreviousTraining
{
    private String title;
    private String date;
    private String type;
    private int imageNo;
    private boolean selected = false;

    public PreviousTraining(String title, String date, boolean selected, int imageNo, String type){
        this.title = title;
        this.date = date;
        this.type = type;
        this.imageNo = imageNo;
        this.selected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageNo() {
        return imageNo;
    }

    public void setImageNo(int imageNo) {
        this.imageNo = imageNo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}