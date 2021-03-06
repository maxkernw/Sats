package se.piedpiper.sats.errormappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import se.piedpiper.sats.errors.DatabaseException;

@Provider
public final class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException>
{
	@Override
	public Response toResponse(final DatabaseException exception)
	{
		return Response.status(Status.GATEWAY_TIMEOUT).build();
	}
}
