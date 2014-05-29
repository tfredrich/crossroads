/*
 * Copyright 2014, Strategic Gains, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strategicgains.crossroads;

/**
 * @author toddf
 * @since May 29, 2014
 */
public class I18nConfiguration
extends Configuration
{
// PROTOCOL: CONSTANTS

private final static String PROPERTIES_FILE_NAME = "I18n.properties";
private final static String RESOURCE_BUNDLE_BASE_NAME_KEY = "i18n.resourceBundleBaseName";
private final static String RESOURCE_BUNDLE_BASE_NAME_DEFAULT = "I18nResourceBundle";

// PROTOCOL: VARIABLES

private static I18nConfiguration instance = null;

// PROTOCOL: CONFIGURATION

public I18nConfiguration()
{
	super();
}

/**
 *  Gets the PropertiesFileName attribute of the ApplicationProperties
 *  object
 *
 * @return  The PropertiesFileName value
 */
protected String getPropertiesFileName()
{
	return PROPERTIES_FILE_NAME;
}

/**
 *  Gets the singleton instance of the I18nConfiguration class.
 *
 * @return  A reference to the singleton instance of I18nConfiguration.
 */
public static I18nConfiguration getInstance()
{
	if (instance == null)
	{
		instance = new I18nConfiguration();
	}

	return instance;
}

// PROTOCOL: ACCESSING

/**
 * @return
 */
public String getResourceBundleName()
{
	return getStringProperty(RESOURCE_BUNDLE_BASE_NAME_KEY, RESOURCE_BUNDLE_BASE_NAME_DEFAULT);
}
}