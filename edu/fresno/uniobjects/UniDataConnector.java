package edu.fresno.uniobjects;

import asjava.uniclientlibs.UniConnectionException;
import asjava.uniobjects.UniSessionException;
import edu.fresno.uniobjects.exceptions.NotConnectedException;

/**
 * UniDataConnector is a very basic interface that specifies methods
 * that any class that connectes to a UniData data source must implement.
 * @author Brandon Tilley
 *
 */
public interface UniDataConnector
{
	/**
	 * Connect to the data source
	 * @throws UniConnectionException If there is an issue with the connection
	 * @throws UniSessionException If there is an issue with the session
	 */
	public void connect() throws UniConnectionException, UniSessionException;
	/**
	 * Disconnect from the data source
	 * @throws UniSessionException If there is an issue with the session
	 */
	public void disconnect() throws UniSessionException;
	/**
	 * Run a query on the data source
	 * @param query The query to run
	 * @return The results of the query
	 * @throws NotConnectedException If the data source is not connected
	 */
	public String query(String query) throws NotConnectedException;
}
