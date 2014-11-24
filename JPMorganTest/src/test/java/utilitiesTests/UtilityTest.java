package utilitiesTests;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utilities.Utility;



/**
 * Unit tests for StringMessage
 */
public class UtilityTest {

	

	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Test the stringChecker() method, dash
	 * */
	@Test
	public void testStringCheckerDash() {
		
		assertEquals(true, Utility.stringChecker(" - "));
	}
	
	/**
	 * Test the stringChecker() method, empty string
	 * */
	@Test
	public void testStringCheckerEmpty() {
		
		assertEquals(false, Utility.stringChecker(""));
	}
	
	/**
	 * Test the stringChecker() method, whitespace string
	 * */
	@Test
	public void testStringCheckerWhitespace() {
		
		assertEquals(false, Utility.stringChecker("  "));
	}
	
	/**
	 * Test the stringChecker() method, null string
	 * */
	@Test
	public void testStringCheckerNull() {
		
		assertEquals(false, Utility.stringChecker(null));
	}

	/**
	 * Test the validConfigurationFileIntegerEntry() method, successful
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryTrue() {
		
		assertEquals(true, Utility.validConfigurationFileIntegerEntry("123"));
	}
	
	
	/**
	 * Test the validConfigurationFileIntegerEntry() method, Integer.MAX_VALUE+1 -- overflow.
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryOverflow() {
		
		String maxPlusOne = String.valueOf(Integer.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, Utility.validConfigurationFileIntegerEntry(maxPlusOne));
	}

	
	/**
	 * Test the validConfigurationFileIntegerEntry() method, negative number
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryNegativeNumber() {
		
		assertEquals(false, Utility.validConfigurationFileIntegerEntry("-123"));
	}
	
	/**
	 * Test the validConfigurationFileIntegerEntry() method, empty string
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryEmpty() {
		
		assertEquals(false, Utility.validConfigurationFileIntegerEntry(""));
	}
	
	/**
	 * Test the validConfigurationFileIntegerEntry() method, NaN string
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryNaN() {
		
		assertEquals(false, Utility.validConfigurationFileIntegerEntry("Text"));
	}
	
	/**
	 * Test the validConfigurationFileIntegerEntry() method, null string
	 * */
	@Test
	public void testValidConfigurationFileIntegerEntryNull() {
		
		assertEquals(false, Utility.validConfigurationFileIntegerEntry(null));
	}
	
	/**
	 * Test the validConfigurationFileLongEntry() method, successful and chek if it handles Integer_Max
	 * */
	@Test
	public void testValidConfigurationFileLongEntryTrue() {
		
		String maxInteger = String.valueOf(Integer.MAX_VALUE);

		assertEquals(true, Utility.validConfigurationFileLongEntry(maxInteger));
	}
	
	
	/**
	 * Test the validConfigurationFileLongEntry() method, Long.MAX_VALUE+1 
	 * */
	@Test
	public void testValidConfigurationFileLongEntryOverflow() {
		
		String maxPlusOne = String.valueOf(Long.MAX_VALUE+1);
		System.out.println(maxPlusOne);
		
		assertEquals(false, Utility.validConfigurationFileLongEntry(maxPlusOne));
	}

	
	/**
	 * Test the validConfigurationFileLongEntry() method, negative number
	 * */
	@Test
	public void testValidConfigurationFileLongEntryNegativeNumber() {
		
		assertEquals(false, Utility.validConfigurationFileLongEntry("-123"));
	}
	
	/**
	 * Test the validConfigurationFileLongEntry() method, empty string
	 * */
	@Test
	public void testValidConfigurationFileLongEntryEmpty() {
		
		assertEquals(false, Utility.validConfigurationFileLongEntry(""));
	}
	
	/**
	 * Test the validConfigurationFileLongEntry() method, NaN string
	 * */
	@Test
	public void testValidConfigurationFileLongEntryNaN() {
		
		assertEquals(false, Utility.validConfigurationFileLongEntry("Text"));
	}
	
	/**
	 * Test the validConfigurationFileLongEntry() method, null string
	 * */
	@Test
	public void testValidConfigurationFileLongEntryNull() {
		
		assertEquals(false, Utility.validConfigurationFileLongEntry(null));
	}
	
	@After
	public void tearDown() {
	}

}
