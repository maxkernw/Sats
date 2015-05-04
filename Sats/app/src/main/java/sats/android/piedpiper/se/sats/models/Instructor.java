package sats.android.piedpiper.se.sats.models;

/**
 * Created by Osama on 2015-04-24.®
 */
public final class Instructor {
    private final String id;
    private final String name;

    public Instructor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
