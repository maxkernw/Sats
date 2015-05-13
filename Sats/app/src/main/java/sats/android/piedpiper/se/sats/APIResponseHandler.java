package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;

import sats.android.piedpiper.se.sats.models.*;
import sats.android.piedpiper.se.sats.models.Class;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class APIResponseHandler
{
    public static final String raspberryURL = "http://80.217.172.201:8080/sats/se/training/activities/?fromDate=20130404&toDate=20150604";
    public static final String sURL = "http://192.168.68.226:8080/sats-server/se/training/activities/?fromDate=20141210&toDate=20160521";
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;

    public APIResponseHandler(android.app.Activity activity){
        this.activity = activity;
        myActivities = new ArrayList<>();
    }

    public void getAllActivities(final StickyListHeadersListView listView)
    {
        Ion.with(activity).load(raspberryURL).setTimeout(100000).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (e == null) {
                    JsonArray jsonArray = result.getAsJsonArray("Activities");

                    for (JsonElement element : jsonArray) {
                        myActivities.add(getActivityObj(element));
                    }

                    Collections.sort(myActivities);

                    for (int i = 0; i < myActivities.size(); i++) {
                        Log.e("Info", "date" + i + ": " + myActivities.get(i).date.toString());
                    }

                    listView.setAdapter(new CustomAdapter(activity, myActivities));

                } else {
                    Log.e("Info", "server timed out");
                }
            }
        });
    }

    private Activity getActivityObj(JsonElement element)
    {
        JsonObject object = element.getAsJsonObject();

        DateTime date;
        Booking booking = null;
        boolean hasBooking = object.has("booking");
        String dateString = object.get("date").getAsString();
        String comment = object.get("comment").getAsString();
        String id = object.get("id").getAsString();
        String source = object.get("source").getAsString();
        String status = object.get("status").getAsString();
        String subType = object.get("subType").getAsString();
        String type = object.get("type").getAsString();
        int durationInMinutes = object.get("durationInMinutes").getAsInt();
        int distanceInKm = object.get("distanceInKm").getAsInt();

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        date = format.parseDateTime(dateString);

        if(hasBooking){
            JsonObject bookingJsonObj = object.get("booking").getAsJsonObject();
            booking = getBookingObj(bookingJsonObj);
        }

        return new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
    }

    private Booking getBookingObj(JsonObject object)
    {
        sats.android.piedpiper.se.sats.models.Class myClass = null;
        boolean hasClass = object.has("class");
        String status = object.get("status").getAsString();
        String center = object.get("center").getAsString();
        String id = object.get("id").getAsString();
        int positionInQueue = object.get("positionInQueue").getAsInt();

        if(hasClass){
            JsonObject classJsonObj = object.get("class").getAsJsonObject();
            myClass = getClassObj(classJsonObj);
        }

        return new Booking(status, myClass, center, id, positionInQueue);
    }

    private Class getClassObj(JsonObject object)
    {
        DateTime startTime;
        ArrayList<Integer> classCategoryIds = new ArrayList<>();
        String startTimeString = object.get("startTime").getAsString();
        String centerId = object.get("centerId").getAsString();
        String centerFilterId = object.get("centerFilterId").getAsString();
        String classTypeId = object.get("classTypeId").getAsString();
        String id = object.get("id").getAsString();
        String instructorId = object.get("instructorId").getAsString();
        String name = object.get("name").getAsString();
        int durationInMinutes = object.get("durationInMinutes").getAsInt();
        int bookedPersonsCount = object.get("bookedPersonsCount").getAsInt();
        int maxPersonsCount = object.get("maxPersonsCount").getAsInt();
        int waitingListCount = object.get("waitingListCount").getAsInt();
        boolean hasCategoryIds = object.has("classCategoryIds");

        if(hasCategoryIds){
            JsonArray categoryIds = object.get("classCategoryIds").getAsJsonArray();

            for(int i = 0; i < categoryIds.size(); i++){
                int categoryId = categoryIds.get(i).getAsInt();
                classCategoryIds.add(categoryId);
            }
        }

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        startTime = format.parseDateTime(startTimeString);

        return new Class(centerId, centerFilterId, classTypeId, durationInMinutes, id, instructorId, name, startTime, bookedPersonsCount, maxPersonsCount, waitingListCount, classCategoryIds);
    }

}
