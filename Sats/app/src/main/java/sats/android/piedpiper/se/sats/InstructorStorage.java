package sats.android.piedpiper.se.sats;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Osama on 2015-04-24.
 */

public class InstructorStorage
{
    private static final String ERROR = "Error" ;
    private static ArrayList<Instructor> instructorList = new ArrayList<>();
    private static JSONArray jsonInstructors;

    public static ArrayList<Instructor> getInstructors() throws JSONException
    {

        SatsRestClient.get("instructors", null, new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {

                try
                {
                    jsonInstructors = response.getJSONArray("instructors");

                    for(int i = 0; i < jsonInstructors.length(); i++)
                    {
                        String id, name;
                        id = jsonInstructors.getJSONObject(i).getString("id");
                        name = jsonInstructors.getJSONObject(i).getString("name");
                        Instructor instructor = new Instructor(id, name);

                        instructorList.add(instructor);
                    }
                } catch (JSONException e) {
                    Log.e(ERROR, "JSON Error in InstructorStorage: " + e.getStackTrace());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.e(ERROR, "Failed to fetch JSON-data from SATS API");
            }
        });
        return instructorList;
    }

    public static Instructor getInstructor(String id) throws JSONException {
        getInstructors();
        Instructor inst = new Instructor(id, id);
        if(instructorList.contains(inst))
        {
            return inst;
        }
        throw new JSONException("Instructor not found!");
    }
}
