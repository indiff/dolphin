//package com.sun.tools.javadoc;
//
//public class Snippet {
//	/**
//	 * Usage
//	 */
//	private void usage() {
//	    messager.notice("main.usage");
//	
//	    // let doclet print usage information (does nothing on error)
//	    if (docletInvoker != null) {
//	        docletInvoker.optionLength("-help");
//	    }
//	}
//	
//	/**
//	 * Exit
//	 */
//	private void exit() {
//	    messager.exit();
//	}
//	
//	
//	/**
//	 * Main program - external wrapper
//	 */
//	int begin(String[] argv) {
//	    boolean failed = false;
//	
//	    try {
//	        failed = !parseAndExecute(argv);
//	    } catch(Messager.ExitJavadoc exc) {
//	        // ignore, we just exit this way
//	    } catch (OutOfMemoryError ee) {
//	        messager.error(null, "main.out.of.memory");
//	        failed = true;
//	    } catch (Error ee) {
//	        ee.printStackTrace();
//	        messager.error(null, "main.fatal.error");
//	        failed = true;
//	    } catch (Exception ee) {
//	        ee.printStackTrace();
//	        messager.error(null, "main.fatal.exception");
//	        failed = true;
//	    } finally {
//	        messager.exitNotice();
//	        messager.flush();
//	    }
//	    failed |= messager.nerrors() > 0;
//	    failed |= rejectWarnings && messager.nwarnings() > 0;
//	    return failed ? 1 : 0;
//	}
//	
//	private void addToList(ListBuffer list, String str){
//	    StringTokenizer st = new StringTokenizer(str, ":");
//	    String current;
//	    while(st.hasMoreTokens()){
//	        current = st.nextToken();
//	        list.append(current);
//	    }
//	}
//	
//	
//	
//	
//	
//	
//	/**
//	 * Main program - internal
//	 */
//	private boolean parseAndExecute(String[] argv) throws IOException {
//	    long tm = System.currentTimeMillis();
//	
//	    ListBuffer javaNames = new ListBuffer();
//	
//	    // Preprocess @file arguments
//	    try {
//	        argv = CommandLine.parse(argv);
//	    } catch (FileNotFoundException e) {
//	        messager.error(null, "main.cant.read", e.getMessage());
//	        exit();
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        exit();
//	    }
//	
//	    setDocletInvoker(argv);
//	    ListBuffer subPackages = new ListBuffer();
//	    ListBuffer excludedPackages = new ListBuffer();
//	    Options compOpts = Options.instance(context);
//	    boolean docClasses = false;
//	
//	    // Parse arguments
//	    for (int i = 0 ; i < argv.length ; i++) {
//	        String arg = argv[i];
//	        if (arg.equals("-subpackages")) {
//	            oneArg(argv, i++);
//	            addToList(subPackages, argv[i]);
//	        } else if (arg.equals("-exclude")){
//	            oneArg(argv, i++);
//	            addToList(excludedPackages, argv[i]);
//	        } else if (arg.equals("-verbose")) {
//	            setOption(arg);
//	            compOpts.put("-verbose", "");
//	        } else if (arg.equals("-encoding")) {
//	            oneArg(argv, i++);
//	            encoding = argv[i];
//	            compOpts.put("-encoding", argv[i]);
//	        } else if (arg.equals("-breakiterator")) {
//	            breakiterator = true;
//	            setOption("-breakiterator");
//	        } else if (arg.equals("-quiet")) {
//	            quiet = true;
//	            setOption("-quiet");
//	        } else if (arg.equals("-help")) {
//	            usage();
//	            exit();
//	        } else if (arg.equals("-Xclasses")) {
//	            setOption(arg);
//	            docClasses = true;
//	        } else if (arg.equals("-Xwerror")) {
//	            setOption(arg);
//	            rejectWarnings = true;
//	        } else if (arg.equals("-private")) {
//	            setOption(arg);
//	            setFilter(ModifierFilter.ALL_ACCESS);
//	        } else if (arg.equals("-package")) {
//	            setOption(arg);
//	            setFilter(Flags.PUBLIC | Flags.PROTECTED |
//	                      ModifierFilter.PACKAGE );
//	        } else if (arg.equals("-protected")) {
//	            setOption(arg);
//	            setFilter(Flags.PUBLIC | Flags.PROTECTED );
//	        } else if (arg.equals("-public")) {
//	            setOption(arg);
//	            setFilter(Flags.PUBLIC);
//	        } else if (arg.equals("-source")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-source") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-source", argv[i]);
//	        } else if (arg.equals("-prompt")) {
//	            compOpts.put("-prompt", "-prompt");
//	            messager.promptOnError = true;
//	        } else if (arg.equals("-sourcepath")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-sourcepath") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-sourcepath", argv[i]);
//	        } else if (arg.equals("-classpath")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-classpath") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-classpath", argv[i]);
//	        } else if (arg.equals("-sysclasspath")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-bootclasspath") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-bootclasspath", argv[i]);
//	        } else if (arg.equals("-bootclasspath")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-bootclasspath") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-bootclasspath", argv[i]);
//	        } else if (arg.equals("-extdirs")) {
//	            oneArg(argv, i++);
//	            if (compOpts.get("-extdirs") != null) {
//	                usageError("main.option.already.seen", arg);
//	            }
//	            compOpts.put("-extdirs", argv[i]);
//	        } else if (arg.equals("-overview")) {
//	            oneArg(argv, i++);
//	        } else if (arg.equals("-doclet")) {
//	            i++;  // handled in setDocletInvoker
//	        } else if (arg.equals("-docletpath")) {
//	            i++;  // handled in setDocletInvoker
//	        } else if (arg.equals("-locale")) {
//	            if (i != 0)
//	                usageError("main.locale_first");
//	            oneArg(argv, i++);
//	            docLocale = argv[i];
//	        } else if (arg.startsWith("-XD")) {
//	            String s = arg.substring("-XD".length());
//	            int eq = s.indexOf('=');
//	            String key = (eq < 0) ? s : s.substring(0, eq);
//	            String value = (eq < 0) ? s : s.substring(eq+1);
//	            compOpts.put(key, value);
//	        }
//	        // call doclet for its options
//	        // other arg starts with - is invalid
//	        else if ( arg.startsWith("-") ) {
//	            int optionLength;
//	            optionLength = docletInvoker.optionLength(arg);
//	            if (optionLength < 0) {
//	                // error already displayed
//	                exit();
//	            } else if (optionLength == 0) {
//	                // option not found
//	                usageError("main.invalid_flag", arg);
//	            } else {
//	                // doclet added option
//	                if ((i + optionLength) > argv.length) {
//	                    usageError("main.requires_argument", arg);
//	                }
//	                ListBuffer args = new ListBuffer();
//	                for (int j = 0; j < optionLength-1; ++j) {
//	                    args.append(argv[++i]);
//	                }
//	                setOption(arg, args.toList());
//	            }
//	        } else {
//	            javaNames.append(arg);
//	        }
//	    }
//	
//	    if (javaNames.isEmpty() && subPackages.isEmpty()) {
//	        usageError("main.No_packages_or_classes_specified");
//	    }
//	
//	    if (!docletInvoker.validOptions(options.toList())) {
//	        // error message already displayed
//	        exit();
//	    }
//	
//	    JavadocTool comp = JavadocTool.make0(context);
//	    if (comp == null) return false;
//	
//	    if (showAccess == null) {
//	        setFilter(defaultFilter);
//	    }
//	
//	    LanguageVersion languageVersion = docletInvoker.languageVersion();
//	    RootDocImpl root = comp.getRootDocImpl(
//	            docLocale, encoding, showAccess,
//	            javaNames.toList(), options.toList(), breakiterator,
//	            subPackages.toList(), excludedPackages.toList(),
//	            docClasses,
//	            // legacy?
//	            languageVersion == null || languageVersion == LanguageVersion.JAVA_1_1, quiet);
//	    
//	    
//	    /*********** TODO Show symbol's name and document ADD by Tan*************/
//	    DocEnv env = root.env;
//	    
//	    Map fieldMap = null; //env.getFieldMap();
//	    Set set = fieldMap.entrySet();
//	    for (Iterator iter = set.iterator(); iter.hasNext();) {
//	    	Map.Entry entry = (Map.Entry) iter.next();
//	    	VarSymbol symbol = (VarSymbol) entry.getKey();
//	    	FieldDocImpl fieldDoc = (FieldDocImpl) entry.getValue();
//	    }
//	    /*********** TODO Show symbol's name and document ADD Tan*************/
//	    
//	    
//	    
//	    // pass off control to the doclet
//	    boolean ok = root != null;
//	    if (ok) ok = docletInvoker.start(root);
//	
//	    // We're done.
//	    if (compOpts.get("-verbose") != null) {
//	        tm = System.currentTimeMillis() - tm;
//	        messager.notice("main.done_in", Long.toString(tm));
//	    }
//	
//	    return ok;
//	}
//	
//	private void setDocletInvoker(String[] argv) {
//	    String docletClassName = null;
//	    String docletPath = null;
//	
//	    // Parse doclet specifying arguments
//	    for (int i = 0 ; i < argv.length ; i++) {
//	        String arg = argv[i];
//	        if (arg.equals("-doclet")) {
//	            oneArg(argv, i++);
//	            if (docletClassName != null) {
//	                usageError("main.more_than_one_doclet_specified_0_and_1",
//	                           docletClassName, argv[i]);
//	            }
//	            docletClassName = argv[i];
//	        } else if (arg.equals("-docletpath")) {
//	            oneArg(argv, i++);
//	            if (docletPath == null) {
//	                docletPath = argv[i];
//	            } else {
//	                docletPath += File.pathSeparator + argv[i];
//	            }
//	        }
//	    }
//	
//	    if (docletClassName == null) {
//	        docletClassName = defaultDocletClassName;
//	    }
//	
//	    // attempt to find doclet
//	    docletInvoker = new DocletInvoker(messager,
//	                                      docletClassName, docletPath);
//	}
//	
//	private void setFilter(long filterBits) {
//	    if (showAccess != null) {
//	        messager.error(null, "main.incompatible.access.flags");
//	        usage();
//	        exit();
//	    }
//	    showAccess = new ModifierFilter(filterBits);
//	}
//	
//	/**
//	 * Set one arg option.
//	 * Error and exit if one argument is not provided.
//	 */
//	private void oneArg(String[] args, int index) {
//	    if ((index + 1) < args.length) {
//	        setOption(args[index], args[index+1]);
//	    } else {
//	        usageError("main.requires_argument", args[index]);
//	    }
//	}
//	
//	private void usageError(String key) {
//	    messager.error(null, key);
//	    usage();
//	    exit();
//	}
//	
//	private void usageError(String key, String a1) {
//	    messager.error(null, key, a1);
//	    usage();
//	    exit();
//	}
//	
//	private void usageError(String key, String a1, String a2) {
//	    messager.error(null, key, a1, a2);
//	    usage();
//	    exit();
//	}
//	
//	/**
//	 * indicate an option with no arguments was given.
//	 */
//	private void setOption(String opt) {
//	    String[] option = { opt };
//	    options.append(option);
//	}
//	
//	/**
//	 * indicate an option with one argument was given.
//	 */
//	private void setOption(String opt, String argument) {
//	    String[] option = { opt, argument };
//	    options.append(option);
//	}
//	
//	/**
//	 * indicate an option with the specified list of arguments was given.
//	 */
//	private void setOption(String opt, List arguments) {
//	    String[] args = new String[arguments.length() + 1];
//	    int k = 0;
//	    args[k++] = opt;
//	    for (List i = arguments; i.nonEmpty(); i=i.tail) {
//	        args[k++] = (String) i.head;
//	    }
//	    options = options.append(args);
//	}
//}

