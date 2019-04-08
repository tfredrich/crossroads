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

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author toddf
 * @since Jun 5, 2014
 */
public class I18nTest
{
	@BeforeClass
	public static void before()
	throws MalformedURLException
	{
		I18n.setBaseName("SampleBundle");
		I18n.setBundlePath("src/test/resources/i18n/");
	}

	@Test
	public void shouldLocalizeNonExistentKey()
	{
		assertEquals("foo", I18n.localize("foo", Locale.US, 1, "two", 3.0));
	}

	@Test
	public void shouldLocalizeKey()
	{
		assertEquals("do you speak french?", I18n.localize("s5", Locale.US));
		assertEquals("parlez-vous français?", I18n.localize("s5", Locale.FRANCE));
		assertEquals("sprechen Sie französisch?", I18n.localize("s5", Locale.GERMANY));
		assertEquals("do you speak french?", I18n.localize("s5", Locale.CHINA));
	}

	@Test
	public void shouldPerformLocalizedSubstitution()
	{
		Calendar cal = new GregorianCalendar(2014, 0, 1, 14, 15, 31);
		assertEquals("At 2:15:31 PM on Jan 1, 2014, there was a disturbance in the Force on planet 7.", 
			I18n.localize("s6", Locale.US, 7, cal.getTime(), "a disturbance in the Force"));

		assertEquals("At 14:15:31 on 1 janv. 2014, there was a disturbance in the Force on planet 7.", 
			I18n.localize("s6", Locale.FRANCE, 7, cal.getTime(), "a disturbance in the Force"));

		assertEquals("At 14:15:31 on 01.01.2014, there was a disturbance in the Force on planet 7.", 
			I18n.localize("s6", Locale.GERMANY, 7, cal.getTime(), "a disturbance in the Force"));
	}

	@Test
	public void shouldPerformCurrencySubstitution()
	{
		Double amount = Double.valueOf(1043.56789);
		assertEquals("The currency amount is $1,043.57 this month.",
			I18n.localize("s7", Locale.US, amount));

		assertEquals("The currency amount is $1,043.568 this month.",
			I18n.localize("s8", Locale.US, amount));

		assertEquals("The currency amount is £1,043.57 this month.",
			I18n.localize("s7", Locale.UK, amount));
		
		assertEquals("La quantité de monnaie est 1 043,57\u00a0€ ce mois-ci.", 
			I18n.localize("s7", Locale.FRANCE, amount));

		assertEquals("Der Währungsbetrag ist 1.043,57\u00a0€ in diesem Monat.", 
			I18n.localize("s7", Locale.GERMANY, amount));
	}
}
