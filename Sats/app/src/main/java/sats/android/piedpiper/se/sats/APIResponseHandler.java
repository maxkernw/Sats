package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.joda.time.DateTime;

import java.lang.String;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Klass;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class APIResponseHandler
{
    public static final String raspberryURL = "http://80.217.172.201:8080/sats/se/training/activities/?fromDate=20150101&toDate=20160101";
    public static final String sURL = "http://192.168.68.226:8080/sats-server/se/training/activities/?fromDate=20141210&toDate=20160521";
    public static final String centersURL = "https://api2.sats.com/v1.0/se/centers";
    private static final String TAG = "APIresponseHandler";
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;
    private HashMap<String, String> centerNamesMap;
    private static Realm realm;
    public static int[] weekPosition = new int[53];
    public static int week = 0;
    public static int[] activitesPerWeek = new int[53];
    public static int thisWeek = 0;
    int i = 0;

    public APIResponseHandler(android.app.Activity activity)
    {
        this.activity = activity;
        myActivities = new ArrayList<>();
        centerNamesMap = new HashMap<>();
    }

    public void getAllActivities(final StickyListHeadersListView listView)
    {
        getCenterNames();

        Ion.with(activity).load(raspberryURL).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {

            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (e == null) {
                    JsonArray jsonArray = result.getAsJsonArray("Activities");
                    Realm.deleteRealmFile(activity.getApplicationContext());
                    realm = Realm.getInstance(activity.getApplicationContext());

                    for (JsonElement element : jsonArray)
                    {
                        myActivities.add(getActivityObj(element));
                    }

                    //////
                    // Osamas sortering
                    //////
                    int x = myActivities.size();
                    int y;
                    for (int m = x; m >= 0; m--) {
                        for (int i = 0; i < x - 1; i++) {
                            y = i + 1;
                            if (myActivities.get(i).getDate().getTime() > myActivities.get(y).getDate().getTime()) {
                                Activity temp;
                                temp = myActivities.get(i);
                                myActivities.set(i, myActivities.get(y));
                                myActivities.set(y, temp);
                            }
                        }
                    }
                    //////

                    for (int i = 0; i < myActivities.size(); i++) {
                        Log.i(TAG, "date" + i + ": " + myActivities.get(i).getDate().toString());
                        DateTime joda = new DateTime(myActivities.get(i).getDate());

                        if(joda.getWeekOfWeekyear() != week){
                            weekPosition[joda.getWeekOfWeekyear()] = i;
                            Log.e("DENNA VECKAN: ", String.valueOf(joda.getWeekOfWeekyear()));
                            week = joda.getWeekOfWeekyear();
                        }
                        if(joda.getWeekOfWeekyear() == week){
                            activitesPerWeek[joda.getWeekOfWeekyear()]++;
                        }
                    }

                    listView.setAdapter(new CustomAdapter(activity, myActivities));
                    realm.close();

                } else {
                    Log.e(TAG, "Could not get activities!");
                    e.printStackTrace();
                }
            }
        });
    }

    private Activity getActivityObj(JsonElement element)
    {
        JsonObject object = element.getAsJsonObject();

        realm.beginTransaction();
        sats.android.piedpiper.se.sats.models.Activity realmActivity = realm.createObject(sats.android.piedpiper.se.sats.models.Activity.class);

        Date date = null;
        Booking booking = null;
        boolean hasBooking = object.has("booking");
        String dateString = object.get("date").getAsString();
        String comment = object.get("comment").getAsString();
        realmActivity.setComment(comment);
        String id = object.get("id").getAsString();
        realmActivity.setId(id);
        String source = object.get("source").getAsString();
        realmActivity.setSource(source);
        String status = object.get("status").getAsString();
        realmActivity.setStatus(status);
        String subType = object.get("subType").getAsString();
        realmActivity.setSubType(subType);
        String type = object.get("type").getAsString();
        realmActivity.setType(type);
        int durationInMinutes = object.get("durationInMinutes").getAsInt();
        realmActivity.setDurationInMinutes(durationInMinutes);
        int distanceInKm = object.get("distanceInKm").getAsInt();
        realmActivity.setDistanceInKm(distanceInKm);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format.parse(dateString);
            realmActivity.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Could not parse dateString from json to date");
        }

        if(subType.equals("gym")){
            subType = "Styrketräning";
            realmActivity.setSubType(subType);
        }

        realm.commitTransaction();
        if(hasBooking){
            JsonObject bookingJsonObj = object.get("booking").getAsJsonObject();
            booking = getBookingObj(bookingJsonObj);
        }


        return new Activity(booking, comment, date, distanceInKm, durationInMinutes, id, source, status, subType, type);
    }

    private Booking getBookingObj(JsonObject object)
    {
        realm.beginTransaction();
        Booking realmBooking = realm.createObject(Booking.class);

        Klass myClass = null;
        boolean hasClass = object.has("class");
        String status = object.get("status").getAsString();
        realmBooking.setStatus(status);
        String center = object.get("center").getAsString();
        realmBooking.setCenter(center);
        String id = object.get("id").getAsString();
        realmBooking.setId(id);
        int positionInQueue = object.get("positionInQueue").getAsInt();
        realmBooking.setPositionInQueue(positionInQueue);

        realm.commitTransaction();

        if(centerNamesMap.containsKey(center)){
            center = centerNamesMap.get(center);
            Log.e("Info", "centerName: " + center);
        }

        if(hasClass){
            JsonObject classJsonObj = object.get("class").getAsJsonObject();
            myClass = getClassObj(classJsonObj);
        }

        return new Booking(status, myClass, center, id, positionInQueue);
    }

    private Klass getClassObj(JsonObject object)
    {
        realm.beginTransaction();
        Klass realmClass = realm.createObject(Klass.class);

        Date startTime = null;
        ArrayList<Integer> classCategoryIds = new ArrayList<>();
        String startTimeString = object.get("startTime").getAsString();
        String centerId = object.get("centerId").getAsString();
        realmClass.setCenterId(centerId);
        String centerFilterId = object.get("centerFilterId").getAsString();
        realmClass.setCenterFilterId(centerFilterId);
        String classTypeId = object.get("classTypeId").getAsString();
        realmClass.setClassTypeId(classTypeId);
        String id = object.get("id").getAsString();
        realmClass.setId(id);
        String instructorId = object.get("instructorId").getAsString();
        realmClass.setInstructorId(instructorId);
        String name = object.get("name").getAsString();
        realmClass.setName(name);
        int durationInMinutes = object.get("durationInMinutes").getAsInt();
        realmClass.setDurationInMinutes(durationInMinutes);
        int bookedPersonsCount = object.get("bookedPersonsCount").getAsInt();
        realmClass.setBookedPersonsCount(bookedPersonsCount);
        int maxPersonsCount = object.get("maxPersonsCount").getAsInt();
        realmClass.setMaxPersonsCount(maxPersonsCount);
        int waitingListCount = object.get("waitingListCount").getAsInt();
        realmClass.setWaitingListCount(waitingListCount);
        boolean hasCategoryIds = object.has("classCategoryIds");

        if(hasCategoryIds){
            JsonArray categoryIds = object.get("classCategoryIds").getAsJsonArray();

            for(int i = 0; i < categoryIds.size(); i++){
                int categoryId = categoryIds.get(i).getAsInt();
                realmClass.getClassCategoryIds().add(categoryId);
                classCategoryIds.add(categoryId);
            }
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            startTime = format.parse(startTimeString);
            realmClass.setStartTime(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG, "Could not parse dateString from json to date");
        }

        realm.commitTransaction();
        return new Klass(centerId, centerFilterId, classTypeId, durationInMinutes, id, instructorId, name, startTime, bookedPersonsCount, maxPersonsCount, waitingListCount, classCategoryIds);
    }

    private void getCenterNames()
    {
        try
        {
            JsonObject result = Ion.with(activity).load(centersURL).asJsonObject().get();
            JsonArray jsonRegionsArray = result.getAsJsonArray("regions");
            JsonArray jsonCentersArray = new JsonArray();

            for (JsonElement element : jsonRegionsArray) {   //loopar regions. för varje region
                JsonObject regionObject = element.getAsJsonObject(); //en region ex sthlm
                jsonCentersArray.addAll(regionObject.get("centers").getAsJsonArray()); //lägg till alla centers för ex sthlm
            }

            for (JsonElement centerElement : jsonCentersArray) {    //loopar centers
                JsonObject center = centerElement.getAsJsonObject();
                String centerId = center.get("id").getAsString();
                String centerName = center.get("name").getAsString();

                centerNamesMap.put(centerId, centerName);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            Log.e("Info", "Could not get center names");
            e.printStackTrace();
        }
    }

}
