package com.algonquincollege.cst8277.lab.modelentities;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class SetOfEntityCeesSerializer extends StdSerializer<Set<EntityCHasManyDees>> {
	private static final long serialVersionUID = 1L;

	public SetOfEntityCeesSerializer() {
        this(null);
    }

    public SetOfEntityCeesSerializer(Class<Set<EntityCHasManyDees>> t) {
        super(t);
    }

	@Override
	public void serialize(Set<EntityCHasManyDees> originalCees, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Set<EntityCHasManyDees> hollowCees = new HashSet<>();
        for (EntityCHasManyDees originalDee : originalCees) {
            // Create a 'hollow' copy of the original EntityCHasManyDees
        	EntityCHasManyDees hollowCee = new EntityCHasManyDees();
        	hollowCee.setCreated(originalDee.getCreated());
        	hollowCee.setId(originalDee.getId());
        	hollowCee.setVersion(originalDee.getVersion());
        	hollowCee.setName(originalDee.getName());
        	hollowCee.setFoobar(originalDee.getFoobar());
        	hollowCee.setDees(null); // This prevents infinite-loop
        	hollowCees.add(hollowCee);
        }
        gen.writeObject(hollowCees);
	}

}
