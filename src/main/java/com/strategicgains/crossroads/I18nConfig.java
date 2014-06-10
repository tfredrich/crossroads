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

/**
 * @author toddf
 * @since Jun 10, 2014
 */
public class I18nConfig
{
	private final static String RESOURCE_BUNDLE_BASE_NAME_KEY = "i18n.baseName";
	private final static String RESOURCE_BUNDLE_PATH_KEY = "i18n.bundlePath";
	private final static String RESOURCE_BUNDLE_BASE_NAME_DEFAULT = "I18n";

	private String baseName;
	private String bundlePath;

	public I18nConfig(Properties p)
	{
		super();
		initialize(p);
	}

	protected void initialize(Properties p)
    {
		baseName = p.getProperty(RESOURCE_BUNDLE_BASE_NAME_KEY, RESOURCE_BUNDLE_BASE_NAME_DEFAULT);
		bundlePath = p.getProperty(RESOURCE_BUNDLE_PATH_KEY);
    }

	public String getBaseName()
	{
		return baseName;
	}

	public boolean hasBundlePath()
	{
		return (bundlePath != null);
	}
	public String getBundlePath()
	{
		return bundlePath;
	}
}
