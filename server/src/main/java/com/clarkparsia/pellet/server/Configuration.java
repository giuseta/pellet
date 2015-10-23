package com.clarkparsia.pellet.server;

import java.util.Properties;

/**
 * @author Edgar Rodriguez-Diaz
 */
public interface Configuration {

	String FILENAME = "server.properties";

	String HOST = "host";
	String PORT = "port";
	String USERNAME = "username";
	String PASSWORD = "password";

	Properties getSettings();
}
