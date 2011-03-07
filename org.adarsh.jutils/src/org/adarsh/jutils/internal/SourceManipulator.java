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

import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.adarsh.jutils.JUtilsException;
import org.adarsh.jutils.JUtilsPlugin;
import org.adarsh.jutils.preferences.PreferenceConstants;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.internal.corext.codemanipulation.StubUtility;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * This is the work horse of the plugin.
 * <p>
 * 
 * It is responsible for fetching the template from either the configuration
 * file or from the preference store and then converting it into a useful
 * representation by using the fields contained in the compilation unit.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 2005
 * 
 * @version 2.0, 14th April 2006
 * 
 * @version 3.0, 10th December 2006
 */
public class SourceManipulator {
	/**
	 * The preference store associated with the plugin.
	 */
	private static final IPreferenceStore PREF_STORE = JUtilsPlugin
			.getDefault().getPreferenceStore();

	/**
	 * First of ${attribute} patterns.
	 */
	private static final Pattern ATTR_PAT1 = Pattern
			.compile(".*\\$\\{attribute\\}.*");

	/**
	 * Second of ${attribute} patterns.
	 */
	private static final Pattern ATTR_PAT2 = Pattern
			.compile("\\$\\{attribute\\}");

	/**
	 * ${class_name} pattern.
	 */
	private static final Pattern CLSS_PAT = Pattern
			.compile("\\$\\{class_name\\}");

	/**
	 * ${class_instance} pattern.
	 */
	private static final Pattern CLSS_INST_PAT = Pattern
			.compile("\\$\\{class_instance\\}");

	/**
	 * Indentation pattern.
	 */
	private static final Pattern INDENT_PAT = Pattern.compile("(?m)^");

	/**
	 * Writes the toString implementation alongwith it's JavaDoc into the
	 * compilation unit.
	 * 
	 * @param compUnit
	 *            the compilation unit (Java source file).
	 * 
	 * @throws JUtilsException
	 *             in case of any errors during the operation.
	 */
	public static void createToStringWithJavaDoc(IType type)
			throws JUtilsException {
		try {
			String elemName = type.getElementName();

			IProgressMonitor monitor = new NullProgressMonitor();

			String javaDoc = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.TOSTRING_JAVADOC_STORE_KEY);

			if (Util.isNullString(javaDoc)) {
				javaDoc = Util.getDefaultToStringJavaDoc();
			}

			javaDoc = CLSS_PAT.matcher(javaDoc).replaceAll(elemName);

			String impl = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);

			if (Util.isNullString(impl)) {
				impl = Util.getDefaultToStringImplementation();
			}

			impl = CLSS_PAT.matcher(impl).replaceAll(elemName);

			Matcher matcher = ATTR_PAT1.matcher(impl);

			String attributesLine;

			StringBuffer attributesChunk = new StringBuffer("");

			String fieldName;

			IField[] fields = type.getFields();

			boolean isSort = SourceManipulator.PREF_STORE
					.getBoolean("tostring.sort");

			if (isSort) {
				Arrays.sort(fields, new Comparator() {
					public int compare(Object arg1, Object arg2) {
						IField field1 = (IField) arg1;
						IField field2 = (IField) arg2;

						return field1.getElementName().compareTo(
								field2.getElementName());
					}
				});
			}

			// translate all variables into their values.
			if (matcher.find()) {
				attributesLine = matcher.group();

				for (int i = 0; i < fields.length; i++) {
					// don't display static fields.
					if (Flags.isStatic(fields[i].getFlags())) {
						continue;
					}

					fieldName = fields[i].getElementName();

					attributesChunk.append(
							ATTR_PAT2.matcher(attributesLine).replaceAll(
									fieldName)).append('\n');      
					 // + "name = " + this.name + TAB
				}

				attributesChunk = fields.length == 0 ? new StringBuffer("")
						: new StringBuffer(attributesChunk.substring(0,
								attributesChunk.lastIndexOf("\n")));
			}

			impl = matcher.replaceAll(attributesChunk.toString());

			/*
			 * Bug Fix: The very fist time after installation, if toString was
			 * attempted to be generated on a class which already had toString,
			 * auto overwrite feature was failing. This was because unless the
			 * preference page is opened for the first time, the value of the
			 * overwrite flag as fetched from the preference store will be false
			 * as the preference store wouldn't have been initialized.
			 * 
			 * Now we assume a true value for overwrite flag if isDefault
			 * fetches true.
			 */

			boolean canOverwrite = SourceManipulator.PREF_STORE
					.isDefault("tostring.overwrite")
					|| SourceManipulator.PREF_STORE
							.getBoolean("tostring.overwrite");

			IMethod[] existingToString = type.findMethods(type.getMethod(
					"toString", new String[] {}));

			String method = new StringBuffer(javaDoc).append(impl).toString();

			String indent = getIndentation(type);

			if (existingToString == null) {
				method = INDENT_PAT.matcher(method).replaceAll(indent);

				type.createMethod(method, null, true, monitor);
			} else {
				if (canOverwrite) {
					existingToString[0].delete(false, monitor);

					method = INDENT_PAT.matcher(method).replaceAll(indent);

					type.createMethod(method, null, true, monitor);
				}
			}
		} catch (JavaModelException e) {
			throw new JUtilsException("Type " + type.getElementName()
					+ " generated exception", e);
		}
	}

	/**
	 * Writes the copy constructor implementation alongwith it's JavaDoc into
	 * the compilation unit.
	 * 
	 * @param compUnit
	 *            the compilation unit (Java source file).
	 * 
	 * @throws JUtilsException
	 *             in case of any errors during the operation.
	 */
	public static void createCopyConstructorWithJavaDoc(IType type)
			throws JUtilsException {
		try {
			String elemName = type.getElementName();

			IProgressMonitor monitor = new NullProgressMonitor();

			String javaDoc = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.COPYCON_JAVADOC_STORE_KEY);

			if (Util.isNullString(javaDoc)) {
				javaDoc = Util.getDefaultCopyConJavaDoc();
			}

			javaDoc = CLSS_PAT.matcher(javaDoc).replaceAll(elemName);

			String instanceName = Character.toLowerCase(elemName.charAt(0))
					+ elemName.substring(1);

			javaDoc = CLSS_INST_PAT.matcher(javaDoc).replaceAll(instanceName);

			String impl = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.COPYCON_BODY_STORE_KEY);

			if (Util.isNullString(impl)) {
				impl = Util.getDefaultCopyConImplementation();
			}

			impl = CLSS_PAT.matcher(impl).replaceAll(elemName);

			Matcher matcher = ATTR_PAT1.matcher(impl);

			String attributesLine;

			StringBuffer attributesChunk = new StringBuffer("");

			String fieldName;

			IField[] fields = type.getFields();

			boolean isSort = SourceManipulator.PREF_STORE
					.getBoolean("copycon.sort");

			if (isSort) {
				Arrays.sort(fields, new Comparator() {
					public int compare(Object arg1, Object arg2) {
						IField field1 = (IField) arg1;
						IField field2 = (IField) arg2;

						return field1.getElementName().compareTo(
								field2.getElementName());
					}
				});
			}

			// translate all variables into their values.
			if (matcher.find()) {
				attributesLine = matcher.group();

				for (int i = 0; i < fields.length; i++) {
					// don't touch static or final fields.
					if (Flags.isStatic(fields[i].getFlags())
							|| Flags.isFinal(fields[i].getFlags())) {
						continue;
					}

					fieldName = fields[i].getElementName();

					attributesChunk.append(
							CLSS_INST_PAT.matcher(
									ATTR_PAT2.matcher(attributesLine)
											.replaceAll(fieldName)).replaceAll(
									instanceName)).append('\n');
				}

				attributesChunk = fields.length == 0 ? new StringBuffer("")
						: new StringBuffer(attributesChunk.substring(0,
								attributesChunk.lastIndexOf("\n")));
			}

			impl = matcher.replaceAll(attributesChunk.toString());

			impl = CLSS_INST_PAT.matcher(impl).replaceAll(instanceName);

			/*
			 * Bug Fix: The very fist time after installation, if copy
			 * constructor was attempted to be generated on a class which
			 * already had one, auto overwrite feature was failing. This was
			 * because unless the preference page is opened for the first time,
			 * the value of the overwrite flag as fetched from the preference
			 * store will be false as the preference store wouldn't have been
			 * initialized.
			 * 
			 * Now we assume a true value for overwrite flag if isDefault
			 * fetches true.
			 */

			boolean canOverwrite = SourceManipulator.PREF_STORE
					.isDefault("copycon.overwrite")
					|| SourceManipulator.PREF_STORE
							.getBoolean("copycon.overwrite");

			IMethod[] existingCopycon = type.findMethods(type.getMethod(
					elemName, new String[] { "Q" + elemName + ";" }));

			String copycon = new StringBuffer(javaDoc).append(impl).toString();

			String indent = getIndentation(type);

			IJavaElement position = determineCopyConPosition(type);

			if (existingCopycon == null) {
				copycon = INDENT_PAT.matcher(copycon).replaceAll(indent);

				type.createMethod(copycon, position, true, monitor);
			} else {
				if (canOverwrite) {
					existingCopycon[0].delete(false, monitor);

					copycon = INDENT_PAT.matcher(copycon).replaceAll(indent);

					type.createMethod(copycon, position, true, monitor);
				}
			}
		} catch (JavaModelException e) {
			throw new JUtilsException("Type " + type.getElementName()
					+ " generated exception", e);
		}
	}

	/**
	 * Determines the position for the new copy constructor.
	 * 
	 * @param type
	 *            the type in which the copy constructor will be inserted.
	 * 
	 * @return an <tt>IJavaElement</tt> which is the first method (if any) in
	 *         the type or <tt>null</tt> otherwise.
	 * 
	 * @throws JavaModelException
	 *             in case of errors during the operation.
	 */
	private static IJavaElement determineCopyConPosition(IType type)
			throws JavaModelException {
		IMethod[] methods = type.getMethods();

		IJavaElement position = null;

		if (methods != null && methods.length >= 1) {
			position = methods[0];
		}

		return position;
	}

	/**
	 * Returns the indentation used for a given type.
	 * 
	 * @param type
	 *            whose indentation is desired.
	 * 
	 * @return a string holding the indent value.
	 * 
	 * @throws JavaModelException
	 *             in case of errors during the operation.
	 */
	private static String getIndentation(IType type) throws JavaModelException {
		int indent = StubUtility.getIndentUsed(type);

		StringBuffer tab = new StringBuffer("");

		for (int x = 0; x <= indent; x++) {
			tab.append("    ");
		}

		return tab.toString();
	}

	/**
	 * Writes the toString implementation alongwith it's JavaDoc into the
	 * compilation unit.
	 * 
	 * @param compUnit
	 *            the compilation unit (Java source file).
	 * 
	 * @throws JUtilsException
	 *             in case of any errors during the operation.
	 */
	public static void createDummySetterWithJavaDoc(IType type, final String generated)
			throws JUtilsException {
		try {
			String elemName = type.getElementName();
	
			IProgressMonitor monitor = new NullProgressMonitor();
	
			String javaDoc = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.TOSTRING_JAVADOC_STORE_KEY);
	
			if (Util.isNullString(javaDoc)) {
				javaDoc = Util.getDefaultToStringJavaDoc();
			}
	
			javaDoc = CLSS_PAT.matcher(javaDoc).replaceAll(elemName);
	
			String impl = SourceManipulator.PREF_STORE
					.getString(PreferenceConstants.TOSTRING_BODY_STORE_KEY);
	
			if (Util.isNullString(impl)) {
				impl = Util.getDefaultToStringImplementation();
			}
	
			impl = CLSS_PAT.matcher(impl).replaceAll(elemName);
	
			Matcher matcher = ATTR_PAT1.matcher(impl);
	
			String attributesLine;
	
			StringBuffer attributesChunk = new StringBuffer("");
	
			String fieldName;
	
			IField[] fields = type.getFields();
	
			boolean isSort = SourceManipulator.PREF_STORE
					.getBoolean("tostring.sort");
	
			if (isSort) {
				Arrays.sort(fields, new Comparator() {
					public int compare(Object arg1, Object arg2) {
						IField field1 = (IField) arg1;
						IField field2 = (IField) arg2;
	
						return field1.getElementName().compareTo(
								field2.getElementName());
					}
				});
			}
	
			// translate all variables into their values.
			if (matcher.find()) {
				attributesLine = matcher.group();
	
				for (int i = 0; i < fields.length; i++) {
					// don't display static fields.
					if (Flags.isStatic(fields[i].getFlags())) {
						continue;
					}
					/// TODO fieldName.
					fieldName = fields[i].getElementName();
					
					attributesChunk.append(
							ATTR_PAT2.matcher(attributesLine).replaceAll(
									fieldName)).append('\n');      
					
					// + "name = " + this.name + TAB
				}
	
				attributesChunk = fields.length == 0 ? new StringBuffer("")
						: new StringBuffer(attributesChunk.substring(0,
								attributesChunk.lastIndexOf("\n")));
			}
	
			impl = matcher.replaceAll(attributesChunk.toString());
	
			/*
			 * Bug Fix: The very fist time after installation, if toString was
			 * attempted to be generated on a class which already had toString,
			 * auto overwrite feature was failing. This was because unless the
			 * preference page is opened for the first time, the value of the
			 * overwrite flag as fetched from the preference store will be false
			 * as the preference store wouldn't have been initialized.
			 * 
			 * Now we assume a true value for overwrite flag if isDefault
			 * fetches true.
			 */
	
			boolean canOverwrite = SourceManipulator.PREF_STORE
					.isDefault("tostring.overwrite")
					|| SourceManipulator.PREF_STORE
							.getBoolean("tostring.overwrite");
	
			IMethod[] existingToString = type.findMethods(type.getMethod(
					"toString", new String[] {}));
	
			String method = new StringBuffer(javaDoc).append(impl).toString();
	
			String indent = getIndentation(type);
	
			if (existingToString == null) {
				method = INDENT_PAT.matcher(method).replaceAll(indent);
				
				type.createMethod(
						"public void dummy() {\n" +
						generated + 
						"}\n"
						, null, true, monitor);
//				type.createMethod(method, null, true, monitor);
			} else {
				if (canOverwrite) {
					existingToString[0].delete(false, monitor);
	
					method = INDENT_PAT.matcher(method).replaceAll(indent);
	
					type.createMethod(
							"public void dummy() {\n" +
							generated + 
							"}\n"
							, null, true, monitor);
//					type.createMethod(method, null, true, monitor);
				}
			}
		} catch (JavaModelException e) {
			throw new JUtilsException("Type " + type.getElementName()
					+ " generated exception", e);
		}
	}
}
