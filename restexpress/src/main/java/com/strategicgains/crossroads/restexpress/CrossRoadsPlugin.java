/*
    Copyright 2015, Strategic Gains, Inc.

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
package com.strategicgains.crossroads.restexpress;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.Locale;

import org.restexpress.Request;
import org.restexpress.RestExpress;
import org.restexpress.pipeline.Preprocessor;
import org.restexpress.plugin.AbstractPlugin;

/**
 * Parses the Accept-Language header on each request, determining which Locale
 * to use for localization and sets the Locale as an attachment on the request.
 * The attachment is accessed via a call to:
 * 
 * Request.getAttachement(CrossRoadsPlugin.REQUEST_LOCALE);
 * 
 * @author tfredrich
 * @since Jul 22, 2015
 */
public class CrossRoadsPlugin
extends AbstractPlugin
{
	public static final String REQUEST_LOCALE = "locale";

	public Locale defaultLocale = Locale.getDefault();

	/**
	 * If there is no Accept-Language header on the request, the
	 * Locale set here is used as the default.
	 * 
	 * @param locale the desired Locale to use when no Accept-Language header is present.
	 * @return this plugin.
	 */
	public CrossRoadsPlugin setDefault(Locale locale)
	{
		this.defaultLocale = locale;
		return this;
	}

	/**
	 * If there is no Accept-Language header on the request, the
	 * languageTag set here is used to create a Locale to use as the default.
	 * 
	 * This method is an alternative to setDefault(Locale).
	 * 
	 * @param languageTag a string describing the desired Locale to use when no Accept-Language header is present.
	 * @return this plugin.
	 */
	public CrossRoadsPlugin setDefault(String languageTag)
	{
		this.defaultLocale = Locale.forLanguageTag(languageTag);
		return this;
	}

	/**
	 * Binds the plugin to the running server, installing the Accept-Language preprocessor.
	 * 
	 * @param server the RestExpress server.
	 */
	@Override
    public void bind(RestExpress server)
    {
		server.addPreprocessor(new LanguagePreprocessor(defaultLocale));
    }

	/**
	 * Parses each request to determine if there is an Accept-Lanaguge header.
	 * If so, determines the Locale corresponding to it and attaches it to the Request.
	 * Otherwise, attaches the 'default' locale to the request.
	 * 
	 * @author tfredrich
	 * @since Jul 22, 2015
	 */
	public class LanguagePreprocessor
	implements Preprocessor
	{
		private Locale defaultLocale;

		public LanguagePreprocessor(Locale defaultLocale)
		{
			super();
			this.defaultLocale = defaultLocale;
		}

		@Override
        public void process(Request request)
        {
			Locale locale = getLocale(request);
			request.putAttachment(REQUEST_LOCALE, locale);
        }

		/**
		 * Returns the preferred Locale that the client will accept content in, based on the Accept-Language header.
		 * If the client request doesn't provide an Accept-Language header, this method returns the default locale for the server.
		 * 
		 * @return the preferred Locale for the client
		 */
		public Locale getLocale(Request request)
		{
			String language = request.getHeader(HttpHeaders.Names.ACCEPT_LANGUAGE);

			if (language == null)
			{
				return defaultLocale;
			}

			 return Locale.forLanguageTag(language);
		}
	}
}
