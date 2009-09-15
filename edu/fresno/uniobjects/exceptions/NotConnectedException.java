package edu.fresno.uniobjects.exceptions;

/**
 * Exception that indicates that the {@link UniDataConnector} is not connected.
 * @author Brandon Tilley
 *
 */
public class NotConnectedException extends Exception
{
	private static final long serialVersionUID = 2743449941479780185L;
	
	/**
	 * Create a default NotConnectedException.
	 */
	public NotConnectedException()
	{
		super();
	}
	
	/**
	 * Create a NotConnectedException with a message.
	 * @param message The message to attach.
	 */
	public NotConnectedException(String message)
	{
		super(message);
	}
}
