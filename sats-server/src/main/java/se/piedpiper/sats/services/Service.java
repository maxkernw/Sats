package se.piedpiper.sats.services;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.piedpiper.sats.models.Type;
import se.piedpiper.sats.repositories.ActivityRepo;

@Path("se/training/activities")
@Produces(MediaType.APPLICATION_JSON)
public final class Service {

	@GET
	public Response getActivity(@QueryParam("fromDate") final int fromDate,
								@QueryParam("toDate") final int toDate){
		
		return Response.ok(ActivityRepo.getActivities()).build();
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
