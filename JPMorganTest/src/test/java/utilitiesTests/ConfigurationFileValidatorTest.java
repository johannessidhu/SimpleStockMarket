package utilitiesTests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import utilities.ConfigurationFileValidator;

public class ConfigurationFileValidatorTest {

	@Test
	public void testStringCheckerDash() {
		assertEquals(true, ConfigurationFileValidator.isValidString(" - "));
	}
	
	@Test
	public void testStringCheckerEmpty() {
		assertEquals(false, ConfigurationFileValidator.isValidString(""));
	}
	
	@Test
	public void testStringCheckerWhitespace() {
		assertEquals(false, ConfigurationFileValidator.isValidString("  "));
	}
	
	@Test
	public void testStringCheckerNull() {
		assertEquals(false, ConfigurationFileValidator.isValidString(null));
	}

	@Test
	public void testValidConfigurationFileIntegerEntryTrue() {
		assertEquals(true, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry("123"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryOverflow() {
		String maxPlusOne = String.valueOf(Integer.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry(maxPlusOne));
	}

	@Test
	public void testValidConfigurationFileIntegerEntryNegativeNumber() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry("-123"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryEmpty() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry(""));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryNaN() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry("Text"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryNull() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileIntegerEntry(null));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryTrue() {
		String maxInteger = String.valueOf(Integer.MAX_VALUE);

		assertEquals(true, ConfigurationFileValidator.isValidConfigurationFileLongEntry(maxInteger));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryOverflow() {
		String maxPlusOne = String.valueOf(Long.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileLongEntry(maxPlusOne));
	}

	@Test
	public void testValidConfigurationFileLongEntryNegativeNumber() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileLongEntry("-123"));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryEmpty() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileLongEntry(""));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryNaN() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileLongEntry("Text"));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryNull() {
		assertEquals(false, ConfigurationFileValidator.isValidConfigurationFileLongEntry(null));
	}

}
