package com.strategicgains.crossroads;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class I18nConfigTest
{
	@Test
	public void shouldSetDefaults()
	{
		I18nConfig config = new I18nConfig(new Properties());
		assertEquals("I18n", config.getBaseName());
		assertFalse(config.hasBundlePath());
	}

	@Test
	public void shouldSetUserDefinedBaseName()
	{
		Properties p = new Properties();
		p.put("i18n.baseName", "ResourceBundle");
		I18nConfig config = new I18nConfig(p);
		assertEquals("ResourceBundle", config.getBaseName());
	}

	@Test
	public void shouldSetUserDefinedBundlePath()
	{
		Properties p = new Properties();
		p.put("i18n.bundlePath", "/home/toddf/i18n/");
		I18nConfig config = new I18nConfig(p);
		assertEquals("/home/toddf/i18n/", config.getBundlePath());
	}
}
