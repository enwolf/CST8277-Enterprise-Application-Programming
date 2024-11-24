/**
 * File:  GoodbyeWorldRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author  Mike Norman
 * 
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.GoodbyeWorldRestResource.GOODBYEWORLD_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.lab.modelentities.EntityB;

@Path(GOODBYEWORLD_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class GoodbyeWorldRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String GOODBYEWORLD_PATH = "/goodbyeworld";

	@GET
	public Response sayGoodbye() {
		logger.debug("{} endpoint reached", GOODBYEWORLD_PATH);
		logger.debug("/goodbyeworld endpoint reached");
		EntityB entityB = new EntityB();
		entityB.setId(5);
		entityB.setVersion(1);
		return Response.ok(entityB).build();
	}

}