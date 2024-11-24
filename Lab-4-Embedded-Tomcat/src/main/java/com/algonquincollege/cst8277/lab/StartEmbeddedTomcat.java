/**
 * File:  StartEmbeddedTomcat.java
 * Course materials CST 8277
 * 
 * @author Mike Norman
 * 
 */
package com.algonquincollege.cst8277.lab;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import ch.qos.logback.access.tomcat.LogbackValve;

/**
 * <b>Description</b></br>
 * </br>
 * Main class that starts an instance of an Embedded Tomcat Server
 * 
 * @see https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
 */
public class StartEmbeddedTomcat {
	private static final Class<?> MY_KLASS = MethodHandles.lookup().lookupClass();
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASS);

	public static final String BASEDIR = "basedir";
	public static final String DEFAULT_WELCOME_PAGE = "index.html";
	public static final String SLASH = "/";
	public static final String STAR = "*";
	public static final String DOT = ".";
	public static final String DEFAULT_SERVLET_NAME = "default"; // Can actually be anything, but this name makes things clear
	public static final String STARTING_EMBEDDED_TOMCAT_MSG = "Starting Embedded Tomcat...";
	public static final String STOPPING_EMBEDDED_TOMCAT = "Stopping Embedded Tomcat...";
	public static final String SOMETHING_WENT_WRONG_MSG = "Something went wrong starting Embedded Tomcat";
	public static final String EXIT_ON_FAILURE_PROP = "org.apache.catalina.startup.EXIT_ON_INIT_FAILURE";
	public static final String REST_ENDPOINTS_SUFFIX = ".restendpoints";
	public static final String WEBAPP_DIR = "src/main/webapp/";
	public static final String JERSEY_SERVLET_NAME = "jersey-servlet"; // Can actually be anything, but this name makes things clear
	public static final String APPLICATION_API_VERSION = "/api/v1";
	public static final String JERSEY_SERVLET_PATH_MAPPING = APPLICATION_API_VERSION + SLASH + STAR;
	public static final int DEFAULT_PORT = 9090;

	public static void main(String[] args) throws LifecycleException, IOException {
		
		// Re-program logging to use SLF4J+Logback (Why? 'cause it is a better logging framework than j.u.l - AND it doesn't have 'Log4Shell' CVE) 
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		
		// Force Embedded Tomcat server to exit if an exception happens during initialization
		// For example:  If the Tomcat connector doesn't start because the port is in use... or some other component fails
		System.setProperty(EXIT_ON_FAILURE_PROP, Boolean.TRUE.toString());
		
		// Embedded Tomcat needs a working directory to store temporary files... make it relative to where server starts
		Path tomcatBaseDirPath = Paths.get(DOT, BASEDIR).toAbsolutePath().normalize();
		File tomcatBaseDir = tomcatBaseDirPath.toFile();
		
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(tomcatBaseDirPath.toString());
		LogbackValve valve = new LogbackValve();
		valve.setQuiet(true);
		valve.setFilename(LogbackValve.DEFAULT_FILENAME);
		tomcat.getHost().getPipeline().addValve(valve);
		tomcat.getConnector().setPort(DEFAULT_PORT);
        tomcat.getConnector().setThrowOnFailure(false);
		tomcat.setAddDefaultWebXmlToWebapp(false);
		Context rootContext = tomcat.addWebapp("", new File(WEBAPP_DIR).getAbsolutePath());
		Tomcat.addServlet(rootContext, DEFAULT_SERVLET_NAME, DefaultServlet.class.getName());
        rootContext.addServletMappingDecoded(SLASH, DEFAULT_SERVLET_NAME);
        rootContext.addWelcomeFile(DEFAULT_WELCOME_PAGE);
        Tomcat.addServlet(rootContext, JERSEY_SERVLET_NAME, new ServletContainer(resourceConfig()));
        rootContext.addServletMappingDecoded(JERSEY_SERVLET_PATH_MAPPING, JERSEY_SERVLET_NAME);
		Runtime.getRuntime().addShutdownHook(new Thread( () -> {
			try {
				logger.debug(STOPPING_EMBEDDED_TOMCAT);
				tomcat.stop();
				FileUtils.deleteDirectory(tomcatBaseDir);
			}
			catch (Exception e) { /* Ignore */ }
		}));
		try {
			logger.debug(STARTING_EMBEDDED_TOMCAT_MSG);
			tomcat.start();
		}
		catch (Exception e) {
			logger.error(SOMETHING_WENT_WRONG_MSG, e);
		}
		tomcat.getServer().await();
	}

	public static ResourceConfig resourceConfig() {
		ResourceConfig rc = new ResourceConfig();
		String package4Lab = MY_KLASS.getPackageName();
		String package4RestEndpoints = package4Lab + REST_ENDPOINTS_SUFFIX;
		rc.packages(package4RestEndpoints);
		JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
			.configure(SerializationFeature.INDENT_OUTPUT, true)
			.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jacksonJaxbJsonProvider.setMapper(mapper);
		rc.register(jacksonJaxbJsonProvider);
		return rc;
	}

}