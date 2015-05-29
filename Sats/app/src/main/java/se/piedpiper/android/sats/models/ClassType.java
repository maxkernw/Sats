package se.piedpiper.android.sats.models;

import java.util.ArrayList;

public class ClassType {

    public String description;
    public String id;
    public String name;
    public String videoURL;
    public ArrayList<Profile> stats;


    public ClassType(String description, String id, String name, ArrayList<Profile> stats, String videoURL) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getValue(int index){
        return stats.get(index).value;
    }

    public ArrayList<Profile> getProfile(){
        return stats;
    }

}
