package edu.fresno.uniobjects.data;

/* Copyright (c) 2010, Fresno Pacific University
   Licensed under the New BSD license; see the LICENSE file for details. */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.fresno.uniobjects.UniDataConnection;

/**
 * A FieldSet is a wrapper for a List of {@link Field}s, used when returning data
 * from {@link UniDataConnection#getFields(FieldDefinition)}.
 * @author Brandon Tilley
 *
 */
public class FieldSet
{
	protected List<Field> fields = new ArrayList<Field>();
	
	/**
	 * Construct a blank FieldSet.
	 */
	public FieldSet(){}
	
	/**
	 * Construct a FieldSet with a list of {@link Field}s as initial data.
	 * @param fields List of Fields for initial data.
	 */
	public FieldSet(List<Field> fields)
	{
		this.fields = fields;
	}
	
	/**
	 * Add a {@link Field} to the internal List.
	 * @param field The Field to add
	 */
	public void add(Field field)
	{
		this.fields.add(field);
	}
	
	/**
	 * Find a field by it's real column name in the UniData data source.
	 * @param name The name of the field to find
	 * @return The associated Field
	 */
	public Field getFieldByName(String name)
	{
		Iterator<Field> it = this.fields.iterator();
		while(it.hasNext())
		{
			Field field = it.next();
			if(field.getFieldName().equalsIgnoreCase(name))
				return field;
		}
		
		return null;
	}
	
	/**
	 * Find a field by it's user-friendly column name specificed by the user.
	 * @param name The user-friendly name of the field to find
	 * @return The associated Field
	 */
	public Field getFieldByFriendlyName(String name)
	{
		Iterator<Field> it = this.fields.iterator();
		while(it.hasNext())
		{
			Field field = it.next();
			if(field.getFriendlyName().equalsIgnoreCase(name))
				return field;
		}
		
		return null;
	}
	
	/**
	 * Return the {@link Field}s as a real List.
	 * @return The List of Fields
	 */
	public List<Field> asList()
	{
		return this.fields;
	}
}
