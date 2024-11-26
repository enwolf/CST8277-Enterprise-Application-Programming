/**
 * File:  EntityHasMany.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab.modelentities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("unused")

//TODO - Add annotation so that only non-null fields are in JSON body
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EntityEHasMany extends PojoBase implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected List<EntityFHasManyToOneBackReference> manyEntities = new ArrayList<>();

    public EntityEHasMany() {
        super();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // TODO - Add annotation to manage OneToMany reference
    @JsonManagedReference
    public List<EntityFHasManyToOneBackReference> getManyEntities() {
        return manyEntities;
    }

    public void setManyEntities(List<EntityFHasManyToOneBackReference> manyEntities) {
        this.manyEntities = manyEntities;
    }

}