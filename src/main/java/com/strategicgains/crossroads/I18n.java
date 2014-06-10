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
 * Wraps ResourceBundle instances and MessageFormat to create a single point of internationalization
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

	public static void initialize(I18nConfig config)
	{
		INSTANCE._init(config);
	}

	public static void setBaseName(String baseName)
	{
		INSTANCE._setBaseName(baseName);
	}

	public static void setBundlePath(String bundlePath)
	throws MalformedURLException
	{
		INSTANCE._setBundlePath(bundlePath);
	}

	public static String localize(String i18nKey, Locale locale, Object... parameters)
	{
		return INSTANCE._localize(i18nKey, locale, parameters);
	}

	private void _init(I18nConfig config)
	{
		_setBaseName(config.getBaseName());
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
