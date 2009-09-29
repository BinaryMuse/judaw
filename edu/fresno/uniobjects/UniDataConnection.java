package edu.fresno.uniobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.fresno.uniobjects.data.Field;
import edu.fresno.uniobjects.data.FieldDefinition;
import edu.fresno.uniobjects.data.FieldSet;
import edu.fresno.uniobjects.exceptions.NotConnectedException;

import asjava.uniclientlibs.UniConnectionException;
import asjava.uniobjects.UniCommand;
import asjava.uniobjects.UniCommandException;
import asjava.uniobjects.UniJava;
import asjava.uniobjects.UniSession;
import asjava.uniobjects.UniSessionException;

/**
 * UniDataConnection allows you to connect to a UniData data source,
 * and provides helpful functions for running queries.
 * @author Brandon Tilley
 */
public class UniDataConnection
{
	/**
	 * Name of the UniObjects session COM object
	 */
	public static final String UNIOBJ_SESSION = "Uniobjects.Unioaifctrl";
	/**
	 * Name of the UniObjects array COM object
	 */
	public static final String UNIOBJ_ARRAY   = "Uniobjects.UniDynArray";
	/**
	 * Constant for the UniData database type
	 */
	public static final String DBTYPE_UNIDATA = "UNIDATA";
	/**
	 * The value mark.
	 */
	public static final String VALUE_MARK = "ý";
	/**
	 * The UniJava object is used to create and destroy sessions.
	 * @deprecated As of version 1.3, replaced by getter {@link #UniJava()}
	 * @see #UniJava()
	 */
	@Deprecated
	public static UniJava UniJava = new UniJava();
	
	protected String host;
	protected String account;
	protected String username;
	protected String password;
	protected UniSession session;
	
	/**
	 * Creates the connection object, passing in the connection parameters.
	 * Does not connect to the database, see {@link #connect()}.
	 * @param username The username to connect as
	 * @param password The password for <code>username</code>
	 * @param host The host to connect to
	 * @param account The account path to use
	 */
	public UniDataConnection(String username, String password, String host, String account)
	{
		setUsername(username);
		setPassword(password);
		setHost(host);
		setAccount(account);
	}
	
	/**
	 * Retrieves the internal UniJava object that is used to create/destroy sessions,
	 * etc. Added in 1.3 to replace direct access to {@link #UniJava}.
	 * @return The internal UniJava object
	 */
	public static UniJava UniJava()
	{
		return UniDataConnection.UniJava;
	}
	
	/**
	 * Uses <code>UniJava.openSession()</code> to create a new session and
	 * attempts to connect to the UniData data source.
	 * @throws UniConnectionException If there is an issue with the connection
	 * @throws UniSessionException If there is an issue with the session
	 */
	public void connect() throws UniConnectionException, UniSessionException
	{
		this.session = UniDataConnection.UniJava().openSession();
		this.session.setUserName(this.getUsername());
		this.session.setPassword(this.getPassword());
		this.session.setHostName(this.getHost());
		this.session.setAccountPath(this.getAccount());
		this.session.setDataSourceType(UniDataConnection.DBTYPE_UNIDATA);
		
		this.session.connect();
	}

	/**
	 * Disconnects from the UniData data source using
	 * <code>UniJava.closeSession()</code>.
	 * @throws UniSessionException If there is an issue with the session
	 * @throws NullPointerException If the session is null
	 */
	public void disconnect() throws UniSessionException
	{
			UniDataConnection.UniJava().closeSession(this.session);
	}

	/**
	 * Executes a query on the UniData connection.
	 * Implements <code>UniSession.Command.Exec()</code>.
	 * @return The response from the UniData data source
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public String query(String query) throws NotConnectedException, UniSessionException, UniCommandException
	{
		if(!this.isActive())
			throw new NotConnectedException();
		
		UniCommand command = null;
		command = this.session.command();
		command.setCommand(query);
		command.exec();
		
		return command.response().trim();
	}
	
	/**
	 * Convenience function for {@link #getFields(FieldDefinition)} without
	 * having to create a new FieldDefinition.
	 * @param file The file to select from
	 * @param fields Array of field names to retrieve
	 * @return A list of {@link FieldSet}s
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public List<FieldSet> getFields(String file, String[] fields) throws NotConnectedException, UniSessionException, UniCommandException
	{
		FieldDefinition fd = new FieldDefinition(file);
		fd.setFieldsByName(fields);
		return getFields(fd);
	}
	
	/**
	 * Convenience function for {@link #getFields(FieldDefinition)} without
	 * having to create a new FieldDefinition.
	 * @param file The file to select from
	 * @param fields Map of field names to friendly field names to retrieve, real field name first
	 * @return A list of {@link FieldSet}s
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public List<FieldSet> getFields(String file, Map<String,String> fields) throws NotConnectedException, UniSessionException, UniCommandException
	{
		FieldDefinition fd = new FieldDefinition(file);
		fd.setFields(fields);
		return getFields(fd);
	}
	
	/**
	 * Convenience function for {@link #getFields(FieldDefinition)} without
	 * having to create a new FieldDefinition.
	 * @param file The file to select from
	 * @param fields List of {@link Field}s that specify what fields to retrieve
	 * @return A list of {@link FieldSet}s
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public List<FieldSet> getFields(String file, List<Field> fields) throws NotConnectedException, UniSessionException, UniCommandException
	{
		FieldDefinition fd = new FieldDefinition(file, fields);
		return getFields(fd);
	}
	
	/**
	 * Convenience function for {@link #getFields(FieldDefinition)} without
	 * having to create a new FieldDefinition.
	 * @param file The file to select from
	 * @param fields Array of {@link Field}s that specify what fields to retrieve
	 * @return A list of {@link FieldSet}s
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public List<FieldSet> getFields(String file, Field[] fields) throws NotConnectedException, UniSessionException, UniCommandException
	{
		FieldDefinition fd = new FieldDefinition(file);
		fd.setFields(fields);
		return getFields(fd);
	}
	
	/**
	 * Executes a special LIST query on the UniData data source to
	 * retrieve certain fields. {@link FieldDefinition} defines which
	 * file and fields, as well as other parameters, to select data from.
	 * @param fieldDefinition The {@link FieldDefinition} that defines the data to retrieve
	 * @return A list of {@link FieldSet}s
	 * @throws NotConnectedException If the UniData connection is not active
	 * @throws UniCommandException If there is an issue with the command
	 * @throws UniSessionException If there is an issue with the session
	 */
	public List<FieldSet> getFields(FieldDefinition fieldDefinition) throws NotConnectedException, UniSessionException, UniCommandException
	{
		String query = fieldDefinition.getQueryString();
		String result = this.query(query);
		if(result.isEmpty())
			return null;
		else
			return parseIntoFieldset(result, fieldDefinition);
	}
	
	/**
	 * Given the data from a query() call and a fieldDefinition, this function parses
	 * the data out into FieldSets for ease-of-use.
	 * @param data The data from query()
	 * @param fieldDefinition The FieldDefinition used
	 * @return A List of FieldSets which contains the Fields with data
	 */
	protected List<FieldSet> parseIntoFieldset(String data, FieldDefinition fieldDefinition)
	{
		if(data.trim().isEmpty())
			return null;
		
		List<FieldSet> fieldSets = new ArrayList<FieldSet>();
		
		// Split on rowSeparator to get an array of rows
		List<String> rows = Arrays.asList(data.split(fieldDefinition.getRowSeparatorRegex()));
		// For each row, split on fieldSeparator to get an array of fields for that row.
		Iterator<String> rowIter = rows.iterator();
		while(rowIter.hasNext())
		{
			String line = rowIter.next().trim();
			List<String> rowFields = 
				Arrays.asList(line.split(fieldDefinition.getFieldSeparatorRegex()));
			
			// For each field in this row, get the field name, friendly name
			// (if any), and the data, and create a Field item. Add all Fields
			// into a FieldSet to be added to the return list.
			FieldSet set = new FieldSet();
			Iterator<String> fieldIter = rowFields.iterator();
			int i = 0;
			while(fieldIter.hasNext())
			{
				String fieldData = fieldIter.next().trim();
				if(i >= fieldDefinition.getFields().size())
					continue;
				Field fieldDef = fieldDefinition.getFields().get(i);
				String fieldName = fieldDef.getFieldName();
				String friendlyName = fieldDef.getFriendlyName();
				Field field = new Field(fieldName, friendlyName);
				field.setData(fieldData);
				set.add(field);
				i++;
			}
			fieldSets.add(set);
		}
		
		return fieldSets;
	}

	/**
	 * Gets the current host
	 * @return The current host
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * Sets the host
	 * @param host The host to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}

	/**
	 * Gets the current account
	 * @return The current account
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * Sets the account
	 * @param account The account to set
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}

	/**
	 * Gets the current username
	 * @return The current username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Sets the username
	 * @param user The username to set
	 */
	public void setUsername(String user)
	{
		this.username = user;
	}

	/**
	 * Gets the current password
	 * @return The current password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the password
	 * @param password The password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Returns the underlying UniObjects session
	 * @return The UniObjects session
	 */
	public UniSession getSession()
	{
		return this.session;
	}
	
	/**
	 * Determines if the connection is active or not
	 * @return True if active, false otherwise
	 */
	public boolean isActive()
	{
		return this.session.isActive();
	}

}
