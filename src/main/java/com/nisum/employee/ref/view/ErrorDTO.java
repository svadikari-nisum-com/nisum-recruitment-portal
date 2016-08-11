/**
 * 
 */
package com.nisum.employee.ref.view;

import java.io.Serializable;


/**
 * @author NISUM
 *
 */

public class ErrorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115821257866287633L;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ErrorDTO() {
	}

	public ErrorDTO(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private String code;
	private String desc;
}
