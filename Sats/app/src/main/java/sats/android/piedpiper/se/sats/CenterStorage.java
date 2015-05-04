package sats.android.piedpiper.se.sats;

import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import sats.android.piedpiper.se.sats.models.Center;
import sats.android.piedpiper.se.sats.models.Region;

public final class CenterStorage
{
    private static final String ERROR = "Error";
    private static final String INFO = "Information";
    private static ArrayList<Center> centerList = new ArrayList<>();
    private static ArrayList<Center> allCenters = new ArrayList<>();
    private static ArrayList<Region> regionList = new ArrayList<>();
    private static JSONArray jsonRegions, centersOfRegion;
    private static JSONObject jsonCenter;
    private static Region region;

    public static ArrayList<Center> getCenters() throws JSONException
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
//                            centerList.clear();
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
//                        region = new Region(centerList);
//                        regionList.add(region);

                        /*for(int x = 0; x < regionList.size(); x++)
                        {
                            for(int y = 0; y < regionList.get(x).getCenterList().size(); y++)
                            {
                                allCenters.add(regionList.get(x).getCenterList().get(y));
                            }
                        }*/
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
        Log.i(INFO, String.valueOf(centerList.size()));

        return centerList;
    }
}