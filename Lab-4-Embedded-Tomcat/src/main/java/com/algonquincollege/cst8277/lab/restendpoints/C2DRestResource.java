/**
 * File:  C2DRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.C2DRestResource.C2D_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.lab.modelentities.EntityCHasManyDees;
import com.algonquincollege.cst8277.lab.modelentities.EntityDHasManyCees;

@Path(C2D_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class C2DRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String C2D_PATH = "/c2d";
	
	public static EntityCHasManyDees c1;
	public static EntityCHasManyDees c2;
	public static EntityDHasManyCees d1;
	public static EntityDHasManyCees d2;
	public static EntityDHasManyCees d3;
	static {
		c1 = new EntityCHasManyDees();
		c1.setId(5);
		c1.setName("qqq");
		c2 = new EntityCHasManyDees();
		c2.setId(6);
		c2.setName("www");
		d1 = new EntityDHasManyCees();
		d1.setId(7);
		d1.setName("eee");
		d2 = new EntityDHasManyCees();
		d2.setId(8);
		d2.setName("rrr");
		d3 = new EntityDHasManyCees();
		d3.setId(9);
		d3.setName("ttt");
		
		c1.getDees().add(d1);
		c2.getDees().add(d2);
		c2.getDees().add(d3);
		
		d1.getCees().add(c1);
		d1.getCees().add(c2);
		d2.getCees().add(c2);
		d3.getCees().add(c1);
		d3.getCees().add(c2);
	}

	@GET
	public Response c1() {
		logger.debug("{} endpoint reached", C2D_PATH);
		return Response.ok(c1).build();
	}

}