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

	public static boolean isValidString(final String string) {  
		return (string != null && !string.isEmpty() && !string.trim().isEmpty());  
	}

	public static boolean isValidConfigurationFileIntegerEntry(String inputNumberString){
		if (!isValidString(inputNumberString)) {
			LOGGER.error("The input string is either empty or null or only consist of empty space.");
			return false;
		} else {
			try {
				if (Integer.parseInt(inputNumberString) < 0) {
					LOGGER.error("For this application the assumption is all configuration numbers must be greater or equal to 0.");
					return false;			}

			} catch (NumberFormatException e) {
				LOGGER.error("Input number in the loaded configuration file is NaN.");
				return false;
			}
		}

		return true;
	}

	public static boolean isValidConfigurationFileLongEntry(String inputNumberString) {
		if (!isValidString(inputNumberString)) {
			LOGGER.error("The input string is either empty or null or only consist of empty space.");
			return false;
		} else {
			try {
				if (Long.parseLong(inputNumberString) < 0) {
					LOGGER.error("For this application the assumption is all configuration numbers must be greater or equal to 0.");
					return false;			
					}
			} catch (NumberFormatException e) {
				LOGGER.error("Input number in the loaded configuration file is NaN.");
				return false;
			}
		}

		return true;
	}

}