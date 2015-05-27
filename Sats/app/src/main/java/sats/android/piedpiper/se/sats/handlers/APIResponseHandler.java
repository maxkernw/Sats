package sats.android.piedpiper.se.sats.handlers;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
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
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import io.realm.RealmResults;
import sats.android.piedpiper.se.sats.models.CenterInfo;
import sats.android.piedpiper.se.sats.adapters.CustomAdapter;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Center;
import sats.android.piedpiper.se.sats.models.ClassType;
import sats.android.piedpiper.se.sats.models.Klass;
import sats.android.piedpiper.se.sats.models.Profile;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class APIResponseHandler
{
    public static final String raspberryURL = "http://80.217.172.201:8080/sats/se/training/activities/?fromDate=20150101&toDate=20160101";
    public static final String sURL = "http://192.168.68.226:8080/sats-server/se/training/activities/?fromDate=20150101&toDate=20160521";
    public static final String centersURL = "https://api2.sats.com/v1.0/se/centers";
    private static final String TAG = "APIresponseHandler";
    public static final String classTypesURL = "https://api2.sats.com/v1.0/se/classtypes";
    public static final String activityTypesURL = "http://80.217.172.201:8080/sats/se/training/activities/";
    private final android.app.Activity activity;
    private ArrayList<Activity> myActivities;
    private ArrayList<ClassType> classTypes;
    private HashMap<String, String> centerNamesMap;
    private HashMap<String, String> activityNamesMap;

    public static HashMap<String, CenterInfo> markers = new HashMap<>();
    public static HashMap<String,String> urls = new HashMap<>();

    private static HashMap<String,LatLng> markers2 = new HashMap<>();
    private static Realm realm;
    public static int week = 0;
    //public static int[] weekPosition = new int[53];
    //public static int[] activitesPerWeek = new int[53];
    public static HashMap<Integer,Integer> weekPosition = new HashMap<>();
    public static HashMap<Integer,Integer> activitesPerWeek = new HashMap<>();

    public APIResponseHandler(android.app.Activity activity)
    {
        this.activity = activity;
        myActivities = new ArrayList<>();
        centerNamesMap = new HashMap<>();
        classTypes = new ArrayList<>();
    }

    public void getAllActivities(final StickyListHeadersListView listView)
    {
        if(realm != null)
        {
            Realm.deleteRealmFile(activity);
        }
        realm = Realm.getInstance(activity);
        getCenterNames();

        Ion.with(activity).load(raspberryURL).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (e == null)
                {
                    JsonArray jsonArray = result.getAsJsonArray("Activities");

                    for (JsonElement element : jsonArray)
                    {
                        myActivities.add(getActivityObj(element));
                    }

                    realm.close();

                    int x = myActivities.size();
                    int y;
                    for (int m = x; m >= 0; m--)
                    {
                        for (int i = 0; i < x - 1; i++)
                        {
                            y = i + 1;
                            if (myActivities.get(i).getDate().getTime() > myActivities.get(y).getDate().getTime())
                            {
                                Activity temp;
                                temp = myActivities.get(i);
                                myActivities.set(i, myActivities.get(y));
                                myActivities.set(y, temp);
                            }
                        }
                    }

                    week = new DateTime(myActivities.get(0).getDate()).getWeekOfWeekyear()-1;
                    for (int i = 0; i < myActivities.size(); i++) {
                        DateTime joda = new DateTime(myActivities.get(i).getDate());

                        if(joda.getWeekOfWeekyear() != week){
                            weekPosition.put(joda.getWeekOfWeekyear(), i);
                            week = joda.getWeekOfWeekyear();
                        }
                        if(joda.getWeekOfWeekyear() == week){
                            if(activitesPerWeek.containsKey(joda.getWeekOfWeekyear())){
                                int value = activitesPerWeek.get(joda.getWeekOfWeekyear());
                                value = value+1;
                                activitesPerWeek.put(joda.getWeekOfWeekyear(), value);
                            }else{
                                activitesPerWeek.put(joda.getWeekOfWeekyear(),1);
                            }
                        }
                    }

                    /*week = new DateTime(myActivities.get(0).getDate()).getWeekOfWeekyear() - 1;
                    for (int i = 0; i < myActivities.size(); i++)
                    {
                        DateTime joda = new DateTime(myActivities.get(i).getDate());

                        if (joda.getWeekOfWeekyear() != week)
                        {
                            weekPosition.put(joda.getWeekOfWeekyear(), i);
                            week = joda.getWeekOfWeekyear();
                        }
                        if (joda.getWeekOfWeekyear() == week)
                        {
                            if (activitesPerWeek.containsKey(joda.getWeekOfWeekyear() + 1))
                            {
                                int value = activitesPerWeek.get(joda.getWeekOfWeekyear() + 1);
                                value = value + 1;
                                activitesPerWeek.put(joda.getWeekOfWeekyear() + 1, value);
                            } else
                            {
                                activitesPerWeek.put(joda.getWeekOfWeekyear() + 1, 1);
                            }
                        }
                    }*/
                    Log.e("APIRESPONSEHANDLER", "size: " + String.valueOf(activitesPerWeek.size()));
                    listView.setAdapter(new CustomAdapter(activity, myActivities));

                } else
                {
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
        String newSubType = getActivityName(subType);
        if(!newSubType.equals("No name")){
            subType = newSubType;
        }
        realmActivity.setSubType(subType);

        realm.commitTransaction();

        if(hasBooking){
            JsonObject bookingJsonObj = object.get("booking").getAsJsonObject();
            booking = getBookingObj(bookingJsonObj);
            RealmResults<Booking> bookings = realm.where(Booking.class)
                    .equalTo("id", booking.getId())
                    .findAll();

            realmActivity.getBookings().add(bookings.first());
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

        if(centerNamesMap.containsKey(center)){
            center = centerNamesMap.get(center);
        }

        realm.commitTransaction();
        if(hasClass){
            JsonObject classJsonObj = object.get("class").getAsJsonObject();
            myClass = getClassObj(classJsonObj);
            RealmResults<Klass> classes = realm.where(Klass.class)
                    .equalTo("id", myClass.getId())
                    .findAll();

            realmBooking.getKlasses().add(classes.first());
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

    public void getCenterNames()
    {
        try
        {
            JsonObject result = Ion.with(activity).load(centersURL).asJsonObject().get();
            Log.e("Result", "Result: " + result);
            JsonArray jsonRegionsArray = result.getAsJsonArray("regions");
            JsonArray jsonCentersArray = new JsonArray();

            for (JsonElement element : jsonRegionsArray) {   //loopar regions. för varje region
                JsonObject regionObject = element.getAsJsonObject(); //en region ex sthlm
                jsonCentersArray.addAll(regionObject.get("centers").getAsJsonArray()); //lägg till alla centers för ex sthlm
            }

            for (JsonElement centerElement : jsonCentersArray) {    //loopar centers
                realm.beginTransaction();
                Center realmCenter = realm.createObject(Center.class);

                JsonObject center = centerElement.getAsJsonObject();
                int centerId = center.get("id").getAsInt();
                if(realmCenter.getId() == 0){
                    realmCenter.setId(centerId);
                }
                String centerName = center.get("name").getAsString();
                realmCenter.setName(centerName);
                Boolean availableForOnlineBooking = center.get("availableForOnlineBooking").getAsBoolean();
                realmCenter.setAvailableForOnlineBooking(availableForOnlineBooking);
                String description = center.get("description").getAsString();
                realmCenter.setDescription(description);
                int filterId = center.get("filterId").getAsInt();
                realmCenter.setFilterId(filterId);
                Boolean isElixia = center.get("isElixia").getAsBoolean();
                realmCenter.setIsElixia(isElixia);
                double lati = center.get("lat").getAsDouble();
                realmCenter.setLati(lati);
                double longi = center.get("long").getAsDouble();
                realmCenter.setLongi(longi);
                int regionId = center.get("regionId").getAsInt();
                realmCenter.setRegionId(regionId);
                String url = center.get("url").getAsString();
                realmCenter.setUrl(url);
                realm.commitTransaction();
                LatLng kord = new LatLng(lati, longi);
                markers.put(centerName, new CenterInfo(url, kord));
                urls.put(centerName, url);
                centerNamesMap.put(String.valueOf(centerId), centerName);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            Log.e(TAG, "Could not get center names");
            e.printStackTrace();
        }
    }

    public ArrayList<ClassType> getClassTypes()
    {
        try {
            JsonObject result = Ion.with(activity).load(classTypesURL).asJsonObject().get();
            JsonArray jsonArray = result.getAsJsonArray("classTypes");
            for (JsonElement element : jsonArray) {

                classTypes.add(getClassTypeObj(element));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e(TAG, "Could not get ClassTypes");
            e.printStackTrace();
        }

        return classTypes;
    }

    private ClassType getClassTypeObj(JsonElement element)
    {
        JsonObject object = element.getAsJsonObject();

        ArrayList<Profile> stats = null;
        String description = object.get("description").getAsString();
        String videoURL = object.get("videoUrl").getAsString();
        String name = object.get("name").getAsString();
        String id = object.get("id").getAsString();
        JsonArray profileJsonObj = object.get("profile").getAsJsonArray();

        stats = getProfile(profileJsonObj);

        return new ClassType(description, id, name, stats, videoURL);
    }

    private ArrayList<Profile> getProfile(JsonArray jsonArray)
    {
        ArrayList<Profile> profileArray = new ArrayList<>();
        for (JsonElement element : jsonArray)
        {
            JsonObject object = element.getAsJsonObject();
            String id = object.get("id").getAsString();
            String name = object.get("name").getAsString();
            int value = object.get("value").getAsInt();

            profileArray.add(new Profile(id, name, value));
        }

        return profileArray;
    }

    public void clear(final StickyListHeadersListView listView)
    {
        myActivities.clear();
        getAllActivities(listView);
    }

    public void getCenterLocations()
    {
        Ion.with(activity).load(centersURL).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (e == null)
                {
                    JsonArray jsonRegionsArray = result.getAsJsonArray("regions");
                    JsonArray jsonCentersArray = new JsonArray();
                    for (JsonElement element : jsonRegionsArray)
                    {   //loopar regions. för varje region
                        JsonObject regionObject = element.getAsJsonObject(); //en region ex sthlm

                        jsonCentersArray.addAll(regionObject.get("centers").getAsJsonArray()); //lägg till alla centers för ex sthlm
                    }

                    for (JsonElement centerElement : jsonCentersArray)
                    {    //loopar centers
                        JsonObject center = centerElement.getAsJsonObject();
                        String centerId = center.get("id").getAsString();
                        String centerName = center.get("name").getAsString();
                        double lati = center.get("lat").getAsDouble();
                        double longi = center.get("long").getAsDouble();
                        LatLng kord = new LatLng(lati, longi);

                        markers2.put(centerName, kord);


                        centerNamesMap.put(centerId, centerName);
                    }
                } else
                {
                    Log.e("Info", "Could not get center names");
                }
            }
        });
    }

    public HashMap<String, LatLng> getMarkers() {
        return markers2;
    }

    public String getActivityName(String subType)
    {
        String name = "No name";
        try {
            JsonObject result = Ion.with(activity).load(activityTypesURL + subType).asJsonObject().get();
            name = result.get("name").getAsString();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            return "No name";
        }

        return name;
    }

}
