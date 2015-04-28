package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Osama on 2015-04-24.
 */

public class RegionStorage
{
    private static final String ERROR = "Error";
    private static final String INFO = "Information";
    private static ArrayList<Center> centerList = new ArrayList<>();
    private static ArrayList<Center> allCenters = new ArrayList<>();
    private static ArrayList<Region> regionList = new ArrayList<>();
    private static JSONArray jsonRegions, centersOfRegion;
    private static JSONObject jsonCenter;
    private static Region region;

    public static ArrayList<Region> getRegions() throws JSONException
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
                        region = new Region(centerList);
                        regionList.add(region);
                        Log.i(INFO, "regionList: " + regionList.size());
                    }
                } catch (JSONException e)
                {
                    Log.e(ERROR, "JSON Error in InstructorStorage: " + e.getStackTrace());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(ERROR, "Failed to fetch JSON-data from SATS API");
            }
        });

        Log.i(INFO, "Träder in i forloopen: " + regionList.size());
        Log.i(INFO, "Träder inte in i forloopen: " + centerList.size());
        /*for (int i = 0; i < regionList.size(); i++)
        {
            Log.e(ERROR, regionList.get(i).getCenterList().toString());
            for (int j = 0; j < regionList.get(i).getCenterList().size(); j++)
            {
                Log.e(ERROR, regionList.get(i).getCenterList().get(j).toString());
                allCenters.add(regionList.get(i).getCenterList().get(j));
            }
        }*/
        return null;
    }
}