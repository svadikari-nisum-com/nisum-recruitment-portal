package com.nisum.employee.ref.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract  class AuditEntity implements Persistable<String> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3134126905704075123L;

	@CreatedDate
	private DateTime createdDate;
	
	@CreatedBy
	private String createdBy;
	
	@LastModifiedDate
	private DateTime lastModifiedDate;
	
	@LastModifiedBy
	private String lastModifiedBy;
	
	@Version
	private Long version;

	private boolean persisted;
	
	public abstract String getId();
	
	@Override
	public boolean isNew() {
		return !persisted;
	}

}
