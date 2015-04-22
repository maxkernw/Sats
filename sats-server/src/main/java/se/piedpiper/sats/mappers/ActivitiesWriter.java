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
		gson = new GsonBuilder().registerTypeAdapter(TreeSet.class, new TypesAdapter()).create();
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
		public JsonElement serialize(TreeSet<Activity> types, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context)
		{
			// The Object which will be returned
			final JsonObject jsonToReturn = new JsonObject();
			// An array to hold all Types
			final JsonArray jsonArrayForTypes = new JsonArray();

			for(Activity type : types)
			{
				// An object to hold all information~ about the Types one by
				// one
				final JsonObject jsonObjectForType = new JsonObject();
				
				final JsonObject theClass = new JsonObject();
				try{
				theClass.add("centerId", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("centerFilterId", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("classTypeId", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("durationInMinutes", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("id", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("instructorId", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("name", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("startTime", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("bookedPersonsCount", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("maxPersonsCount", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("regionId", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				theClass.add("waitingListCount", new JsonPrimitive(type.getBooking().getAClass().getCenterId()));
				JsonArray classCategories = new JsonArray();
				for(Integer integer: type.getBooking().getAClass().getClassCategories()){
					classCategories.add(new JsonPrimitive(integer));
				}
				theClass.add("waitingListCount", classCategories);
				
				
				final JsonObject theBooking = new JsonObject();
				theBooking.add("status", new JsonPrimitive(type.getBooking().getStatus()));
				theBooking.add("class", theClass);
				theBooking.add("center", new JsonPrimitive(type.getBooking().getCenter()));
				theBooking.add("id", new JsonPrimitive(type.getBooking().getId()));
				theBooking.add("positionInQueue", new JsonPrimitive(type.getBooking().getPositionInQueue()));
				
				jsonObjectForType.add("booking", theBooking);		
				jsonObjectForType.add("comment", new JsonPrimitive(type.getComment()));
				jsonObjectForType.add("date", new JsonPrimitive(type.getDate().toString()));
				jsonObjectForType.add("distanceInKm", new JsonPrimitive(type.getDistanceInKm()));
				jsonObjectForType.add("durationInMinutes", new JsonPrimitive(type.getDurationInMinutes()));
				jsonObjectForType.add("id", new JsonPrimitive(type.getId()));
				jsonObjectForType.add("source", new JsonPrimitive(type.getSource()));
				jsonObjectForType.add("status", new JsonPrimitive(type.getStatus()));
				jsonObjectForType.add("subType", new JsonPrimitive(type.getSubType()));
				jsonObjectForType.add("type", new JsonPrimitive(type.getType()));
				
				// Adding the object to the array
				jsonArrayForTypes.add(jsonObjectForType);
				
				}catch(NullPointerException e){
					System.out.println(type.toString());
				}
			}
			
			// Adding the array to the jsonReturn-object
			jsonToReturn.add("Activities", jsonArrayForTypes);

			return jsonToReturn;
		}
	}
}