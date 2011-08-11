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

package org.adarsh.jutils.preferences;

import org.adarsh.jutils.Messages;

/**
 * Contains all constants related to the preferences.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 * 
 * @version 3.0, 10th December 2006
 */
public class PreferenceConstants {
	/**
	 * Indicates the bundle name for preferences
	 */
	public static final String PREFERENCE_MESSAGES_BUNDLE = "org.adarsh.jutils.preferences.preference_messages";

	/**
	 * The java partitioning string.
	 */
	public static final String PARTITIONING = "___java_partitioning";

	/**
	 * The source viewer width.
	 */
	public static final int WIDTH = 3;

	/**
	 * The source viewer height.
	 */
	public static final int HEIGHT = 5;

	/**
	 * The source viewer style.
	 */
	public static final int SOURCEVIEWER_STYLE = 2816;

	/**
	 * The source viewer grid style.
	 */
	public static final int GRID_STYLE = 1296;

	/**
	 * The source viewer java font.
	 */
	public static final String JAVA_FONT = "org.eclipse.jdt.ui.editors.textfont";

	/**
	 * <tt>JUtils</tt> preference page description.
	 */
	public static final String JUTILS_PAGE_DESCRIPTION = Messages
			.getString("preferences.jutils.page.description");

	/**
	 * Represents attribute.
	 */
	public static final String ATTRIBUTE = Messages
			.getString("preferences.attribute");

	/**
	 * Represents attribute description.
	 */
	public static final String ATTRIBUTE_DESCRIPTION = Messages
			.getString("preferences.attribute.description");

	/**
	 * Represents class instance.
	 */
	public static final String CLASS_INSTANCE = Messages
			.getString("preferences.class.instance");

	/**
	 * Represents class instance description.
	 */
	public static final String CLASS_INSTANCE_DESCRIPTION = Messages
			.getString("preferences.class.instance.description");

	/**
	 * Represents class name.
	 */
	public static final String CLASS_NAME = Messages
			.getString("preferences.class.name");

	/**
	 * Represents class name description.
	 */
	public static final String CLASS_NAME_DESCRIPTION = Messages
			.getString("preferences.class.name.description");

	/**
	 * <tt>JUtils -> Copy Constructor</tt> preference page description.
	 */
	public static final String COPYCON_PAGE_DESCRIPTION = Messages
			.getString("preferences.copycon.page.description");

	/**
	 * <tt>JUtils -> toString()</tt> preference page description.
	 */
	public static final String TOSTRING_PAGE_DESCRIPTION = Messages
			.getString("preferences.tostring.page.description");

	/**
	 * Label for JavaDoc.
	 */
	public static final String JAVADOC_LABEL = Messages
			.getString("preferences.javadoc");

	/**
	 * Label for method body.
	 */
	public static final String METHOD_BODY_LABEL = Messages
			.getString("preferences.method.body");

	/**
	 * Label for constructor body.
	 */
	public static final String CONSTRUCTOR_BODY_LABEL = Messages
			.getString("preferences.constructor.body");

	/**
	 * Store key for toString JavaDoc.
	 */
	public static final String TOSTRING_JAVADOC_STORE_KEY = "tostring.javadoc";

	/**
	 * Store key for toString body.
	 */
	public static final String TOSTRING_BODY_STORE_KEY = "tostring.body";

	/**
	 * Store key for copy constructor JavaDoc.
	 */
	public static final String COPYCON_JAVADOC_STORE_KEY = "copycon.javadoc";

	/**
	 * Store key for copy constructor body.
	 */
	public static final String COPYCON_BODY_STORE_KEY = "copycon.body";

	/**
	 * Represents the mode selection radio control.
	 */
	public static final String MODE = "mode";

	// /// Check Boxes End /////

	/**
	 * Represents the sort check box for toString.
	 */
	public static final String TOSTRING_SORT = "tostring.sort";

	/**
	 * Represents the overwrite check box for toString.
	 */
	public static final String TOSTRING_OVERWRITE = "tostring.overwrite";

	/**
	 * Represents the autosave check box for toString.
	 */
	public static final String TOSTRING_AUTOSAVE = "tostring.autosave";

	/**
	 * Represents the sort check box for Copy Constructor.
	 */
	public static final String COPYCON_SORT = "copycon.sort";

	/**
	 * Represents the overwrite check box for Copy Constructor.
	 */
	public static final String COPYCON_OVERWRITE = "copycon.overwrite";

	/**
	 * Represents the autosave check box for Copy Constructor.
	 */
	public static final String COPYCON_AUTOSAVE = "copycon.autosave";

	/**
	 * Represents the sort check box label for toString.
	 */
	public static final String TOSTRING_SORT_LABEL = Messages
			.getString("preferences.tostring.sort");

	/**
	 * Represents the overwrite check box label for toString.
	 */
	public static final String TOSTRING_OVERWRITE_LABEL = Messages
			.getString("preferences.tostring.overwrite");

	/**
	 * Represents the autosave check box label for toString.
	 */
	public static final String TOSTRING_AUTOSAVE_LABEL = Messages
			.getString("preferences.tostring.autosave");

	/**
	 * Represents the sort check box label for Copy Constructor.
	 */
	public static final String COPYCON_SORT_LABEL = Messages
			.getString("preferences.copycon.sort");

	/**
	 * Represents the overwrite check box label for Copy Constructor.
	 */
	public static final String COPYCON_OVERWRITE_LABEL = Messages
			.getString("preferences.copycon.overwrite");

	/**
	 * Represents the autosave check box label for Copy Constructor.
	 */
	public static final String COPYCON_AUTOSAVE_LABEL = Messages
			.getString("preferences.copycon.autosave");

	// /// Check Boxes End /////

	/**
	 * Mode constant for string.
	 */
	public static final String STRING = "string";

	/**
	 * Mode constant for string buffer.
	 */
	public static final String STRING_BUFFER = "stringbuffer";

	/**
	 * Mode constant for string builder.
	 */
	public static final String STRING_BUILDER = "stringbuilder";

	/**
	 * Label for mode.
	 */
	public static final String MODE_LABEL = Messages
			.getString("preferences.tostring.mode");

	/**
	 * Label for string.
	 */
	public static final String STRING_LABEL = Messages
			.getString("preferences.tostring.string");

	/**
	 * Label for string buffer.
	 */
	public static final String STRING_BUFFER_LABEL = Messages
			.getString("preferences.tostring.stringbuffer");

	/**
	 * Label for string builder.
	 */
	public static final String STRING_BUILDER_LABEL = Messages
			.getString("preferences.tostring.stringbuilder");

	public static final String EDITOR_PATH = "editor.path";

	public static final String GETTER_SETTER_DESCRIPTION = Messages
			.getString("getter.setter.desc");

	public static final String STYLE1 = Messages.getString("getter.setter.style1");

	public static final String STYLE2 = Messages.getString("getter.setter.style2");

	public static final String STYLE3 = Messages.getString("getter.setter.style3");

	public static final String STR_STYLE1 = "1";
	public static final String STR_STYLE2 = "2";
	public static final String STR_STYLE3 = "3";

	public static final String GETTER_SETTER_STYLE = "getter.setter.style";

	public static final String GETTER_SETTER_STYLE_LABEL = Messages.getString("getter.setter.style.label");

	public static final String MODIFY_AUTHOR = Messages
			.getString("preferences.modify.author");;

	public static final String MODIFY_AUTHOR_DESCRIPTION = Messages
			.getString("preferences.modify.author.description");;
}
