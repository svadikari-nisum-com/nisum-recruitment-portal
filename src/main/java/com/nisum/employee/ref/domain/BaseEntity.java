package com.nisum.employee.ref.domain;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public abstract class BaseEntity implements Persistable<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1280774154161658754L;
	@CreatedDate
	private Date createDtm;
	@LastModifiedDate
	private Date updateDtm;
	
	private boolean persisted;
	
	public abstract String getId();
	
	@Override
	public boolean isNew() {
		return !persisted;
	}

}
