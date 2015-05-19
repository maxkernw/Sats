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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import sats.android.piedpiper.se.sats.holders.ActivityHolder;
import sats.android.piedpiper.se.sats.holders.BookedActivityHolder;
import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Center;
import sats.android.piedpiper.se.sats.models.Class;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class IonRequester {

    private static JsonArray jsonActivities;
    private static ArrayList<Activity> ActivitiesList = new ArrayList<>();

    public static int[] weekPosition = new int[53];
    public static int week = 0;
    public static int[] activitesPerWeek = new int[53];
    public static int thisWeek = 0;



    public static StringBuilder sURL =  new StringBuilder("http://80.217.172.201:8080/sats/se/training/activities/?fromDate=20150101&toDate=20160101");
    private static CustomAdapter adapter;
    private static CustomAdapter adapter2;
    public static int activitiesWeek = 0;


    public static void getBooking (final android.app.Activity activity, final StickyListHeadersListView listView){

//        String fromDate = "";
//        String toDate = "";
//
//        fromDate = dateFrom.toString().replaceAll("-", "").substring(0,8);
//        toDate = dateTo.toString().replaceAll("-", "").substring(0,8);
//        Log.e("fromDate", fromDate);
//        Log.e("toDate", toDate);
//       // DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
//        sURL =  new StringBuilder("http://80.217.172.201:8080/sats/se/training/activities/?");
//        sURL.append("fromDate=" + fromDate);
//        sURL.append("&toDate=" + toDate);
//        Log.e("SURL", "url: " + sURL);
        Ion.with(activity.getApplicationContext()).load(sURL.toString()).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (result == null) {
                    DateTime date = DateTime.now();
                    Log.e("Info", "could not get json" + date.toString());
                } else {
                    jsonActivities = result.getAsJsonArray("Activities");

                    for (int i = 0; i < jsonActivities.size(); i++) {

                        JsonObject booking = null;
                        if (jsonActivities.get(i).getAsJsonObject().has("booking")) {
                            booking = jsonActivities.get(i).getAsJsonObject().get("booking").getAsJsonObject();
                        }

                        Booking theBooking = null;

                        if (booking != null) {

                            String centerId = "", centerFilterId, classTypeId, id, instructorId, name;
                            int durationInMinutes, bookedPersonsCount, maxPersonsCount;
                            String startTime;
                            ArrayList<Integer> waitingListCount = null;

                            JsonObject theClass = booking.get("class").getAsJsonObject();
                            centerId = theClass.get("centerId").getAsString();
                            centerFilterId = theClass.get("centerFilterId").getAsString();
                            classTypeId = theClass.get("classTypeId").getAsString();
                            durationInMinutes = theClass.get("durationInMinutes").getAsInt();
                            id = theClass.get("id").getAsString();
                            instructorId = theClass.get("instructorId").getAsString();
                            name = theClass.get("name").getAsString();
                            bookedPersonsCount = theClass.get("bookedPersonsCount").getAsInt();
                            maxPersonsCount = theClass.get("maxPersonsCount").getAsInt();
                            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
                            DateTime date = new DateTime(2012,12,25,0,0,0);

                            if (theClass.has("startTime")) {
                                startTime = theClass.get("startTime").getAsString();


                                date = format.parseDateTime(startTime);


                            }

                            Class trainingClass = new Class(centerId, centerFilterId, classTypeId,
                                    durationInMinutes, id, instructorId, name, date, bookedPersonsCount, maxPersonsCount, 0 ,waitingListCount);

                            String status, center, BookingId;
                            int positionInQueue;

                            status = booking.get("status").getAsString();
                            center = booking.get("center").getAsString();
                            BookingId = booking.get("id").getAsString();
                            positionInQueue = booking.get("positionInQueue").getAsInt();
                            theBooking = new Booking(status, trainingClass, center, BookingId, positionInQueue);
                        }

                        String comment = jsonActivities.get(i).getAsJsonObject().get("comment").getAsString();
                        String id = jsonActivities.get(i).getAsJsonObject().get("id").getAsString();
                        String source = jsonActivities.get(i).getAsJsonObject().get("source").getAsString();
                        String type = jsonActivities.get(i).getAsJsonObject().get("type").getAsString();
                        String status = jsonActivities.get(i).getAsJsonObject().get("status").getAsString();
                        String subType = jsonActivities.get(i).getAsJsonObject().get("subType").getAsString();
                        Log.e("Subtype", "Subtype: " + subType);
                        String daten = jsonActivities.get(i).getAsJsonObject().get("date").getAsString();

                        int distance = 0;
                        int durationInMinutes = jsonActivities.get(i).getAsJsonObject().get("durationInMinutes").getAsInt();

                        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

                        Log.e("Log", "Date" + daten);
                        DateTime date = format.parseDateTime(daten);


                        Activity active = new Activity(theBooking, comment, date.toDateTime(), distance,
                                durationInMinutes, id, source, status,
                                subType, type);

                        ActivitiesList.add(active);
                        Set<Activity> hs = new HashSet<>();
                        hs.addAll(ActivitiesList);
                        ActivitiesList.clear();
                        ActivitiesList.addAll(hs);
                        Collections.sort(ActivitiesList);


                        if(date.getWeekOfWeekyear() != week){
                            weekPosition[date.getWeekOfWeekyear()] = i;
                            Log.e("DENNA VECKAN: ", String.valueOf(date.getWeekOfWeekyear()));
                            week = date.getWeekOfWeekyear();
                        }
                        if(date.getWeekOfWeekyear() == week){
                            activitesPerWeek[date.getWeekOfWeekyear()]++;
                        }




                    }




                    adapter = new CustomAdapter(activity, ActivitiesList);
                    activitiesWeek = ActivitiesList.size();
                    for(int i = 0; i < 53; i++)
                    {
                        Log.e("Weeks per derp", "Activiperweek " + IonRequester.activitesPerWeek[i]);
                    }

                    //adapter = new CustomAdapter(activity, ActivitiesList);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    public static void clear(android.app.Activity activity, final StickyListHeadersListView listView){
        ActivitiesList.clear();
    }

    private static JsonObject jsonCenter;
    public static void getClass (android.app.Activity activity,final BookedActivityHolder holder, String id)
    {
        Ion.with(activity.getApplicationContext()).load("https://api2.sats.com/v1.0/se/centers/" + id).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (result == null)
                {
                    Log.e("Info", "could not get json");
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
                    holder.center.setText(center.name);
                }
            }
        });
    }

    public static void getName(android.app.Activity activity,final ActivityHolder holder, String subType)
    {
        Ion.with(activity.getApplicationContext()).load("http://192.168.68.226:8080/sats-server/se/training/activities/" + subType).asJsonObject().setCallback(new FutureCallback<JsonObject>()
        {
            @Override
            public void onCompleted(Exception e, JsonObject result)
            {
                if (result == null)
                {
                    Log.e("Info", "could not get json");
                }
                else
                {
                    holder.title.setText(result.get("name").getAsString());
                }

            }
        });
    }





}

