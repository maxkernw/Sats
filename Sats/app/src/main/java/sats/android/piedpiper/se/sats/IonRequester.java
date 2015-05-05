package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import sats.android.piedpiper.se.sats.models.Activity;
import sats.android.piedpiper.se.sats.models.Booking;
import sats.android.piedpiper.se.sats.models.Klass;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Steffe on 15-04-29.
 */
public class IonRequester {

    private static JsonArray jsonActivities;
    private static ArrayList<Activity> ActivitiesList = new ArrayList<>();
    public static final String sURL = "http://192.168.68.226:8080/sats-server/se/training/activities/?fromDate=20121210&toDate=20160521";
    private static CustomAdapter adapter;

    public static void getBooking (final android.app.Activity activity, final StickyListHeadersListView listView){
        Ion.with(activity.getApplicationContext()).load(sURL).asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(result == null){
                    Log.e("Testa", "could not get json");
                }else{
                    Log.e("Testa", "got all json without problem");
                    jsonActivities = result.getAsJsonArray("Activities");

                    for(int i = 0; i < jsonActivities.size(); i++)
                    {

                        JsonObject booking = null;
                        if(jsonActivities.get(i).getAsJsonObject().has("booking")){
                            //Log.e("ohaa",String.valueOf(i));
                            booking = jsonActivities.get(i).getAsJsonObject().get("booking").getAsJsonObject();
                        }else{
                            //Log.e("ohaa","neeej");
                        }

                        Booking bookingen = null;

                        if(booking != null){

                            String centerId = "", centerFilterId, classTypeId, id, instructorId, name, regionId;
                            int durationInMinutes, bookedPersonsCount, maxPersonsCount;
                            String startTime;
                            ArrayList<Integer> waitingListCount = null;

                            //JSONObject klass = booking.getJSNObject("class");
                            JsonObject klass = booking.get("class").getAsJsonObject();
                            centerId = klass.get("centerId").getAsString();
                            //}
                            centerFilterId = klass.get("centerFilterId").getAsString();
                            //getString("centerFilterId");
                            classTypeId = klass.get("classTypeId").getAsString();
                            //getString("classTypeId");
                            durationInMinutes = klass.get("durationInMinutes").getAsInt();
                            //getInt("durationInMinutes");
                            id = klass.get("id").getAsString();
                            //getString("id");
                            instructorId = klass.get("instructorId").getAsString();
                            //getString("instructorId");
                            name = klass.get("name").getAsString();
                            //getString("name");
                            bookedPersonsCount = klass.get("bookedPersonsCount").getAsInt();
                            //getInt("bookedPersonsCount");
                            maxPersonsCount = klass.get("maxPersonsCount").getAsInt();
                            //getInt("maxPersonsCount");
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            Date date = null;
                            if(klass.has("startTime")) {
                                startTime = klass.get("startTime").getAsString();
                                //getString("startTime");

                                try {
                                    date = format.parse(startTime);
                                } catch (ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            Log.e("test", name);

                            Klass klassen = new Klass( centerId,  centerFilterId,  classTypeId,
                                    durationInMinutes,  id,  instructorId,  name, date,  bookedPersonsCount,  maxPersonsCount, waitingListCount);


                            String status, center,BookingId;
                            int positionInQueue;

                            status = booking.get("status").getAsString();
                            //getString("status");
                            center = booking.get("center").getAsString();
                            //getString("center");
                            BookingId = booking.get("id").getAsString();
                            //getString("id");
                            positionInQueue = booking.get("positionInQueue").getAsInt();
                            //getInt("positionInQueue");
                            bookingen = new Booking(status, klassen, center, BookingId, positionInQueue);

                        }

                        String comment = jsonActivities.get(i).getAsJsonObject().get("comment").getAsString();
                        //getJSONObject(i).getString("comment");
                        String id = jsonActivities.get(i).getAsJsonObject().get("id").getAsString();
                        //getJSONObject(i).getString("id");
                        String source = jsonActivities.get(i).getAsJsonObject().get("source").getAsString();
                        //getJSONObject(i).getString("source");
                        String type = jsonActivities.get(i).getAsJsonObject().get("type").getAsString();
                        //getJSONObject(i).getString("type");
                        String status = jsonActivities.get(i).getAsJsonObject().get("status").getAsString();
                        //getJSONObject(i).getString("status");
                        String subType = jsonActivities.get(i).getAsJsonObject().get("subType").getAsString();
                        //getJSONObject(i).getString("subType");
                        String daten = jsonActivities.get(i).getAsJsonObject().get("date").getAsString();
                        //getJSONObject(i).getString("date");

                        java.util.Date date2;
                        int distance = 0;
                        //int distance = jsonActivities.get(i).getAsJsonObject().get("distance").getAsInt();
                        //getJSONObject(i).getInt("distance");
                        int durationInMinutes = jsonActivities.get(i).getAsJsonObject().get("durationInMinutes").getAsInt();
                        //getJSONObject(i).getInt("durationInMinutes");

                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                        Date date = null;
                        try {
                            date = format.parse(daten);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }

                        Activity active = new Activity(bookingen, comment, date, distance,
                                durationInMinutes,  id,  source,  status,
                                subType,  type);

                        ActivitiesList.add(active);

                        //Log.e("test", active.status);
                    }

                    //
                    //Osamas kod för centers och instructors
                    //
                    adapter = new CustomAdapter(activity, ActivitiesList);
                    listView.setAdapter(adapter);
                    Log.e("Testa", "completed");

                }


            }
        });
    }

    public static void clear(android.app.Activity activity, final StickyListHeadersListView listView){
        ActivitiesList.clear();
        getBooking(activity, listView);
    }
}

