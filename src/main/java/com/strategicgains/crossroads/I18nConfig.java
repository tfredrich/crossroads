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

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Provides accessing methods for properties values, baseName and bundlePath,
 * which are used in the Singleton instance of I18n.
 * 
 * @author toddf
 * @since Jun 10, 2014
 * @see I18n
 */
public class I18nConfig
{
	private final static String RESOURCE_BUNDLE_BASE_NAME_KEY = "i18n.baseName";
	private final static String RESOURCE_BUNDLE_PATH_KEY = "i18n.bundlePath";
	private final static String RESOURCE_BUNDLE_BASE_NAME_DEFAULT = "I18n";

	private String baseName;
	private String bundlePath;

	/**
	 * Construct a new I18nConfig instance, using the configuration values from a Properties instance.
	 * 
	 * @param p configuration values.
	 */
	public I18nConfig(Properties p)
	{
		super();
		initialize(p);
	}

	/**
	 * Set the configuration values from a Properties instance.
	 * 
	 * @param p configuration values.
	 */
	protected void initialize(Properties p)
    {
		baseName = p.getProperty(RESOURCE_BUNDLE_BASE_NAME_KEY, RESOURCE_BUNDLE_BASE_NAME_DEFAULT);
		bundlePath = p.getProperty(RESOURCE_BUNDLE_PATH_KEY);
    }

	/**
	 * Get the ResourceBundle base name.
	 * 
	 * @return the baseName for the resource bundles.
	 * @see ResourceBundle
	 */
	public String getBaseName()
	{
		return baseName;
	}

	/**
	 * Answer whether the configuration has a bundlePath property.
	 * 
	 * @return true if the bundlePath property is set, otherwise false.
	 */
	public boolean hasBundlePath()
	{
		return (bundlePath != null);
	}

	/**
	 * Typically, {@link ResourceBundle}s are loaded from the ClassPath. However, many
	 * applications prefer to load {@link ResourceBundle}s from the file system so
	 * they can be deployed and updated separately from the application.
	 * <p/>
	 * By setting a bundlePath, the directory in which {@link ResourceBundle}s live,
	 * I18n will load resource bundles from that file location instead of from the classpath.
	 * 
	 * @return the value of the bundlePath setting. Possibly null.
	 */
	public String getBundlePath()
	{
		return bundlePath;
	}
}
