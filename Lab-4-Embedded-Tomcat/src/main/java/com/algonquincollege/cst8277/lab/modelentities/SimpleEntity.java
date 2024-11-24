/**
 * File:  SimpleEntity.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 * 
 */
package com.algonquincollege.cst8277.lab.modelentities;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")

/**
 * Similar to the 'MessageHolder' class from rest-demo, this simple class
 * has three fields; however, only two are in the JSON body
 * 
 * Note:  This class does NOT inherit from PojoBase
 */
//TODO - Add annotation so that only non-null fields are in JSON body
public class SimpleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String MSG_SHOULDNT_BE_SEEN = "something else that shouldn't be seen";

	protected String message;
	protected LocalDateTime date;
	protected String somethingElse;

	public SimpleEntity() {
		super();
		setDate(LocalDateTime.now());
		setSomethingElse(MSG_SHOULDNT_BE_SEEN);
	}

	public SimpleEntity(String message) {
		this();
		this.message = message;
	}

	// TODO - Make JSON field 'msgEchodBack', not 'message'
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonIgnore
	public String getSomethingElse() {
		return somethingElse;
	}

	public void setSomethingElse(String somethingElse) {
		this.somethingElse = somethingElse;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("(SimpleMessage)msg = ").append(getMessage()).append(" [date = ")
				.append(getDate()).append("]");
		return builder.toString();
	}
}