/**
 * File:  TestEntitiesSystem.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 *
 */
package com.algonquincollege.cst8277.lab;

import static com.algonquincollege.cst8277.lab.StartEmbeddedTomcat.APPLICATION_API_VERSION;
import static com.algonquincollege.cst8277.lab.StartEmbeddedTomcat.DEFAULT_PORT;
import static com.algonquincollege.cst8277.lab.restendpoints.ManagedRefRestResource.MANAGED_REF_PATH;
import static com.algonquincollege.cst8277.lab.restendpoints.EchoRestResource.ECHO_PATH;
import static com.algonquincollege.cst8277.lab.restendpoints.GoodbyeWorldRestResource.GOODBYEWORLD_PATH;
import static com.algonquincollege.cst8277.lab.restendpoints.HelloWorldRestResource.HELLOWORLD_PATH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.util.List;

import javax.naming.NamingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.algonquincollege.cst8277.lab.modelentities.EntityA;
import com.algonquincollege.cst8277.lab.modelentities.EntityB;
import com.algonquincollege.cst8277.lab.modelentities.EntityEHasMany;
import com.algonquincollege.cst8277.lab.modelentities.EntityFHasManyToOneBackReference;
import com.algonquincollege.cst8277.lab.modelentities.SimpleEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@TestMethodOrder(MethodOrderer.MethodName.class) // Execute test methods in alpha(numerical) order 
public class TestEntitiesSystem {
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);

	public static final String HTTP_SCHEMA = "http";
    public static final String HOST = "localhost";
    public static final String MSG_TOBE_ECHOD = "something to be echo'd";

    // Test fixture(s)
    static URI uri;

    @BeforeAll
    public static void oneTimeSetUp() throws Exception {
        logger.debug("oneTimeSetUp");
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        uri = UriBuilder
            .fromUri(APPLICATION_API_VERSION)
            .scheme(HTTP_SCHEMA)
            .host(HOST)
            .port(DEFAULT_PORT)
            .build();
    }

    protected WebTarget webTarget;
    @BeforeEach
    public void setUp() {
        Client client = ClientBuilder.newClient(
            new ClientConfig().register(MyObjectMapperProvider.class).register(new LoggingFeature()));
        webTarget = client.target(uri);
    }

    @Test
    public void test01_helloworld() throws JsonMappingException, JsonProcessingException, NamingException {
    	Response response = webTarget
            .path(HELLOWORLD_PATH)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        EntityA entityA = response.readEntity(new GenericType<EntityA>(){});
        assertThat(entityA, notNullValue());
    }

    @Test
    public void test02_goodbyeworld() throws JsonMappingException, JsonProcessingException, NamingException {
        Response response = webTarget
            .path(GOODBYEWORLD_PATH)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        EntityB entityB = response.readEntity(new GenericType<EntityB>(){});
        assertThat(entityB, notNullValue());
    }
    
    @Test
    public void test03_echo() {
        Entity<String> somethingEntity = Entity.text(MSG_TOBE_ECHOD);
        Response response = webTarget
    		.path(ECHO_PATH)
            .request()
            .post(somethingEntity);
        assertThat(response.getStatus(), is(200));
        SimpleEntity simple = response.readEntity(new GenericType<SimpleEntity>(){});
        assertThat(simple, notNullValue());
        assertThat(simple.getMessage(), equalTo(MSG_TOBE_ECHOD));
    	
    }

    @Test
    public void test04_managed_refs() {
        Response response = webTarget
            .path(MANAGED_REF_PATH)
            .request()
            .get();
        assertThat(response.getStatus(), is(200));
        EntityEHasMany entityHasMany = response.readEntity(new GenericType<EntityEHasMany>(){});
        assertThat(entityHasMany, notNullValue());
        List<EntityFHasManyToOneBackReference> entities = entityHasMany.getManyEntities();
        assertThat(entities, hasSize(2));
        EntityFHasManyToOneBackReference backReference1 = entities.get(0);
        EntityEHasMany owner = backReference1.getOwningEntity();
        assertThat(entityHasMany, sameInstance(owner));
    }
}