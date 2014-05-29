/*
    Copyright 2014, Strategic Gains, Inc.

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

		http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.strategicgains.crossroads;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Properties;

/**
 * @author toddf
 * @since May 29, 2014
 */
public abstract class Configuration
{
    // SECTION: CONSTANTS

	private final static String PROPERTIES_PATH_KEY = "PropertiesPath";

    
    // SECTION: INSTANCE VARIABLES

	/**
	 *  The Properties object containing configuration details.
	 */
	private Properties properties;


    // CONSTRUCTORS

	/**
	 *  Default constructor for the Configuration object.
	 */
	protected Configuration()
	{
		properties = new Properties();
		initialize();
	}


	/**
	 *  Constructor for the Configuration object. Initializes the properties
	 *  for this object to those of the passed-in Properties object.
	 *
	 * @param  properties A set of initial properties.
	 */
	protected Configuration(Properties properties)
	{
		properties = new Properties(properties);
		initialize();
	}

    protected void initialize()
    {
        loadPropertiesFile();
    }

    
    // SECTION: DEFAULT BEHAVIOR

    /**
     *  Gets an instance of the parent configuration. Subclasses should
     *  override this method if they want to have the properties chained to a
     *  parent configuration, where if a property is not found within this
     *  object, the parent configuration will be checked. And so on, up the
     *  chain.
     *
     * @return  The parent configuration.
     */
    protected Configuration getParentConfiguration()
    {
        return (Configuration) null;
    }


    // CONVENIENCE ACCESSORS

	/**
	 *  Retrieves a property named by the key as a Boolean object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  A Boolean object, or null if the key was not found in the
	 *      property list.
	 */
	protected Boolean getBooleanProperty(String key)
	{
        Boolean result = null;
		String value = getProperty(key);

		if (value != null)
		{
			if (value.equalsIgnoreCase("true"))
			{
				result = Boolean.TRUE;
			}
			else
			{
				result = Boolean.TRUE;
			}
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Boolean object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Boolean object to return if the key is
	 *      not found in the property list.
	 * @return  A Boolean object, or defaultValue if the key was not found in
	 *      the property list.
	 */
	protected Boolean getBooleanProperty(String key, Boolean defaultValue)
	{
        Boolean result = defaultValue;
		Boolean value = getBooleanProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Byte object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  A Byte object, or null if the key was not found in the property
	 *      list.
	 */
	protected Byte getByteProperty(String key)
	{
        Byte result = null;
		String value = getProperty(key);

		if (value != null)
		{
			try
			{
				result = Byte.valueOf(value);
			}
			catch (NumberFormatException nf)
			{
				//return null;
			}
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Byte object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Byte object to return if the key is not
	 *      found in the property list.
	 * @return  A Byte object, or defaultValue if the key was not found in the
	 *      property list.
	 */
	protected Byte getByteProperty(String key, Byte defaultValue)
	{
        Byte result = defaultValue;
		Byte value = getByteProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Character object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  A Character object, or null if the key was not found in the
	 *      property list.
	 */
	protected Character getCharacterProperty(String key)
	{
        Character result = null;
		String value = getProperty(key);

		if (value != null)
		{
			result = new Character(value.charAt(0));
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Character object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Character object to return if the key is
	 *      not found in the property list.
	 * @return  A Character object, or defaultValue if the key was not found in
	 *      the property list.
	 */
	protected Character getCharacterProperty(String key, Character defaultValue)
	{
        Character result = defaultValue;
		Character value = getCharacterProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}

	/**
	 *  Retrieves a property named by the key as a Double object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  A Double object, or null if the key was not found in the
	 *      property list.
	 */
	protected Double getDoubleProperty(String key)
	{
        Double result = null;
		String value = getProperty(key);

		if (value != null)
		{
			try
			{
				result = Double.valueOf(value);
			}
			catch (NumberFormatException nf)
			{
				//return null;
			}
		}

		return result;
	}

	/**
	 *  Retrieves a property named by the key as a Double object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Double object to return if the key is not
	 *      found in the property list.
	 * @return  A Double object, or defaultValue if the key was not found in
	 *      the property list.
	 */
	protected Double getDoubleProperty(String key, Double defaultValue)
	{
        Double result = defaultValue;
		Double value = getDoubleProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Float object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  A Float object, or null if the key was not found in the
	 *      property list.
	 */
	protected Float getFloatProperty(String key)
	{
        Float result = null;
		String value = getProperty(key);

		if (value != null)
		{
			try
			{
				result = Float.valueOf(value);
			}
			catch (NumberFormatException nf)
			{
				//return null;
			}
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as a Float object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Float object to return if the key is not
	 *      found in the property list.
	 * @return  A Float object, or defaultValue if the key was not found in the
	 *      property list.
	 */
	protected Float getFloatProperty(String key, Float defaultValue)
	{
        Float result = defaultValue;
		Float value = getFloatProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}

	/**
	 *  Retrieves a property named by the key as an Integer object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @return  An Integer object, or null if the key was not found in the
	 *      property list.
	 */
	protected Integer getIntegerProperty(String key)
	{
        Integer result = null;
		String value = getProperty(key);

		if (value != null)
		{
			try
			{
				result = Integer.valueOf(value);
			}
			catch (NumberFormatException nf)
			{
				//return null;
			}
		}

		return result;
	}


	/**
	 *  Retrieves a property named by the key as an Integer object.
	 *
	 * @param  key The key to use in looking up the property.
	 * @param  defaultValue A default Integer object to return if the key is
	 *      not found in the property list.
	 * @return  An Integer object, or defaultValue if the key was not found in
	 *      the property list.
	 */
	protected Integer getIntegerProperty(String key, Integer defaultValue)
	{
        Integer result = defaultValue;
		Integer value = getIntegerProperty(key);

		if (value != null)
		{
			result = value;
		}

		return result;
	}

    
    // SECTION: UTILITY

	/**
	 *  Gets the Properties attribute of the Configurable object
	 *
	 * @return  The Properties attribute.
	 */
	private Properties getProperties()
	{
		return properties;
	}


	/**
	 *  Gets the file name for the configuration file. Subclasses should
	 *  override this method if they want to get their configuration parameters
	 *  from a unique file name.
	 *
	 * @return  The PropertiesFileName value
	 */
	protected String getPropertiesFileName()
	{
		return (String) null;
	}


	/**
	 *  Gets the keys as a Collection from the Properties object.
	 *
	 * @return  The PropertiesKeys value
	 */
	protected Collection getPropertiesKeys()
	{
		return getProperties().keySet();
	}


	/**
	 *  Get the global path for the location of properties files.
	 *
	 *  @return The path name, or null.
	 */
	protected String getPropertiesPath()
	{
		String returnPath = getProperties().getProperty(PROPERTIES_PATH_KEY);

		if (returnPath == null)
		{
			returnPath = System.getProperty(PROPERTIES_PATH_KEY);
		}

		return returnPath;
	}

	/**
	 *  Gets the values in the Properties object as a collection.
	 *
	 * @return  The PropertiesValues value
	 */
	protected Collection getPropertiesValues()
	{
		return getProperties().values();
	}


	/**
	 *  Gets the Property attribute of the Configurable object, checking the
	 *  chain of the parent configuration if the key is not found within this
	 *  object's properties.
	 *
	 * @param  key Description of Parameter
	 * @return  The Property value
	 */
	private String getProperty(String key)
	{
		String toReturn = getProperties().getProperty(key);

		if (toReturn == null)
		{
			Configuration parent = getParentConfiguration();

			if (parent != null)
			{
				toReturn = parent.getProperty(key);
			}
		}

		return toReturn;
	}


	/**
	 *  Retrieves a property as String based on a requested key.
	 *
	 * @param  key The key string to lookup a property.
	 * @return  A property String, or null if the key is not found.
	 */
	protected String getStringProperty(String key)
	{
		return getProperty(key);
	}


	/**
	 *  Retrieves a property as a String based on a requested key. If the key
	 *  is not found, the defaultValue String is returned.
	 *
	 * @param  key The key string to lookup a property.
	 * @param  defaultValue Description of Parameter
	 * @return  A property String that is looked up, or the default value if
	 *      the key is not found.
	 */
	protected String getStringProperty(String key, String defaultValue)
	{
		String value = getProperties().getProperty(key);

		if (value != null)
		{
			return value;
		}

		return defaultValue;
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setBooleanProperty(String key, Boolean value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setByteProperty(String key, Byte value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}

	/**
	 *  Load a list of properties from an InputStream.
	 *
	 * @param  stream An input stream from which to read the properties.
	 * @exception  IOException On error reading the stream.
	 */
	protected void loadFrom(InputStream stream)
		throws IOException
	{
		getProperties().load(stream);
	}

	/**
	 *  Attempts to load configuration properties from a disk file named by the
	 *  getPropertiesFileName method.
	 *
	 * @return  true if the properties file was loaded successfully.
	 */
	protected boolean loadPropertiesFile()
	{
		try
		{
			String filePath = getPropertiesPath() + getPropertiesFileName();
			InputStream propertyStream = new FileInputStream(filePath);
			loadFrom(propertyStream);
			//System.out.println("Properties successfully loaded from: " + filePath);
			propertyStream.close();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setCharacterProperty(String key, Character value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setDoubleProperty(String key, Double value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setFloatProperty(String key, Float value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, the key gets removed from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setIntegerProperty(String key, Integer value)
	{
		if (value != null)
		{
			setProperty(key, value.toString());
		}
		else
		{
			setProperty(key, null);
		}
	}


	/**
	 *  Set the global path name for the location of properties files.
	 */
	protected void setPropertiesPath(String pathName)
	{
		setProperty(PROPERTIES_PATH_KEY, pathName);
	}


	/**
	 *  Sets a Property named by the key in the property list to value. If the
	 *  key already exists in the list, the old value is overwritten. If the
	 *  value is null, or is a zero-length string, the key gets removed from
	 *  the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	private void setProperty(String key, String value)
	{
		if (value == null | value.length() == 0)
		{
			getProperties().remove(key);
		}
		else
		{
			getProperties().setProperty(key, value);
		}
	}


	/**
	 *  Sets a String Property named by the key in the property list to value.
	 *  If the key already exists in the list, the old value is overwritten. If
	 *  the value is null, or is a zero-length string, the key gets removed
	 *  from the list.
	 *
	 * @param  key The new Property value
	 * @param  value The new Property value
	 */
	protected void setStringProperty(String key, String value)
	{
		setProperty(key, value);
	}

	/**
	 *  Save the list of properties to an OutputStream.
	 *
	 * @param  stream An output stream on which to write the properties.
	 * @exception  IOException On error writing the stream.
	 */
	protected void storeTo(OutputStream stream)
		throws IOException
	{
		getProperties().store(stream, null);
	}
}
