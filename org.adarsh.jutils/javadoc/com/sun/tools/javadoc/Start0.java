/*
 * Copyright 1997-2006 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package com.sun.tools.javadoc;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Options;
import com.tan.util.GenerateUtil;

/**
 * Main program of Javadoc. Previously named "Main".
 * 
 * @since 1.2
 * @author Robert Field
 * @author Neal Gafter (rewrite)
 */
class Start0 {
	/** Context for this invocation. */
	private final Context context;

	private final String defaultDocletClassName;
	private final ClassLoader docletParentClassLoader;

	private static final String javadocName = "javadoc";
	private static final String LINE = System.getProperty("line.separator",
			"\r\n");

	private static final String standardDocletClassName = "com.sun.tools.doclets.standard.Standard";

	private ListBuffer options = new ListBuffer();

	private ModifierFilter showAccess = null;

	private long defaultFilter = Flags.PUBLIC | Flags.PROTECTED;

	private Messager messager;

	String docLocale = "";

	boolean breakiterator = false;
	boolean quiet = false;
	String encoding = null;

	private DocletInvoker docletInvoker;

	// TODO static field delete.
	// private static final int F_VERBOSE = 1 << 0;
	// private static final int F_WARNINGS = 1 << 2;

	/* Treat warnings as errors. */
	private boolean rejectWarnings = false;

	Start0(String programName, PrintWriter errWriter, PrintWriter warnWriter,
			PrintWriter noticeWriter, String defaultDocletClassName) {
		this(programName, errWriter, warnWriter, noticeWriter,
				defaultDocletClassName, null);
	}

	Start0(String programName, PrintWriter errWriter, PrintWriter warnWriter,
			PrintWriter noticeWriter, String defaultDocletClassName,
			ClassLoader docletParentClassLoader) {
		context = new Context();
		messager = new Messager(context, programName, errWriter, warnWriter,
				noticeWriter);
		this.defaultDocletClassName = defaultDocletClassName;
		this.docletParentClassLoader = docletParentClassLoader;
	}

	Start0(String programName, String defaultDocletClassName) {
		this(programName, defaultDocletClassName, null);
	}

	Start0(String programName, String defaultDocletClassName,
			ClassLoader docletParentClassLoader) {
		context = new Context();
		messager = new Messager(context, programName);
		this.defaultDocletClassName = defaultDocletClassName;
		this.docletParentClassLoader = docletParentClassLoader;
	}

	Start0(String programName, ClassLoader docletParentClassLoader) {
		this(programName, standardDocletClassName, docletParentClassLoader);
	}

	Start0(String programName) {
		this(programName, standardDocletClassName);
	}

	Start0(ClassLoader docletParentClassLoader) {
		this(javadocName, standardDocletClassName, docletParentClassLoader);
	}

	Start0() {
		this(javadocName);
	}

	/**
	 * New Method for print setter and getter.
	 * 
	 * @param argv
	 * @return
	 * @throws IOException
	 */
	public String printSetterGetter(String[] argv) throws IOException {
		ListBuffer javaNames = new ListBuffer();
		// Preprocess @file arguments
		// setDocletInvoker(argv);
		ListBuffer subPackages = new ListBuffer();
		ListBuffer excludedPackages = new ListBuffer();
		Options compOpts = Options.instance(context);
		boolean docClasses = false;
		// Parse arguments
		for (int i = 0; i < argv.length; i++) {
			String arg = argv[i];
			if (arg.equals("-encoding")) {
				oneArg(argv, i++);
				encoding = argv[i];
				compOpts.put("-encoding", argv[i]);
			} else {
				javaNames.append(arg);
			}
		}

		JavadocTool comp = JavadocTool.make0(context);
		if (comp == null)
			return null;

		if (showAccess == null) {
			setFilter(defaultFilter);
		}

		// LanguageVersion languageVersion = docletInvoker.languageVersion();
		quiet = true;
		RootDocImpl root = comp.getRootDocImpl(docLocale, encoding, showAccess,
				javaNames.toList(), options.toList(), breakiterator,
				subPackages.toList(), excludedPackages.toList(), docClasses,
				// legacy?
				true,// languageVersion == null || languageVersion ==
						// LanguageVersion.JAVA_1_1,
				quiet);

		/*********** TODO Operate getter & setter. *************/
		ClassDoc[] classDocs = root.classes();
		if (null != classDocs && classDocs.length > 0) {
			String name;
			StringBuilder info = new StringBuilder();
			ClassDoc doc = classDocs[0];
			String javaName = doc.name();
			FieldDoc[] fields = doc.fields(false);
			MethodDoc[] methods = doc.methods(false);
			Tag[] tags = doc.tags();
			/*
			 * append(info, "类的个数:",classDocs.length, LINE); append(info,
			 * "类名",javaName, LINE); append(info, "字段个数:", fields.length, LINE);
			 * append(info, "方法个数", methods.length, LINE);
			 * 
			 * StringBuilder setter = new StringBuilder(); StringBuilder getter
			 * = new StringBuilder(); StringBuilder td = new StringBuilder();
			 * StringBuilder th = new StringBuilder();
			 * 
			 * // dump the fields. for (int i = 0; i < fields.length; i++) {
			 * name = fields[i].name(); append(info, "字段名:",name, ", 注释:" ,
			 * fields[i].commentText(), LINE); }
			 * 
			 * // dump the methods. for (int i =0; i < methods.length; i++) {
			 * name = methods[i].name(); append(info, "方法名:",name,", 注释:",
			 * methods[i].commentText(), LINE); }
			 * 
			 * // dump the tags. for (int i =0; i < tags.length; i++) { name =
			 * tags[i].name(); append(info, "标记:",name, ", 标记注释:",
			 * tags[i].text(), ", 标记种类:", tags[i].kind(), LINE); }
			 */

			GenerateUtil util = new GenerateUtil();

			util.setJavaName(javaName);
			info = null;
			return util.generateSetter(fields);
		}
		return null;
	}

	/**
	 * Set one arg option. Error and exit if one argument is not provided.
	 */
	private void oneArg(String[] args, int index) {
		if ((index + 1) < args.length) {
			setOption(args[index], args[index + 1]);
		} else {
			usageError("main.requires_argument", args[index]);
		}
	}

	private void usageError(String key) {
		messager.error(null, key);
		usage();
		exit();
	}

	private void usageError(String key, String a1) {
		messager.error(null, key, a1);
		usage();
		exit();
	}

	private void usageError(String key, String a1, String a2) {
		messager.error(null, key, a1, a2);
		usage();
		exit();
	}

	/**
	 * indicate an option with no arguments was given.
	 */
	private void setOption(String opt) {
		String[] option = { opt };
		options.append(option);
	}

	/**
	 * indicate an option with one argument was given.
	 */
	private void setOption(String opt, String argument) {
		String[] option = { opt, argument };
		options.append(option);
	}

	/**
	 * indicate an option with the specified list of arguments was given.
	 */
	private void setOption(String opt, List arguments) {
		String[] args = new String[arguments.length() + 1];
		int k = 0;
		args[k++] = opt;
		for (List i = arguments; i.nonEmpty(); i = i.tail) {
			args[k++] = (String) i.head;
		}
		options = options.append(args);
	}

	private void setFilter(long filterBits) {
		if (showAccess != null) {
			messager.error(null, "main.incompatible.access.flags");
			usage();
			exit();
		}
		showAccess = new ModifierFilter(filterBits);
	}

	/**
	 * Usage
	 */
	private void usage() {
		messager.notice("main.usage");

		// let doclet print usage information (does nothing on error)
		if (docletInvoker != null) {
			docletInvoker.optionLength("-help");
		}
	}

	/**
	 * Exit
	 */
	private void exit() {
		messager.exit();
	}

}
