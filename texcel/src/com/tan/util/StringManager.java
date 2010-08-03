package com.tan.util;

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.Getter;

public class StringManager {
    private ResourceBundle bundle;
    private @Getter Locale locale;

    private StringManager(String packageName) {
       this(packageName, Locale.getDefault());
    }
    
    private StringManager(String packageName, Locale locale) {
    	
    	String bundleName = packageName + ".LocalStrings";
    	
        try {
            bundle = ResourceBundle.getBundle(bundleName, locale);
        } catch( MissingResourceException ex ) {
            // Try from the current loader (that's the case for trusted apps)
            // Should only be required if using a TC5 style classloader structure
            // where common != shared != server
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if( cl != null ) {
                try {
                    bundle = ResourceBundle.getBundle(
                            bundleName, locale, cl);
                } catch(MissingResourceException ex2) {
                    // Ignore
                }
            }
        }
        // Get the actual locale, which may be different from the requested one
        if (bundle != null) {
            locale = bundle.getLocale();
        }
    }

	public String getString(String key) {
        if(key == null){
            throw new IllegalArgumentException("key can not be a null value");
        }

        String str = null;

        try {
            // Avoid NPE if bundle is null and treat it like an MRE
            if (bundle != null) {
                str = bundle.getString(key);
            }
        } catch(MissingResourceException mre) {
            //bad: shouldn't mask an exception the following way:
            //   str = "[cannot find message associated with key '" + key + "' due to " + mre + "]";
            //     because it hides the fact that the String was missing
            //     from the calling code.
            //good: could just throw the exception (or wrap it in another)
            //      but that would probably cause much havoc on existing
            //      code.
            //better: consistent with container pattern to
            //      simply return null.  Calling code can then do
            //      a null check.
            str = null;
        }

        return str;
    }

    public String getString(final String key, final Object... args) {
        String value = getString(key);
        if (value == null) {
            value = key;
        }

        MessageFormat mf = new MessageFormat(value);
        mf.setLocale(locale);
        return mf.format(args, new StringBuffer(), null).toString();
    }

    // --------------------------------------------------------------
    // STATIC SUPPORT METHODS
    // --------------------------------------------------------------
    private static Hashtable<String, StringManager> managers =
        new Hashtable<String, StringManager>();

    /**
     * Get the StringManager for a particular package. If a manager for
     * a package already exists, it will be reused, else a new
     * StringManager will be created and returned.
     *
     * @param packageName The package name
     */
    public static final StringManager getManager(String packageName) {
       return getManager(packageName, null);
    }
    
    public synchronized static final StringManager getManager(String packageName, Locale locale) {
    	// checked locale.
    	if (locale == null){
    		locale = Locale.getDefault();
    	}
    	
        StringManager mgr = null;
        StringManager old = managers.get(packageName);
        
        if (old != null && 
        	old.getLocale() != null && 
        	old.getLocale().equals(locale)) {
        	mgr = old;
        }
        
        if (mgr == null) {
            mgr = new StringManager(packageName, locale);
            managers.put(packageName, mgr);
        }
        return mgr;
    }
}
