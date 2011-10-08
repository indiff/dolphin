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

package org.adarsh.jutils.internal;

/**
 * Contains constants which directly represent the tag and attribute names of
 * the configuration XML file.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 */
public class ConfigurationConstants {
	/**
	 * The configuration file name.
	 */
	public static final String DEFAULT_CONFIG_FILE = "DefaultConfiguration.xml";

	/**
	 * Represents tag for <tt>to-string</tt>.
	 */
	public static final String TOSTRING_TAG = "to-string";

	/**
	 * Represents tag for <tt>copy-constructor</tt>.
	 */
	public static final String COPYCON_TAG = "copy-constructor";

	/**
	 * Represents tag for <tt>java-doc</tt>.
	 */
	public static final String JAVADOC_TAG = "java-doc";

	/**
	 * Represents tag for <tt>implementation</tt>.
	 */
	public static final String IMPLEMENTATION_TAG = "implementation";

	/**
	 * Represents attribute for <tt>type</tt>.
	 */
	public static final String TYPE_ATTRIBUTE = "type";
}
