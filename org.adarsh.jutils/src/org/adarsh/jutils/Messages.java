/*
 * JUtils ToString Generator for Eclipse
 * 
 * Copyright (C) 2007  Adarsh Ramamurthy
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 * 
 * Plugin Home Page: http://eclipse-jutils.sourceforge.net
 */

package org.adarsh.jutils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Acts as a helper for fetching messages from the externalized resource file.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 10th December 2006
 */
public class Messages {
	/**
	 * The bundle name.
	 */
	private static final String BUNDLE_NAME = "org.adarsh.jutils.messages";

	/**
	 * The <tt>ResourceBundle</tt> associated with the bundle.
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Fetches the string property given the key.
	 * 
	 * @param key
	 *            the key.
	 * 
	 * @return a <tt>String</tt> containing the value of the key.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
