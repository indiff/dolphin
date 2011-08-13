package com.sysdeo.eclipse.tomcat;

import java.io.File;

public class Tomcat7Bootstrap extends Tomcat6Bootstrap {
	
	/**
	 * Modify by tyj.
	 */
	public String[] getClasspath() {
		String toolsJarLocation = new StringBuffer( VMLauncherUtility.getVMInstall().getInstallLocation().toString() )
		.append( File.separator ).append( "lib" )
		.append( File.separator ).append( "tools.jar" ).toString()
		;
		if(new File( toolsJarLocation ).exists()) {
			return new String[] {
					getTomcatDir() + File.separator + "bin" + File.separator + "bootstrap.jar",
					getTomcatDir() + File.separator + "bin" + File.separator + "tomcat-juli.jar",
					toolsJarLocation
			};
		} else {
			return new String[] {
					getTomcatDir() + File.separator + "bin" + File.separator + "bootstrap.jar",
					getTomcatDir() + File.separator + "bin" + File.separator + "tomcat-juli.jar"
			};
		}
	}
	
/*	public String[] getClasspath() {
		ArrayList classpath = new ArrayList();
		classpath.add(getTomcatDir() + File.separator + "bin" + File.separator + "bootstrap.jar");
		classpath.add(getTomcatDir() + File.separator + "bin" + File.separator + "tomcat-juli.jar");
		// Add tools.jar JDK file to classpath
		String toolsJarLocation = VMLauncherUtility.getVMInstall().getInstallLocation() + File.separator + "lib" + File.separator + "tools.jar";
		if(new File(toolsJarLocation).exists()) {
			classpath.add(toolsJarLocation);
		}
		return ((String[])classpath.toArray(new String[0]));
	}*/
	
	public String getLabel() {
		return "Tomcat 7.x";
	}	
}
