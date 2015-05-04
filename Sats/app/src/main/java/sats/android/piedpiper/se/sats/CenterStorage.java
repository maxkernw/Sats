package sats.android.piedpiper.se.sats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CenterStorage {
    private static ArrayList<Center> centerList = new ArrayList<>();
    private static ArrayList<Center> allCenters = new ArrayList<>();
    private static ArrayList<Region> regionList = new ArrayList<>();
    private static JSONArray jsonRegions, centersOfRegion;
    private static JSONObject jsonCenter;

    public static void populateCenters(Context context) throws JSONException
    {
        SatsRestClient.get("centers", null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try
                {
                    jsonRegions = response.getJSONArray("regions");

                    for (int i = 0; i < jsonRegions.length(); i++)
                    {
                        jsonCenter = jsonRegions.getJSONObject(i);
                        centersOfRegion = jsonCenter.getJSONArray("centers");

                        for (int j = 0; j < centersOfRegion.length(); j++)
                        {
                            boolean availableForOnlineBooking, isElixia;
                            String description, name, url;
                            int filterId, id, lati, longi, regionId;

                            jsonCenter = centersOfRegion.getJSONObject(j);

                            availableForOnlineBooking = jsonCenter.getBoolean("availableForOnlineBooking");
                            description = jsonCenter.getString("description");
                            filterId = jsonCenter.getInt("filterId");
                            id = jsonCenter.getInt("id");
                            isElixia = jsonCenter.getBoolean("isElixia");
                            lati = jsonCenter.getInt("lat");
                            longi = jsonCenter.getInt("long");
                            name = jsonCenter.getString("name");
                            regionId = jsonCenter.getInt("regionId");
                            url = jsonCenter.getString("url");

                            Center center = new Center(availableForOnlineBooking, isElixia, description, name, url, filterId, id, lati, longi, regionId);
                            centerList.add(center);
                        }
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                Log.e("ERROR", "Could not get HTTPresponse");
            }

        });


    }

    public static ArrayList<Center> getCenters() {
        Log.i("Info", String.valueOf(centerList.size()));
        return centerList;
    }

    public final Center getCenter(int centerId) {
        for (Center center : centerList) {
            if(center.getId() == centerId)
                return center;
        }
        return null;
    }
}