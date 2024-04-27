package com.toypjt.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserListDaoParamVO {
	private int page;
	private int pageSize;
	private String sortColumn;	
}
