/**
 * 
 */
package com.nisum.employee.ref.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author NISUM CONSULTING
 *
 */
public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1076260393766812035L;
	private List<ErrorDTO> errors = new ArrayList<>();
	private Date createDtm;
	private Date updateDtm;

	public Date getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

	public Date getUpdateDtm() {
		return updateDtm;
	}

	public void setUpdateDtm(Date updateDtm) {
		this.updateDtm = updateDtm;
	}

	/**
	 * @return the errors
	 */
	public List<ErrorDTO> getErrors() {
		return CollectionUtils.isNotEmpty(errors) ? errors : null;
	}

	/**
	 * @param errors
	 *            the errors to set
	 */
	public void setErrors(List<ErrorDTO> errors) {
		this.errors = errors;
	}

	public boolean hasErrors() {
		return CollectionUtils.isNotEmpty(errors);
	}

	public void addError(String code, String desc) {
		errors.add(new ErrorDTO(code, desc));
	}

	public void addError(ErrorDTO errorDto) {
		errors.add(errorDto);
	}

	public void addErrors(List<ErrorDTO> errorsList) {
		errors.addAll(errorsList);
	}
}
