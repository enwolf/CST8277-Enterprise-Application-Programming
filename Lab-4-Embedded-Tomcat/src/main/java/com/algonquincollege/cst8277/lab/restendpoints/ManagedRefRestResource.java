/**
 * File:  ManagedRefRestResource.java <br>
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.restendpoints;

import static com.algonquincollege.cst8277.lab.restendpoints.ManagedRefRestResource.MANAGED_REF_PATH;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.algonquincollege.cst8277.lab.modelentities.EntityEHasMany;
import com.algonquincollege.cst8277.lab.modelentities.EntityFHasManyToOneBackReference;

@Path(MANAGED_REF_PATH)
@Produces(MediaType.APPLICATION_JSON)
public class ManagedRefRestResource implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);
	
	public static final String MANAGED_REF_PATH = "/managed-ref";

	@GET
	public Response ref() {
		logger.debug("{} endpoint reached", MANAGED_REF_PATH);
		EntityEHasMany owner = new EntityEHasMany();
		owner.setId(6);
		owner.setVersion(1);
		owner.setName("Bugs Bunny");
		EntityFHasManyToOneBackReference backRef1 = new EntityFHasManyToOneBackReference();
		backRef1.setId(8);
		backRef1.setField1("aaa");
		backRef1.setVersion(2);
		backRef1.setOwningEntity(owner);
		owner.getManyEntities().add(backRef1);
		EntityFHasManyToOneBackReference backRef2 = new EntityFHasManyToOneBackReference();
		backRef2.setId(11);
		backRef2.setField1("bbb");
		backRef2.setVersion(1);
		backRef2.setOwningEntity(owner);
		owner.getManyEntities().add(backRef2);
		return Response.ok(owner).build();
	}

}