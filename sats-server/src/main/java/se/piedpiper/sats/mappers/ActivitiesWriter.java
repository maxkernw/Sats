package se.piedpiper.sats.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.TreeSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.piedpiper.sats.models.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class ActivitiesWriter implements MessageBodyWriter<TreeSet<Activity>>
{
	private Gson gson;

	public ActivitiesWriter()
	{
		gson = new GsonBuilder().registerTypeAdapter(TreeSet.class, new TypesAdapter()).serializeNulls().create();
	}

	// MessageBodyWriter
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return type.isAssignableFrom(TreeSet.class);
	}

	@Override
	public long getSize(TreeSet<Activity> types, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType)
	{
		return 0;
	}

	@Override
	public void writeTo(TreeSet<Activity> types, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream)
			throws IOException, WebApplicationException
	{
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(types, TreeSet.class, writer);
		}
	}

	private static final class TypesAdapter implements JsonSerializer<TreeSet<Activity>>
	{

		@Override
		public JsonElement serialize(TreeSet<Activity> activities, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context)
		{
			// The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			// An array to hold all Types
			final JsonArray jsonArrayForActivity = new JsonArray();

			for(Activity activity : activities)
			{
				// An object to hold all information~ about the Types one by
				// one
				final JsonObject jsonObjectForActivity = new JsonObject();
				
				if(activity.getBooking() != null){
					final JsonObject theClass = new JsonObject();
					theClass.add("centerId", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("centerFilterId", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("classTypeId", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("durationInMinutes", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("id", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("instructorId", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("name", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("startTime", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("bookedPersonsCount", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("maxPersonsCount", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("regionId", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					theClass.add("waitingListCount", new JsonPrimitive(activity.getBooking().getAClass().getCenterId()));
					JsonArray classCategories = new JsonArray();
					for(Integer integer: activity.getBooking().getAClass().getClassCategories()){
						classCategories.add(new JsonPrimitive(integer));
					}
					theClass.add("waitingListCount", classCategories);
					
					
					final JsonObject theBooking = new JsonObject();
					theBooking.add("status", new JsonPrimitive(activity.getBooking().getStatus()));
					theBooking.add("class", theClass);
					theBooking.add("center", new JsonPrimitive(activity.getBooking().getCenter()));
					theBooking.add("id", new JsonPrimitive(activity.getBooking().getId()));
					theBooking.add("positionInQueue", new JsonPrimitive(activity.getBooking().getPositionInQueue()));
					jsonObjectForActivity.add("booking", theBooking);		
				}else{
					jsonObjectForActivity.add("booking", JsonNull.INSTANCE);
				}
					jsonObjectForActivity.add("comment", new JsonPrimitive(activity.getComment()));
					jsonObjectForActivity.add("date", new JsonPrimitive(activity.getDate().toString()));
					jsonObjectForActivity.add("distanceInKm", new JsonPrimitive(activity.getDistanceInKm()));
					jsonObjectForActivity.add("durationInMinutes", new JsonPrimitive(activity.getDurationInMinutes()));
					jsonObjectForActivity.add("id", new JsonPrimitive(activity.getId()));
					jsonObjectForActivity.add("source", new JsonPrimitive(activity.getSource()));
					jsonObjectForActivity.add("status", new JsonPrimitive(activity.getStatus()));
					jsonObjectForActivity.add("subType", new JsonPrimitive(activity.getSubType()));
					jsonObjectForActivity.add("type", new JsonPrimitive(activity.getType()));
					
					// Adding the object to the array
					jsonArrayForActivity.add(jsonObjectForActivity);
			}
			
			// Adding the array to the jsonReturn-object
			jsonToReturn.add("Activities", jsonArrayForActivity);

			return jsonToReturn;
		}
	}
}