package se.piedpiper.sats.services;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public class CacheController 
{

	public static Response Cache(Request request, Object object, int time) 
	{
        CacheControl cc = new CacheControl();
        cc.setMaxAge(time);
        
        EntityTag etag = new EntityTag(Integer.toString(object.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(etag);

        // cached resource did change -> serve updated content
        if(builder == null)
        {
                builder = Response.ok(object);
                builder.tag(etag);
        }

        builder.cacheControl(cc);
        return builder.build();
	}

}
