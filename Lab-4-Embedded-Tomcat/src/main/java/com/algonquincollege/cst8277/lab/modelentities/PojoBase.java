/**
 * File:  PojoBase.java Course materials CST 8277
 * 
 * @author Mike Norman
 * 
 */
package com.algonquincollege.cst8277.lab.modelentities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@SuppressWarnings("unused")

/**
 * Abstract class that is base of (class) hierarchy for all @Entity classes
 */

// Similar to JPA Inheritance 'discriminator', need to tell Jackson
// (our Json processing library) how to tell EntityA from EntityB or C/D/E/F ...
/* TODO - Add annotation to encode Entities as follows
     EntityA                          = 'typeA'
     EntityB                          = 'typeB'
     EntityCHasManyDees               = 'typeC'
     EntityDHasManyCees               = 'typeD'
     EntityEHasMany                   = 'typeE'
     EntityFHasManyToOneBackReference = 'typeF'
*/
// TODO - Add annotation so that only non-null fields are in JSON body
public abstract class PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int id;
	protected int version;
	protected LocalDateTime created;
	protected String foobar;

	public PojoBase() {
		this.setCreated(LocalDateTime.now());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public LocalDateTime getCreated() {
		return this.created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	// TODO - Add annotation here to change 'foobar' to 'msg' in JSON body
	public String getFoobar() {
		return foobar;
	}

	public void setFoobar(String foobar) {
		this.foobar = foobar;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// Only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		return prime * result + Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof PojoBase otherPojoBase) {
			// See comment (above) in hashCode():  Compare using only member variables that are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherPojoBase.getId());
		}
		return false;
	}

}