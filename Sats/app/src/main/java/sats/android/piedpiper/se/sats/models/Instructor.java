package sats.android.piedpiper.se.sats.models;

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
