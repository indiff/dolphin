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

/**
 * A JUtils specific runtime exception.
 * 
 * @author Dolphin code
 * 
 * @version 1.0, 10th December 2006
 */
public class JUtilsException extends RuntimeException {
	private static final long serialVersionUID = -5355004212146678695L;

	/**
	 * Constructs a <tt>JUtilsException</tt> instance.
	 */
	public JUtilsException() {
		super();
	}

	/**
	 * Constructs a <tt>JUtilsException</tt> instance using the message.
	 * 
	 * @param message
	 *            the message to wrap over.
	 */
	public JUtilsException(String message) {
		super(message);
	}

	/**
	 * Constructs a <tt>JUtilsException</tt> instance using the
	 * <tt>Throwable</tt> instance.
	 * 
	 * @param throwable
	 *            the <tt>Throwable</tt> instance to wrap over.
	 */
	public JUtilsException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * Constructs a <tt>JUtilsException</tt> instance using the message and the
	 * <tt>Throwable</tt> instance.
	 * 
	 * @param message
	 *            the message for the exception.
	 * 
	 * @param throwable
	 *            the <tt>Throwable</tt> instance to wrap over.
	 */
	public JUtilsException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
