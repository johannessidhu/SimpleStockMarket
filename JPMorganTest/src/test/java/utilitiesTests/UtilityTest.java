package utilitiesTests;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utilities.Utility;

public class UtilityTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testStringCheckerDash() {
		assertEquals(true, Utility.isValidString(" - "));
	}
	
	@Test
	public void testStringCheckerEmpty() {
		assertEquals(false, Utility.isValidString(""));
	}
	
	@Test
	public void testStringCheckerWhitespace() {
		assertEquals(false, Utility.isValidString("  "));
	}
	
	@Test
	public void testStringCheckerNull() {
		assertEquals(false, Utility.isValidString(null));
	}

	@Test
	public void testValidConfigurationFileIntegerEntryTrue() {
		assertEquals(true, Utility.isValidConfigurationFileIntegerEntry("123"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryOverflow() {
		String maxPlusOne = String.valueOf(Integer.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, Utility.isValidConfigurationFileIntegerEntry(maxPlusOne));
	}

	@Test
	public void testValidConfigurationFileIntegerEntryNegativeNumber() {
		assertEquals(false, Utility.isValidConfigurationFileIntegerEntry("-123"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryEmpty() {
		assertEquals(false, Utility.isValidConfigurationFileIntegerEntry(""));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryNaN() {
		assertEquals(false, Utility.isValidConfigurationFileIntegerEntry("Text"));
	}
	
	@Test
	public void testValidConfigurationFileIntegerEntryNull() {
		assertEquals(false, Utility.isValidConfigurationFileIntegerEntry(null));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryTrue() {
		String maxInteger = String.valueOf(Integer.MAX_VALUE);

		assertEquals(true, Utility.isValidConfigurationFileLongEntry(maxInteger));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryOverflow() {
		String maxPlusOne = String.valueOf(Long.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, Utility.isValidConfigurationFileLongEntry(maxPlusOne));
	}

	@Test
	public void testValidConfigurationFileLongEntryNegativeNumber() {
		assertEquals(false, Utility.isValidConfigurationFileLongEntry("-123"));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryEmpty() {
		assertEquals(false, Utility.isValidConfigurationFileLongEntry(""));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryNaN() {
		assertEquals(false, Utility.isValidConfigurationFileLongEntry("Text"));
	}
	
	@Test
	public void testValidConfigurationFileLongEntryNull() {
		assertEquals(false, Utility.isValidConfigurationFileLongEntry(null));
	}
	
	@After
	public void tearDown() {
	}

}
