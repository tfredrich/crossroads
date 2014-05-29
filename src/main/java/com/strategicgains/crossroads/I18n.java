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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Responsible for internationalization of text, numbers, dates, times, etc. in
 * a locale-neutral way.
 * 
 * @author toddf
 * @since May 29, 2014
 */
public class I18n
{
	// PROTOCOL: CONTANTS

	private static final int DEFAULT_PRECISION = 2;
	private static final int DEFAULT_CURRENCY_PRECISION = 2;
	private static final int DEFAULT_PERCENT_PRECISION = 3;

	// PROTOCOL: VARIABLES

	private static I18nConfiguration config = I18nConfiguration.getInstance();
	private Locale locale;
	private TimeZone timeZone;

	// PROTOCOL: CONSTRUCTION

	public I18n()
	{
		this(Locale.getDefault(), TimeZone.getDefault());
	}

	public I18n(Locale locale)
	{
		this(locale, TimeZone.getDefault());
	}

	public I18n(Locale locale, TimeZone timeZone)
	{
		super();
		setLocale(locale);
		setTimeZone(timeZone);
	}

	// PROTOCOL: ACCESSING

	/**
	 * Get the locale for this I18n instance.
	 */
	public Locale getLocale()
	{
		return locale;
	}

	/**
	 * get the time zone for this I18n instance.
	 */
	public TimeZone getTimeZone()
	{
		return timeZone;
	}

	/**
	 * Set the locale for this I18n instance.
	 * 
	 * @param locale
	 */
	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}

	/**
	 * Set the time zone for this I18n instance.
	 * 
	 * @param timeZone
	 */
	public void setTimeZone(TimeZone timeZone)
	{
		this.timeZone = timeZone;
	}

	// PROTOCOL: LOCALIZING

	/**
	 * Localize a message via its key.
	 * 
	 * @param i18nKey
	 *            A resource bundle key used to lookup an internationalizable
	 *            string.
	 * @return Internationalized string corresponding to i18nKey, with
	 *         appropriate parameter values substituted if applicable. Never
	 *         null
	 */
	public String localize(String i18nKey)
	{
		return localize(i18nKey, null, null);
	}

	/**
	 * Localize a message with optional parameter substitution.
	 * 
	 * @param i18nKey
	 *            A resource bundle key used to lookup an internationalizable
	 *            string.
	 * @param parameters
	 *            A map containing string values keyed by parameter name. May be
	 *            null.
	 * @return Internationalized string corresponding to i18nKey, with
	 *         appropriate parameter values substituted if applicable. Never
	 *         null.
	 */
	public String localize(String i18nKey, Map<String, String> parameters)
	{
		return localize(i18nKey, null, parameters);
	}

	/**
	 * Localize a message with an index. For instance, if a properties
	 * 
	 * @param i18nKey
	 *            A resource bundle key used to lookup an internationalizable
	 *            string.
	 * @param index
	 *            An index that is appended to i18nKey. Null implies no index.
	 * @return Internationalized string corresponding to i18nKey/index. Never
	 *         null.
	 */
	public String localize(String i18nKey, String index)
	{
		return localize(i18nKey, index, null);
	}

	/**
	 * Localize a message using an optional index and optional parameter
	 * substitution.
	 * 
	 * @param i18nKey
	 *            A resource bundle key used to lookup an internationalizable
	 *            string.
	 * @param index
	 *            An index that is appended to i18nKey. Null implies no index.
	 * @param parameters
	 *            A map containing string values keyed by parameter name. May be
	 *            null.
	 * @return Internationalized string corresponding to i18nKey/index, with
	 *         appropriate parameter values substituted if applicable. Never
	 *         null.
	 */
	public String localize(String i18nKey, String index,
	    Map<String, String> parameters)
	{
		String key = i18nKey;

		if (index != null) key += index;

		String result = key;
		ResourceBundle resourceBundle = getResourceBundle(config
		    .getResourceBundleName());

		if (resourceBundle != null)
		{
			try
			{
				String rawText = resourceBundle.getString(key);
				result = substituteParameters(rawText, parameters);
			}
			catch (MissingResourceException e)
			{
				// Logger.log("Resource Bundle key not found: " + key);
			}
		}
		else
		{
			// Logger.log("Resource Bundle not found: " +
			// config.getResourceBundleName());
		}

		return result;
	}

	/**
	 * Localize a validation error.
	 * 
	 * @param error
	 *            An internationalizable validation error.
	 * @return A localized veresion of the error message. Never null.
	 */
	public String localize(ValidationError error)
	{
		return localize(error.getMessage(), error.getParameters());
	}

	/**
	 * Localize a date with the default (SHORT) style.
	 * 
	 * @param date
	 *            A date. May be null.
	 * @return A localized string representation of the date in short form.
	 *         Never null.
	 * @see DateFormat.SHORT
	 */
	public String localize(Date date)
	{
		return localize(date, DateFormat.SHORT);
	}

	/**
	 * Localize a date using a given style.
	 * 
	 * @param date
	 *            A date. May be null.
	 * @param style
	 *            Date format style according to DateFormat.SHORT,
	 *            DateFormat.LONG, etc.
	 * @return A localized string representation of the date in the requested
	 *         style. Never null.
	 */
	public String localize(Date date, int style)
	{
		String result = "";

		if (date != null)
		{
			DateFormat f = getDateFormat(style);
			result = f.format(date);
		}

		return result;
	}

	/**
	 * Localize an integer.
	 * 
	 * @param number
	 *            An int.
	 * @return A localized string representation of the integer.
	 */
	public String localize(int number)
	{
		NumberFormat f = getIntegerFormat();
		return f.format(number);
	}

	/**
	 * Localize a double using the default precision.
	 * 
	 * @param number
	 *            A double.
	 * @return A localized string representation of the number, with default
	 *         precision.
	 */
	public String localize(double number)
	{
		return localize(number, DEFAULT_PRECISION);
	}

	/**
	 * Localize a double with the specifice precision (decimal places).
	 * 
	 * @param number
	 *            A double.
	 * @param precision
	 *            The number of decimal places to show.
	 * @return A localized string representation of the number, with the
	 *         requested precision.
	 */
	public String localize(double number, int precision)
	{
		NumberFormat f = getNumberFormat(precision);
		return f.format(number);
	}

	/**
	 * Localize a double value, representing it as a currency (with currency
	 * indicator). Uses the default precision.
	 * 
	 * @param number
	 * @return
	 */
	public String localizeAsCurrency(double number)
	{
		return localizeAsCurrency(number, DEFAULT_CURRENCY_PRECISION);
	}

	/**
	 * Localize a double value as a currency, with the specified precision.
	 * 
	 * @param number
	 * @param precision
	 * @return
	 */
	public String localizeAsCurrency(double number, int precision)
	{
		NumberFormat f = getCurrencyFormat(precision);
		return f.format(number);
	}

	/**
	 * Localize a double as a percentage (with percentage indicator). Uses the
	 * default precision.
	 * 
	 * @param number
	 * @return
	 */
	public String localizeAsPercent(double number)
	{
		return localizeAsPercent(number, DEFAULT_PERCENT_PRECISION);
	}

	/**
	 * Localize a double as a percentage with the specified precision.
	 * 
	 * @param number
	 * @param precision
	 * @return
	 */
	public String localizeAsPercent(double number, int precision)
	{
		NumberFormat f = getPercentFormat(precision);
		return f.format(number);
	}

	// PROTOCOL: PARSING

	/**
	 * Parse a localized string into a BigDecimal.
	 */
	public BigDecimal parseBigDecimal(String bigDecimalString)
	throws ParseException
	{
		BigDecimal result = null;

		if (bigDecimalString != null && bigDecimalString.trim().length() > 0)
		{
			NumberFormat f = getNumberFormat();
			result = new BigDecimal(f.parse(bigDecimalString).toString());
		}

		return result;
	}

	/**
	 * Parse a localized date string (in SHORT format) into a Date.
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 *             if the string is an invalid SHORT format date.
	 */
	public Date parseDate(String dateString) throws ParseException
	{
		Date result = null;

		if (dateString != null && dateString.trim().length() > 0)
		{
			DateFormat f = getDateFormat(DateFormat.SHORT);
			result = f.parse(dateString);
		}

		return result;
	}

	/**
	 * Parse a localized date string (with time portion) into a Date.
	 * 
	 * @param dateString
	 * @return Date
	 * @throws ParseException
	 *             if the string is an invalid date.
	 */
	public Date parseDateTime(String dateString) throws ParseException
	{
		Date result = null;

		if (dateString != null && dateString.trim().length() > 0)
		{
			DateFormat f = getDateTimeFormat();
			result = f.parse(dateString);
		}

		return result;
	}

	/**
	 * Parse a localized string into a Double.
	 * 
	 * @param doubleString
	 * @return Double
	 * @throws ParseException
	 */
	public Double parseDouble(String doubleString) throws ParseException
	{
		Double result = null;

		if (doubleString != null && doubleString.trim().length() > 0)
		{
			NumberFormat f = getNumberFormat();
			result = new Double(f.parse(doubleString).toString());
		}

		return result;
	}

	/**
	 * Parse a localized string into an Integer.
	 * 
	 * @param integerString
	 * @return Integer
	 * @throws ParseException
	 */
	public Integer parseInteger(String integerString) throws ParseException
	{
		Integer result = null;

		if (integerString != null && integerString.trim().length() > 0)
		{
			NumberFormat f = getIntegerFormat();
			result = new Integer(f.parse(integerString).toString());
		}

		return result;
	}

	// PROTOCOL: UTILITY - PUBLIC

	/**
	 * Converts all of the characters in the string to lower case using the
	 * rules of the locale.
	 * 
	 * @param string
	 *            The string to convert to lower case.
	 * @return The string, converted to lower case.
	 */
	public String toLowerCase(String string)
	{
		return string.toLowerCase(getLocale());
	}

	/**
	 * Converts all of the characters in the string to upper case using the
	 * rules of the locale.
	 * 
	 * @param string
	 *            The string to convert to upper case.
	 * @return The string, converted to upper case.
	 */
	public String toUpperCase(String string)
	{
		return string.toUpperCase(getLocale());
	}

	// PROTOCOL: UTILITY - RESOURCE BUNDLE

	/**
	 * @param baseBundleName
	 * @return ResourceBundle
	 */
	private ResourceBundle getResourceBundle(String baseBundleName)
	{
		return ResourceBundle.getBundle(baseBundleName, getLocale());
	}

	// PROTOCOL: UTILITY - FORMAT

	/**
	 * @param precision
	 * @return NumberFormat
	 */
	private NumberFormat getCurrencyFormat(int precision)
	{
		NumberFormat f = NumberFormat.getCurrencyInstance(getLocale());
		setFormatPrecision(f, precision);
		return f;
	}

	/**
	 * @return DateFormat for the given style.
	 */
	private DateFormat getDateFormat(int style)
	{
		DateFormat f = DateFormat.getDateInstance(style, getLocale());
		f.setTimeZone(getTimeZone());
		return f;
	}

	/**
	 * @return
	 */
	private DateFormat getDateTimeFormat()
	{
		return DateFormat.getDateTimeInstance(DateFormat.SHORT,
		    DateFormat.SHORT, getLocale());
	}

	/**
	 * @return NumberFormat
	 */
	private NumberFormat getIntegerFormat()
	{
		return NumberFormat.getIntegerInstance(getLocale());
	}

	/**
	 * @return NumberFormat
	 */
	private NumberFormat getNumberFormat()
	{
		return NumberFormat.getNumberInstance(getLocale());
	}

	/**
	 * @param precision
	 * @return NumberFormat
	 */
	private NumberFormat getNumberFormat(int precision)
	{
		NumberFormat f = NumberFormat.getNumberInstance(getLocale());
		setFormatPrecision(f, precision);
		return f;
	}

	/**
	 * @param precision
	 * @return
	 */
	private NumberFormat getPercentFormat(int precision)
	{
		NumberFormat f = NumberFormat.getPercentInstance(getLocale());
		setFormatPrecision(f, precision);
		return f;
	}

	// PROTOCOL: UTILITY - PRIVATE

	private void setFormatPrecision(NumberFormat format, int precision)
	{
		format.setMinimumFractionDigits(precision);
		format.setMaximumFractionDigits(precision);
	}

	private String substituteParameters(String string,
	    Map<String, String> parameters)
	{
		String result = string;

		if (parameters != null)
		{
			MapMessageFormat f = new MapMessageFormat();
			result = f.format(string, parameters);
		}

		return result;
	}
}