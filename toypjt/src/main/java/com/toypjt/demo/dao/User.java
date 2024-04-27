package com.toypjt.demo.dao;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Getter;
import lombok.Setter;

@DynamicUpdate
@Entity
@Getter 
@Setter
@Table(name = "users")
public class User {

	@Id
	@Column(name = "user_id", columnDefinition = "VARCHAR(10)")
	private String userId;
	@Column(columnDefinition = "VARCHAR(15)")
	private String userPw;
	@Column(columnDefinition = "VARCHAR(10)")
	private String nickNm;
	@Column(columnDefinition = "VARCHAR(5)")
	private String userNm;
	@Column(columnDefinition = "VARCHAR(15)")
	private String telNum;
	@Column(columnDefinition = "VARCHAR(100)")
	private String email;
	@CreationTimestamp 
	private LocalDateTime createdDt; 
	@UpdateTimestamp
	private LocalDateTime updatedDt; 
}
