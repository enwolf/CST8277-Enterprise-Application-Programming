/**
 * File:  HelloWorldRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.HelloWorldRestResource.HELLOWORLD_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.lab.modelentities.EntityA;

@Path(HELLOWORLD_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class< ?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String HELLOWORLD_PATH = "/helloworld";

	@GET
	public Response sayHello() {
		logger.debug("{} endpoint reached", HELLOWORLD_PATH);
		EntityA entityA = new EntityA();
		entityA.setId(2);
		entityA.setVersion(1);
		return Response.ok(entityA).build();
	}

}