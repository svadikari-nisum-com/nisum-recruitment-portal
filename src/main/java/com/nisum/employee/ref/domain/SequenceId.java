package com.nisum.employee.ref.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "sequence")
public class SequenceId {

	@Id
	private String id;

	private long seq;

}
