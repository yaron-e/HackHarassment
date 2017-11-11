package com.Shared;

public class StringUtil {
	
	/**
	 * Checks if the string is null
	 * 
	 * @param s
	 * @return True is string is blank
	 * 		   False is String is NOT blank
	 */
	public static boolean isBlank(String s) {
		if (s == null || s.isEmpty() || s.equals("") || s.equals(" ")) {
			return true;
		}
		return false;
	}

}
