/**
 * File:  EchoRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.EchoRestResource.ECHO_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.lab.modelentities.SimpleEntity;

@Path(ECHO_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class EchoRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String ECHO_PATH = "/echo";

	@POST
	public Response echo(String theMessageToEchoBack) {
		logger.debug("{} endpoint reached", ECHO_PATH);
		return Response.ok(new SimpleEntity(theMessageToEchoBack)).build();
	}

}