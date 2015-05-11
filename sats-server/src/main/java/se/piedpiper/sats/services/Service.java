package se.piedpiper.sats.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import se.piedpiper.sats.models.Activity;
import se.piedpiper.sats.models.Type;
import se.piedpiper.sats.repositories.ActivityRepo;
import se.piedpiper.sats.repositories.TypeRepo;

@Path("se/training/activities")
@Produces(MediaType.APPLICATION_JSON)
public final class Service 
{

	@GET
	public Response getActivities(
			@DefaultValue("") @QueryParam("fromDate") final String fromDate,
			@DefaultValue("") @QueryParam("toDate") final String toDate,
			@Context Request request) 
	{
		if (fromDate.length() == 8 && toDate.length() == 8) 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date dateFrom, dateTo;
			try {
				dateFrom = sdf.parse(fromDate);
				dateTo = sdf.parse(toDate);
				TreeSet<Activity> activities = ActivityRepo.getActivities(dateFrom, dateTo);

				return CacheController.Cache(request, activities, 1800);
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@GET
	@Path("types")
	public Response getTypes(@Context final Request request) 
	{
		ArrayList<Type> types = TypeRepo.getTypes();
		
		return CacheController.Cache(request, types, 1800);
	}

	@GET
	@Path("{type}")
	public Response getType(@PathParam("type") final String subType,
							@Context final Request request) 
	{
		Type type = TypeRepo.getType(subType);
		
		return CacheController.Cache(request, type, 1800);
	}
}