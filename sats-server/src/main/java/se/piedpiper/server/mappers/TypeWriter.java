package se.piedpiper.server.mappers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import se.piedpiper.server.models.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class TypeWriter implements MessageBodyWriter<Type>
{
	private Gson gson;

	public TypeWriter()
	{
		gson = new GsonBuilder().registerTypeAdapter(Type.class, new TypeAdapter()).create();
	}

	@Override
	public boolean isWriteable(Class<?> type,
			java.lang.reflect.Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		// TODO Auto-generated method stub
		return type.isAssignableFrom(Type.class);
	}

	@Override
	public long getSize(Type t, Class<?> type,
			java.lang.reflect.Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeTo(Type t, Class<?> type,
			java.lang.reflect.Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		// TODO Auto-generated method stub
		try(final JsonWriter writer = new JsonWriter(new OutputStreamWriter(entityStream)))
		{
			gson.toJson(t, Type.class, writer);
		}
		
	}
	
	private static final class TypeAdapter implements JsonSerializer<Type>
	{

		@Override
		public JsonElement serialize(Type type,
				java.lang.reflect.Type typeOfSrc,
				JsonSerializationContext context) {
			// TODO Auto-generated method stub
			
			final JsonObject userJson = new JsonObject();
			userJson.add("name", new JsonPrimitive(type.getName()));
			userJson.add("type", new JsonPrimitive(type.getType()));
			userJson.add("subtype", new JsonPrimitive(type.getSubType()));

			return userJson;
		}
		
	}
}