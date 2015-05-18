package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import sats.android.piedpiper.se.sats.holders.ActivityHolder;
import sats.android.piedpiper.se.sats.holders.BookedActivityHolder;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Center;
import sats.android.piedpiper.se.sats.models.Klass;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public final class IonRequester
{

    private static JsonArray jsonActivities;
    private static ArrayList<Activity> ActivitiesList = new ArrayList<>();
    public static final String sURL = "http://80.217.172.201:8080/sats/se/training/activities/";
    private static CustomAdapter adapter;
    private static Realm realm;

    public static void getBooking (final android.app.Activity activity, final StickyListHeadersListView listView)
    {
        Ion.with(activity.getApplicationContext()).load(sURL + "?fromDate=20121210&toDate=20160521").asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {

            @Override
            public void onCompleted(Exception e, JsonObject result) {

                if(result == null){
                    Log.e("Error", "Could not get JSON-data!" );
                }else{
                    Realm.deleteRealmFile(activity.getApplicationContext());
                    realm = Realm.getInstance(activity.getApplicationContext());

                    jsonActivities = result.getAsJsonArray("Activities");

                    for(int i = 0; i < jsonActivities.size(); i++)
                    {

                        JsonObject JSONbooking = null;

                        if(jsonActivities.get(i).getAsJsonObject().has("booking"))
                        {
                            JSONbooking = jsonActivities.get(i).getAsJsonObject().get("booking").getAsJsonObject();
                        }

                        Booking theBooking = null;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        if(JSONbooking != null)
                        {
                            String centerId = "", centerFilterId, classTypeId, id, instructorId, name;
                            int durationInMinutes, bookedPersonsCount, maxPersonsCount;
                            String startTime;
                            ArrayList<Integer> waitingListCount = null;
                            realm.beginTransaction();
                            Klass realmKlass = realm.createObject(Klass.class);

                            JsonObject theClass = JSONbooking.get("class").getAsJsonObject();
                            centerId = theClass.get("centerId").getAsString();
                            realmKlass.setCenterId(centerId);
                            centerFilterId = theClass.get("centerFilterId").getAsString();
                            realmKlass.setCenterFilterId(centerFilterId);
                            classTypeId = theClass.get("classTypeId").getAsString();
                            realmKlass.setClassTypeId(classTypeId);
                            durationInMinutes = theClass.get("durationInMinutes").getAsInt();
                            realmKlass.setDurationInMinutes(durationInMinutes);
                            id = theClass.get("id").getAsString();
                            realmKlass.setId(id);
                            instructorId = theClass.get("instructorId").getAsString();
                            realmKlass.setInstructorId(instructorId);
                            name = theClass.get("name").getAsString();
                            realmKlass.setName(name);
                            bookedPersonsCount = theClass.get("bookedPersonsCount").getAsInt();
                            realmKlass.setBookedPersonsCount(bookedPersonsCount);
                            maxPersonsCount = theClass.get("maxPersonsCount").getAsInt();
                            realmKlass.setMaxPersonsCount(maxPersonsCount);
                            Date date = new Date();


                            if(theClass.has("startTime"))
                            {
                                startTime = theClass.get("startTime").getAsString();
                                try {
                                    date = format.parse(startTime);
                                    realmKlass.setStartTime(date);
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            realm.commitTransaction();

                            Klass trainingKlass = new Klass( centerId,  centerFilterId,  classTypeId,
                                    durationInMinutes,  id,  instructorId,  name, date,  bookedPersonsCount,  maxPersonsCount, 0, waitingListCount);

                            String status, center,bookingId;
                            int positionInQueue;
                            realm.beginTransaction();
                            Booking realmBooking = realm.createObject(Booking.class);

                            status = JSONbooking.get("status").getAsString();
                            realmBooking.setStatus(status);
                            center = JSONbooking.get("center").getAsString();
                            realmBooking.setCenter(center);
                            bookingId = JSONbooking.get("id").getAsString();
                            realmBooking.setId(bookingId);
                            positionInQueue = JSONbooking.get("positionInQueue").getAsInt();
                            realmBooking.setPositionInQueue(positionInQueue);
                            theBooking = new Booking(status, trainingKlass, center, bookingId, positionInQueue);
                            realmBooking.getKlasses().add(realmKlass);
                            realm.commitTransaction();
                        }

                        realm.beginTransaction();
                        Activity realmActivity = realm.createObject(Activity.class);

                        String comment = jsonActivities.get(i).getAsJsonObject().get("comment").getAsString();
                        realmActivity.setComment(comment);
                        String id = jsonActivities.get(i).getAsJsonObject().get("id").getAsString();
                        realmActivity.setId(id);
                        String source = jsonActivities.get(i).getAsJsonObject().get("source").getAsString();
                        realmActivity.setSource(source);
                        String type = jsonActivities.get(i).getAsJsonObject().get("type").getAsString();
                        realmActivity.setType(type);
                        String status = jsonActivities.get(i).getAsJsonObject().get("status").getAsString();
                        realmActivity.setStatus(status);
                        String subType = jsonActivities.get(i).getAsJsonObject().get("subType").getAsString();
                        realmActivity.setSubType(subType);
                        Log.e("Subtype", "Subtype: " + subType);
                        String daten = jsonActivities.get(i).getAsJsonObject().get("date").getAsString();

                        Date date = null;
                        try {
                            date = format.parse(daten);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        realmActivity.setDate(date);

                        int distance = 0;
                        int durationInMinutes = jsonActivities.get(i).getAsJsonObject().get("durationInMinutes").getAsInt();
                        realmActivity.setDurationInMinutes(durationInMinutes);

                        Activity active = new Activity(theBooking, comment, date, distance,
                                durationInMinutes,  id,  source,  status,
                                subType,  type);

                        ActivitiesList.add(active);
                        realm.commitTransaction();
                    }

                    realm.close();
                    Set<Activity> hs = new HashSet<>();
                    hs.addAll(ActivitiesList);
                    ActivitiesList.clear();
                    ActivitiesList.addAll(hs);
                    adapter = new CustomAdapter(activity, ActivitiesList);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    public static void clear(android.app.Activity activity, final StickyListHeadersListView listView)
    {
        ActivitiesList.clear();
        getBooking(activity, listView);
    }

    private static JsonObject jsonCenter;

    public static void getCenter(android.app.Activity activity, final BookedActivityHolder holder, String id)
    {
        Ion.with(activity.getApplicationContext()).load("https://api2.sats.com/v1.0/se/centers/" + id).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (result == null)
                {
                    Log.e("Error", "Could not get JSON-data!");
                }
                else
                {
                    jsonCenter = result.getAsJsonObject("center");

                    boolean availableForOnlineBooking, isElixia;
                    String description, name, url;
                    int filterId, id, lati, longi, regionId;

                    availableForOnlineBooking = jsonCenter.get("availableForOnlineBooking").getAsBoolean();
                    description = jsonCenter.get("description").getAsString();
                    filterId = jsonCenter.get("filterId").getAsInt();
                    id = jsonCenter.get("id").getAsInt();
                    isElixia = jsonCenter.get("isElixia").getAsBoolean();
                    lati = jsonCenter.get("lat").getAsInt();
                    longi = jsonCenter.get("long").getAsInt();
                    name = jsonCenter.get("name").getAsString();
                    regionId = jsonCenter.get("regionId").getAsInt();
                    url = jsonCenter.get("url").getAsString();
                    Center center = new Center(availableForOnlineBooking, isElixia, description, name, url, filterId, id, lati, longi, regionId);
                    holder.center.setText(center.getName());
                }
            }
        });
    }

    public static void getName(android.app.Activity activity,final ActivityHolder holder, String subType)
    {
        Ion.with(activity.getApplicationContext()).load(sURL + subType).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (result == null)
                {
                    Log.e("Error", "Could not get JSON-data!");
                }
                else
                {
                    holder.title.setText(result.get("name").getAsString());
                }
            }
        });
    }
}