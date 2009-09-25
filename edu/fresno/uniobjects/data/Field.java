package edu.fresno.uniobjects.data;

import edu.fresno.uniobjects.UniDataConnection;

/**
 * A Field is a wrapper for three pieces of information about a UniData field:
 * <ul>
 * <li>The name of the field (eg <code>FIRST.NAME</code>)</li>
 * <li>The friendly name of the field (eg <code>fname</code>)</li>
 * <li>The data the field contains, if created by
 * {@link UniDataConnection#getFields(FieldDefinition)}</li>
 * </ul>
 * @author Brandon Tilley
 *
 */
public class Field
{
	protected String fieldName;
	protected String friendlyName;
	protected String data;
	
	/**
	 * Create a field with no data
	 */
	public Field(){}
	
	/**
	 * Get the data contained in a field
	 * @return The data from the UniData data source contained in this field
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * Sets the data contained in the field. This is only for use by
	 * {@link UniDataConnection#getFields(FieldDefinition)} and otherwise
	 * serves no purpose.
	 * @param data The data to set
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * Create a Field, specifying the field name and the user-friendly field name.
	 * @param fieldName The name of the field
	 * @param friendlyName A user-friendly name to identify the field
	 */
	public Field(String fieldName, String friendlyName)
	{
		setFieldName(fieldName);
		setFriendlyName(friendlyName);
	}
	
	/**
	 * Gets the field name.
	 * @return The field name
	 */
	public String getFieldName()
	{
		return fieldName;
	}
	
	/**
	 * Sets the field name.
	 * @param fieldName The field name to set
	 */
	public void setFieldName(String fieldName)
	{
		this.fieldName = fieldName;
	}
	
	/**
	 * Gets the user-friendly name for the field.
	 * @return The user-friendly name for the field
	 */
	public String getFriendlyName()
	{
		return friendlyName;
	}
	
	/**
	 * Sets the user-friendly field name.
	 * @param friendlyName The user-friendly field name to set
	 */
	public void setFriendlyName(String friendlyName)
	{
		this.friendlyName = friendlyName;
	}
}
