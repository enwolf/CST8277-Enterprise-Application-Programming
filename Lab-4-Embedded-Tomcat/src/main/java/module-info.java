@SuppressWarnings("all")
open module StartEmbeddedTomcat {
    requires org.slf4j;
    requires jul.to.slf4j;
    requires logback.core;
    requires logback.access;
    requires org.apache.tomcat.embed.core;
	requires java.management;
	requires jdk.management;
	requires jersey.container.servlet.core;
	requires java.ws.rs;
	requires jersey.server;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	requires com.fasterxml.jackson.jaxrs.json;
	requires com.fasterxml.jackson.annotation;
	requires java.naming;
	requires org.apache.commons.io;
}