package com.algonquincollege.cst8277.lab.modelentities;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class SetOfEntityDeesSerializer extends StdSerializer<Set<EntityDHasManyCees>> {
	private static final long serialVersionUID = 1L;

	public SetOfEntityDeesSerializer() {
        this(null);
    }

    public SetOfEntityDeesSerializer(Class<Set<EntityDHasManyCees>> t) {
        super(t);
    }

	@Override
	public void serialize(Set<EntityDHasManyCees> originalDees, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Set<EntityDHasManyCees> hollowDees = new HashSet<>();
        for (EntityDHasManyCees originalDee : originalDees) {
            // Create a 'hollow' copy of the original EntityDHasManyCees
        	EntityDHasManyCees hollowDee = new EntityDHasManyCees();
        	hollowDee.setCreated(originalDee.getCreated());
        	hollowDee.setId(originalDee.getId());
        	hollowDee.setVersion(originalDee.getVersion());
        	hollowDee.setName(originalDee.getName());
        	hollowDee.setFoobar(originalDee.getFoobar());
        	hollowDee.setCees(null); // This prevents infinite-loop
        	hollowDees.add(hollowDee);
        }
        gen.writeObject(hollowDees);
	}

}
