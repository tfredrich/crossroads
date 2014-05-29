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

import java.util.Map;

/**
 * @author toddf
 * @since May 29, 2014
 */
public class ValidationError
{
	private Object invalidObject;
	private String message;
	private Map<String, String> parameters;

	public ValidationError(Object object, String message)
	{
		this(object, message, null);
	}

	/**
	 * @param message
	 *            A String to describe the error.
	 * @param parameters
	 *            A HashMap of substitution parameters. May be null.
	 */
	public ValidationError(Object object, String message, Map<String, String> parameters)
	{
		super();
		setMessage(message);
		setInvalidObject(object);
		this.parameters = parameters;
	}

	public Object getInvalidObject()
	{
		return invalidObject;
	}

	public String getMessage()
	{
		return message;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setInvalidObject(Object object)
	{
		this.invalidObject = object;
	}

	/**
	 * @param i18nKey The value to use in setting messageKey.
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
}