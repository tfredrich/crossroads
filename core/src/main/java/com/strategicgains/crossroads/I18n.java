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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wraps ResourceBundle instances and MessageFormat to create a single point for internationalization
 * of string patterns.
 * 
 * @author toddf
 * @since Jun 5, 2014
 * @see MessageFormat
 */
public class I18n
{
	private static final Logger LOGGER = LoggerFactory.getLogger(I18n.class);
	private static final I18n INSTANCE = new I18n();

	private String baseName;
	private ClassLoader loader=null;
	
	private I18n()
	{
		super();
	}

	/**
	 * Initialize the Singleton I18n instance with the given I18nConfig.
	 * 
	 * @param config an {@link I18nConfig} instance.
	 * @throws MalformedURLException if i81n.bundlePath value is not valid.
	 * @see I18nConfig
	 */
	public static void initialize(I18nConfig config)
	throws MalformedURLException
	{
		INSTANCE._init(config);
	}

	/**
	 * Configure the ResourceBundle base name for the Singleton instance of I18n.
	 * 
	 * @param baseName a ResourceBundle base name.
	 * @see ResourceBundle
	 */
	public static void setBaseName(String baseName)
	{
		INSTANCE._setBaseName(baseName);
	}

	/**
	 * Typically, {@link ResourceBundle}s are loaded from the ClassPath. However, some
	 * applications prefer to load {@link ResourceBundle}s from the file system so
	 * they can be deployed and updated separately from the application.
	 * <p>
	 * By setting a bundlePath, the directory in which {@link ResourceBundle}s live,
	 * I18n will load resource bundles from that file location instead of from the classpath.
	 * </p>
	 * @param bundlePath A directory (including trailing slash) path to the {@link ResourceBundle} location. May be null to force I18n to load {@link ResourceBundle}s from the classpath.
	 * @throws MalformedURLException if the bundlePath is not a valid path.
	 */
	public static void setBundlePath(String bundlePath)
	throws MalformedURLException
	{
		INSTANCE._setBundlePath(bundlePath);
	}

	/**
	 * Localize a key for the given locale and parameters.
	 * 
	 * @param i18nKey a key in a {@link ResourceBundle} file
	 * @param locale the locale to use when localizing the output.
	 * @param parameters An object array to use in substituting patterns in the resource bundle value.
	 * @return a localized string or the i18nKey if it is not found in a resource bundle file.
	 */
	public static String localize(String i18nKey, Locale locale, Object... parameters)
	{
		return INSTANCE._localize(i18nKey, locale, parameters);
	}

	private void _init(I18nConfig config)
	throws MalformedURLException
	{
		_setBaseName(config.getBaseName());
		
		if (config.hasBundlePath())
		{
			_setBundlePath(config.getBundlePath());
		}
	}

	private void _setBaseName(String baseName)
	{
		this.baseName = baseName;
	}

	private void _setBundlePath(String bundlePath)
	throws MalformedURLException
	{
		if (bundlePath == null)
		{
			this.loader = null;
			return;
		}

		URL url = new File(bundlePath).toURI().toURL();
		this.loader = new URLClassLoader(new URL[]{url});
	}

	private String _localize(String i18nKey, Locale locale, Object... parameters)
	{
		ResourceBundle resourceBundle = _getBundle(locale);

		try
		{
			String pattern = resourceBundle.getString(i18nKey);
			MessageFormat format = new MessageFormat(pattern, locale);
			return format.format(parameters);
		}
		catch (MissingResourceException e)
		{
			LOGGER.warn("Resource Bundle key not found: " + i18nKey);
			return i18nKey;
		}
	}

	private ResourceBundle _getBundle(Locale locale)
    {
		if (loader == null)
		{
			return ResourceBundle.getBundle(baseName, locale);
		}

		return ResourceBundle.getBundle(baseName, locale, loader);
    }
}
