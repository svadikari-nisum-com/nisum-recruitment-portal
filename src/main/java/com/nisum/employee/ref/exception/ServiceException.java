/**
 * 
 */
package com.nisum.employee.ref.exception;

/**
 * @author NISUM
 *
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2718042103788377219L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Exception e) {
		super(e);
	}
	
}
