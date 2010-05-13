package edu.fresno.uniobjects.data;

/* Copyright (c) 2010, Fresno Pacific University
   Licensed under the New BSD license; see the LICENSE file for details. */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.fresno.uniobjects.UniDataConnection;

/**
 * FieldDefinition is an object that represents a set of data
 * that should be retrieved from the UniData data source.
 * @author Brandon Tilley
 *
 */
public class FieldDefinition
{
	protected String file;
	protected List<Field> fields = new ArrayList<Field>();
	protected boolean selectOnly;
	protected String fieldSeparator = "|";
	protected String fieldSeparatorRegex = "\\|";
	protected String rowSeparator = "~";
	protected String rowSeparatorRegex = "~";

	/**
	 * Construct a new FieldDefinition, specifying only the file.
	 * @param file The file to pass to the LIST command
	 */
	public FieldDefinition(String file)
	{
		setFile(file);
	}

	/**
	 * Construct a new FieldDefinition, specifying the file and the fields.
	 * @param file The file to pass to the LIST command
	 * @param fields A list of field names to retrieve from
	 */
	public FieldDefinition(String file, List<Field> fields)
	{
		setFile(file);
		setFields(fields);
	}

	/**
	 * Builds the LIST command required to retrieve the data from the
	 * data source.
	 * @return The query to run with {@link UniDataConnection#query(String)}
	 */
	public String getQueryString()
	{
		StringBuilder query = new StringBuilder();
		query.append("LIST " + getFile() + " EVAL\"");
		Iterator<Field> it = getFields().iterator();
		// For each field, add it to the list
		while(it.hasNext())
		{
			String fieldName = it.next().getFieldName();
			query.append(fieldName);
			query.append(":'" + getFieldSeparator() + "':");
		}
		query.append("'" + getRowSeparator() + "'\" FMT \"300L\" ");
		query.append("ID.SUP HDR.SUP COL.SUP NO.PAGE COUNT.SUP");
		if(isSelectOnly())
			query.append(" SELECT.ONLY");

		return query.toString();
	}

	/**
	 * Gets the current working file.
	 * @return The working file
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * Sets the current working file.
	 * @param file The file to set to the working file
	 */
	public void setFile(String file)
	{
		this.file = file;
	}

	/**
	 * Returns a List of Fields describing this definition
	 * @return A List of {@link Field}s to retrieve data from
	 */
	public List<Field> getFields()
	{
		return fields;
	}

	/**
	 * Sets the fields to retrieve from based on a Map of field names and
	 * user-friendly field names.
	 * @param fields A Map of field names to user-friendly field names.
	 */
	public void setFields(Map<String,String> fields)
	{
		List<Field> localFields = new ArrayList<Field>();
		Iterator<Map.Entry<String,String>> it = fields.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<String,String> pairs = it.next();
			localFields.add(new Field(pairs.getKey(), pairs.getValue()));
		}

		setFields(localFields);
	}

	/**
	 * Sets the fields to retrieve data from based on a List of {@link Field}s.
	 * @param fields
	 */
	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

	/**
	 * Sets the fields to retrieve data from based on an array of field names only.
	 * @param fields An array of field names
	 */
	public void setFieldsByName(String[] fields)
	{
		List<String> localFields = Arrays.asList(fields);
		setFieldsByName(localFields);
	}

	/**
	 * Sets the fields to retrieve data from based on a List of field names only.
	 * @param fields A list of field names
	 */
	public void setFieldsByName(List<String> fields)
	{
		List<Field> localFields = new ArrayList<Field>();
		Iterator<String> it = fields.iterator();
		while(it.hasNext())
		{
			Field field = new Field((String) it.next(), null);
			localFields.add(field);
		}

		setFields(localFields);
	}

	/**
	 * Sets the fields to retrieve data from based on an array of Fields.
	 * @param fields An array of {@link Field}s
	 */
	public void setFields(Field[] fields)
	{
		List<Field> localFields = Arrays.asList(fields);
		setFields(localFields);
	}

	/**
	 * Adds a single {@link Field} to the list of fields to retrieve data from.
	 * @param field
	 */
	public void addField(Field field)
	{
		if(!fields.contains(field))
			this.fields.add(field);
	}

	/**
	 * Determines whether or not the definition is in select only mode
	 * (appending <code>SELECT.ONLY</code> to the end of the query).
	 * @return True if in select only mode, false otherwise.
	 */
	public boolean isSelectOnly()
	{
		return selectOnly;
	}

	/**
	 * Sets whether or not the definition is in select only mode
	 * (appending <code>SELECT.ONLY</code> to the end of the query).
	 * @param selectOnly True to enable select only mode, false to disable
	 */
	public void setSelectOnly(boolean selectOnly)
	{
		this.selectOnly = selectOnly;
	}

	/**
	 * Gets the field separator string
	 * @return The field separator string
	 */
	public String getFieldSeparator()
	{
		return fieldSeparator;
	}

	/**
	 * Sets the field separator string
	 * @param fieldSeparator The string to use as a field separator
	 */
	public void setFieldSeparator(String fieldSeparator)
	{
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * Gets the row separator string
	 * @return The row separator string
	 */
	public String getRowSeparator()
	{
		return rowSeparator;
	}

	/**
	 * Sets the row separator string
	 * @param rowSeparator The string to use as a row separator
	 */
	public void setRowSeparator(String rowSeparator)
	{
		this.rowSeparator = rowSeparator;
	}

	/**
	 * Gets the field separator string as an escaped regex.
	 * @return The field separator string as an escaped regex
	 * @see #getFieldSeparator
	 */
	public String getFieldSeparatorRegex()
	{
		return fieldSeparatorRegex;
	}

	/**
	 * Sets the field separator string as an escaped regex.
	 * @param fieldSeparatorRegex The field separator string as an escaped regex
	 * @see #setFieldSeparator
	 */
	public void setFieldSeparatorRegex(String fieldSeparatorRegex)
	{
		this.fieldSeparatorRegex = fieldSeparatorRegex;
	}

	/**
	 * Gets the row separator string as an escaped regex.
	 * @return The row separator string as an escaped regex
	 * @see #getRowSeparator
	 */
	public String getRowSeparatorRegex()
	{
		return rowSeparatorRegex;
	}

	/**
	 * Sets the row separator string as an escaped regex.
	 * @param rowSeparatorRegex The row separator string as an escaped regex
	 * @see #setRowSeparator
	 */
	public void setRowSeparatorRegex(String rowSeparatorRegex)
	{
		this.rowSeparatorRegex = rowSeparatorRegex;
	}
}
