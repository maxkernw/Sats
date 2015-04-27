package se.piedpiper.sats.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import se.piedpiper.sats.models.Activity;
import se.piedpiper.sats.repositories.ActivityRepo;
import se.piedpiper.sats.repositories.TypeRepo;

@Path("se/training/activities")
@Produces(MediaType.APPLICATION_JSON)
public final class Service {

	@GET
	public Response getActivities(@DefaultValue("")@QueryParam("fromDate") final String fromDate,
								  @DefaultValue("")@QueryParam("toDate") final String toDate,
								  @Context Request request){
		if(fromDate.length() == 8 && toDate.length() == 8){
	        
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		    Date dateFrom;
		    Date dateTo;
			try {
				
				dateFrom = sdf.parse(fromDate);
				dateTo = sdf.parse(toDate);
				TreeSet <Activity> activities = ActivityRepo.getActivities(dateFrom,dateTo);
		        CacheControl cc = new CacheControl();
		        cc.setMaxAge(1800);
		        
		        EntityTag etag = new EntityTag(Integer.toString(activities.hashCode()));
		        ResponseBuilder builder = request.evaluatePreconditions(etag);

		        // cached resource did change -> serve updated content
		        if(builder == null){
		                builder = Response.ok(activities);
		                builder.tag(etag);
		        }

		        builder.cacheControl(cc);
		        return builder.build();
		        
			} catch (ParseException e) {
				return Response.status(Status.BAD_REQUEST).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build(); 
	}
	
	@GET
	@Path("types")
    public Response getTypes() {
        return Response.ok(TypeRepo.getTypes()).build();
    }
	
	@GET
	@Path("{type}")
	public Response getType(@PathParam("type") final String subType)
	{
		return Response.ok(TypeRepo.getType(subType)).build();
	}
}
