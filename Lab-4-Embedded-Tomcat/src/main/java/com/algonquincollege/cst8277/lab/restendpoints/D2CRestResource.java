/**
 * File:  D2CRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.C2DRestResource.d3;
import static com.algonquincollege.cst8277.lab.restendpoints.D2CRestResource.D2C_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path(D2C_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class D2CRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String D2C_PATH = "/d2c";

	@GET
	public Response d3() {
		logger.debug("{} endpoint reached", D2C_PATH);
		return Response.ok(d3).build();
	}

}