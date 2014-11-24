package utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Class provide some helper methods that are used throughout this project i.e. cross cutting concerns that reappear 
 * @author Johannes Sidhu
 * */
public class Utility {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(Utility.class);	

	/**
	 * Empty constructor
	 */
	public Utility() {
	
	}

	/**
	 * Utility method for checking input from the Apache configuration file
	 * @param string
	 * @return true if the string entered is not null, is not empty and does not consist of only white space 
	 */
	public static boolean stringChecker(final String string) {  
		return (string != null && !string.isEmpty() && !string.trim().isEmpty());  
	}

	/**
	 * Utility method to check if a string is a valid integer for configuring the ResourceScheduler
	 * @param inputNumberString
	 * @return true if the input string is a valid number (for configuring the ResourceScheduler)
	 */
	public static boolean validConfigurationFileIntegerEntry(String inputNumberString){

		if(!stringChecker(inputNumberString)) {
			LOGGER.error("The input string is either empty or null or only consist of empty space.");
			return false;
		}
		else {
			try {

				if(Integer.parseInt(inputNumberString) < 0) {
					LOGGER.error("For this application the assumption is all configuration numbers must be greater or equal to 0.");
					return false;			}

			} catch (NumberFormatException e) {
				LOGGER.error("Input number in the loaded configuration file is NaN.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Utility method to check if a string is a valid integer for configuring the ResourceScheduler
	 * @param inputNumberString
	 * @return true if the input string is a valid number (for configuring the ResourceScheduler)
	 */
	public static boolean validConfigurationFileLongEntry(String inputNumberString){

		if(!stringChecker(inputNumberString)) {
			LOGGER.error("The input string is either empty or null or only consist of empty space.");
			return false;
		}
		else {
			try {

				if(Long.parseLong(inputNumberString) < 0) {
					LOGGER.error("For this application the assumption is all configuration numbers must be greater or equal to 0.");
					return false;			}

			} catch (NumberFormatException e) {
				LOGGER.error("Input number in the loaded configuration file is NaN.");
				return false;
			}
		}
		return true;
	}

	
	
}
