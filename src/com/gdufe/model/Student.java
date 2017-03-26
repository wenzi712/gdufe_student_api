package com.gdufe.model;

import java.util.Date;

public class Student {
	private String id;	//学号
	private String name;
	private String sex;
	private String clazz;	//班级
	private String major;	//专业
	private String college;
	private String creditCardId;
	private Date EnrollTime;
	
	
	
	
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getCreditCardId() {
		return creditCardId;
	}
	public void setCreditCardId(String creditCardId) {
		this.creditCardId = creditCardId;
	}
	public Date getEnrollTime() {
		return EnrollTime;
	}
	public void setEnrollTime(Date enrollTime) {
		EnrollTime = enrollTime;
	}
	
}
