package sats.android.piedpiper.se.sats;

/**
 * Created by Osama on 2015-04-24.
 */
public final class Instructor {
    private String id;
    private String name;

    protected Instructor(String id, String name) {
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
