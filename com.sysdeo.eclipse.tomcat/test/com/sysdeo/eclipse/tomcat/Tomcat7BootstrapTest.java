/**
 * 
 */
package com.sysdeo.eclipse.tomcat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author tanyuanji
 *
 */
public class Tomcat7BootstrapTest {
	
	private Tomcat7Bootstrap tomcat;
	
	
	@Before
	public void setup() {
		tomcat = new Tomcat7Bootstrap();
	}
	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat7Bootstrap#getClasspath()} 的测试方法。
	 */
	@Test
	public void testGetClasspath() {
		System.out.println( Arrays.toString( tomcat.getClasspath() ) );
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat7Bootstrap#getLabel()} 的测试方法。
	 */
	@Test
	public void testGetLabel() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getVmArgs()} 的测试方法。
	 */
	@Test
	public void testGetVmArgs() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getPrgArgs(java.lang.String)} 的测试方法。
	 */
	@Test
	public void testGetPrgArgs() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getStartCommand()} 的测试方法。
	 */
	@Test
	public void testGetStartCommand() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getStopCommand()} 的测试方法。
	 */
	@Test
	public void testGetStopCommand() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getMainClass()} 的测试方法。
	 */
	@Test
	public void testGetMainClass() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getContextWorkDir(java.lang.String)} 的测试方法。
	 */
	@Test
	public void testGetContextWorkDir() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getServletJarPath()} 的测试方法。
	 */
	@Test
	public void testGetServletJarPath() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getJasperJarPath()} 的测试方法。
	 */
	@Test
	public void testGetJasperJarPath() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getJSPJarPath()} 的测试方法。
	 */
	@Test
	public void testGetJSPJarPath() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getTomcatJars()} 的测试方法。
	 */
	@Test
	public void testGetTomcatJars() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getXMLTagAfterContextDefinition()} 的测试方法。
	 */
	@Test
	public void testGetXMLTagAfterContextDefinition() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getElJarPath()} 的测试方法。
	 */
	@Test
	public void testGetElJarPath() {
		
	}

	/**
	 * {@link com.sysdeo.eclipse.tomcat.Tomcat6Bootstrap#getAnnotationsJarPath()} 的测试方法。
	 */
	@Test
	public void testGetAnnotationsJarPath() {
		
	}

}
