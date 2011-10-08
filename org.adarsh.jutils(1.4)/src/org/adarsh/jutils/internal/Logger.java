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

import org.adarsh.jutils.JUtilsPlugin;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * A simple wrapper over the eclipse built-in logger.
 * 
 * @author Adarsh
 * 
 * @version 1.0, 10th December 2006
 */
public class Logger {
	/**
	 * This instance will be used in all the log methods.
	 */
	private static final ILog LOG = JUtilsPlugin.getDefault().getLog();

	/**
	 * Logs a message at debug level.
	 * 
	 * @param message
	 *            the message to be logged.
	 */
	public static void debug(String message) {
		IStatus status = new Status(Status.OK, JUtilsPlugin.PLUGIN_ID,
				Status.OK, message, null);

		LOG.log(status);
	}

	/**
	 * Logs a message at information level.
	 * 
	 * @param message
	 *            the message to be logged.
	 */
	public static void info(String message) {
		IStatus status = new Status(Status.INFO, JUtilsPlugin.PLUGIN_ID,
				Status.OK, message, null);

		LOG.log(status);
	}

	/**
	 * Logs a message at warning level.
	 * 
	 * @param message
	 *            the message to be logged.
	 */
	public static void warn(String message) {
		IStatus status = new Status(Status.WARNING, JUtilsPlugin.PLUGIN_ID,
				Status.OK, message, null);

		LOG.log(status);
	}

	/**
	 * Logs a message at error level.
	 * 
	 * @param message
	 *            the message to be logged.
	 */
	public static void error(String message) {
		IStatus status = new Status(Status.ERROR, JUtilsPlugin.PLUGIN_ID,
				Status.OK, message, null);

		LOG.log(status);
	}

	/**
	 * Logs a message at error level. This method must be used for logging
	 * exceptions.
	 * 
	 * @param message
	 *            the message to be logged.
	 * 
	 * @param throwable
	 *            the exception to be logged.
	 */
	public static void error(String message, Throwable throwable) {
		IStatus status = new Status(Status.ERROR, JUtilsPlugin.PLUGIN_ID,
				Status.OK, message, throwable);

		LOG.log(status);
	}
	
	
	
}
