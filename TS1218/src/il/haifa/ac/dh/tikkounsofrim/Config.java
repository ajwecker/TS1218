package il.haifa.ac.dh.tikkounsofrim;

import java.util.*;

/**
 * Tikkoun Sofrim config.
 * @author urischor
 *
 */
public class Config {

	/** Name of resource bundle */
	private static final String BUNDLE_NAME = "tikkoun";

	/** Resource bundle */
	private static final ResourceBundle RESOURCE_BUNDLE =
		ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Get a resource string.
	 * @param key name of resource
	 * @return String the resource string corresponding to the given key
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * Get a resource integer.
	 * @param key name of resource
	 * @key defaultVal The default value if this key does not contain an integer
	 * @return The resource integer corresponding to the given key
	 */
	public static int getInt(String key, int defaultVal) {
		String strVal = getString(key);
		if (strVal != null) {
			try {
				return Integer.parseInt(strVal);
			} catch (NumberFormatException e) {					
			}			
		}
		return defaultVal;
	}

	/**
	 * Get a resource flag.
	 * @param key name of resource
	 * @return bolean <code>true</code> if resource equals (case insensitive) "true",
	 * <code>false</code> otherwise.
	 */
	public static Boolean getBoolean(String key) {
		try {
			String strVal = RESOURCE_BUNDLE.getString(key);
			return new Boolean(strVal);
		}
		catch (MissingResourceException e) {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * Creates a new Strings object to manage resource strings.
	 */
	private Config() {
	}
}
