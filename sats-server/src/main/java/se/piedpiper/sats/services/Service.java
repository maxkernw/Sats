package se.piedpiper.sats.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.piedpiper.sats.repositories.ActivityRepo;
import se.piedpiper.sats.repositories.TypeRepo;

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
        return Response.ok(TypeRepo.getTypes()).build();
    }
	
	@GET
	@Path("{type}")
	public Response getType(@PathParam("type") final String subType)
	{
		return Response.ok(TypeRepo.getType(subType)).build();
	}
}
