package se.piedpiper.server.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.piedpiper.server.models.Activity;
import se.piedpiper.server.models.Booking;
import se.piedpiper.server.models.Class;
import se.piedpiper.server.models.Type;

@Path("se/training/activities")
@Produces(MediaType.APPLICATION_JSON)
public final class Service {

	@GET
	public Response getActivity(@QueryParam("fromDate") final int fromDate,
								@QueryParam("toDate") final int toDate){
		
		ArrayList<Integer> classCategories = new ArrayList<>();
		classCategories.add(1);
		classCategories.add(2);
		classCategories.add(3);
		
		Class theClass = new Class(123, 1234, 123, 122, 121, 123, "steffe", new Date(), 1337, 1500, 2, 8, classCategories);
		Booking booking = new Booking("Ready", theClass, 10, 13, 8);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date = format1.format(new Date()); 
		Activity activity = new Activity(booking, "bra bra", date, 10, 20, 2, "ohaa", "bra bra", "en sån där sub", "typ typ");
		
		TreeSet<Activity> activities = new TreeSet<>();
		activities.add(activity);
		return Response.ok(activities).build();
	}
	
	@GET
	@Path("types")
    public Response getTypes() {
		ArrayList<Type> set = new ArrayList<>();
		
		Type t1 = new Type("namn1", "typ1", "subtype1");
		Type t2 = new Type("namn2", "typ2", "subtype2");
		Type t3 = new Type("namn3", "typ3", "subtype3");
		
		set.add(t1);
		set.add(t2);
		set.add(t3);
		
        return Response.ok(set).build();
    }
	
	@GET
	@Path("{type}")
	public Response getType(@PathParam("type") final String type)
	{
		return Response.ok(new Type(type, "typ1", "subtype1")).build();
	}
}
