package sats.android.piedpiper.se.sats.handlers;


import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

import sats.android.piedpiper.se.sats.models.ClassType;
import sats.android.piedpiper.se.sats.models.Profile;

public class SatsAPIResponseHandler {

    public static final String ClassTypesURL = "https://api2.sats.com//v1.0//se//classTypes";
    public static final String CentersURL = "https:api2.sats.com/v1.0/se/centers";
    private static JsonArray jsonClassTypes;
    private static JsonArray jsonCenters;

    public static HashMap<String, ClassType> KlassTyper = new HashMap<>();
    public static HashMap<String,String> CenterNames = new HashMap<String, String>();

    public static void getClassType (android.app.Activity activity)
    {
        Ion.with(activity.getApplicationContext()).load("https://api2.sats.com//v1.0//se//classTypes").asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (result == null) {
                    Log.e("Info", "could not get json");
                } else {
                    String description, name, url;
                    String ClassId;
                    jsonClassTypes = result.getAsJsonArray("classTypes");
                    JsonElement Profiless;
                    JsonArray Profile;
                    sats.android.piedpiper.se.sats.models.Profile profilen;

                    for (int i = 0; i < jsonClassTypes.size(); i++) {
                        description = jsonClassTypes.get(i).getAsJsonObject().get("description").getAsString();
                        name = jsonClassTypes.get(i).getAsJsonObject().get("name").getAsString();
                        ClassId = jsonClassTypes.get(i).getAsJsonObject().get("id").getAsString();

                        url = jsonClassTypes.get(i).getAsJsonObject().get("videoUrl").getAsString();

                        Profiless = jsonClassTypes.get(i).getAsJsonObject().get("profile");
                        Profile = Profiless.getAsJsonArray();
                        ArrayList<sats.android.piedpiper.se.sats.models.Profile> stats = new ArrayList<Profile>();

                        //for (int b = 0; i < Profile.size(); i++) {
                        for(JsonElement element: Profile){

                            //String TypeId = Profile.get(b).getAsJsonObject().get("id").getAsString();
                            String TypeId = element.getAsJsonObject().get("id").getAsString();
                            String TypeName = element.getAsJsonObject().get("name").getAsString();
                            int value = element.getAsJsonObject().get("value").getAsInt();
                            Profile profilo = new Profile(TypeId,TypeName, value);

                            stats.add(profilo);

                        }

                        ClassType klassTypen = new ClassType(description, ClassId, name, stats, url);

                        KlassTyper.put(ClassId, klassTypen);


                    }


                }
            }
        });
    }

    public static void getCenterNames(android.app.Activity activity)
    {
        Ion.with(activity.getApplicationContext()).load("https://api2.sats.com/v1.0/se/centers/").asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if (result == null) {
                    Log.e("Info", "could not get json");
                } else {
                    jsonCenters = result.getAsJsonArray("centers");
                    for (int i = 0; i < jsonCenters.size(); i++) {
                        String centerName = jsonCenters.get(i).getAsJsonObject().get("name").getAsString();
                        String centerID = jsonCenters.get(i).getAsJsonObject().get("id").getAsString();
                        CenterNames.put(centerID, centerName);
                    }
                }

            }
        });
    }

    public static String findCenterNameById(String centerID){
        return CenterNames.get(centerID);
    }
}
