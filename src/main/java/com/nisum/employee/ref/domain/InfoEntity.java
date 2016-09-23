package com.nisum.employee.ref.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Info")
public class InfoEntity {
	
	@Id
	String key;
	List<String> value;
}
