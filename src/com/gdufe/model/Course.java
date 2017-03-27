package com.gdufe.model;

/*
 * 	软件工程课程设计
	尹华讲师（高校）
	9-16(周)
	实验楼802
	[01-02]节
 * */
public class Course {
	private String courseName;
	private String teacherName;
	private String courseWeek;	//从第几周到第几周
	private String location;	//上课地点
	private String courseTime;
	private String day;			//周几的课
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseWeek() {
		return courseWeek;
	}
	public void setCourseWeek(String courseWeek) {
		this.courseWeek = courseWeek;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCourseTime() {
		return courseTime;
	}
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}
	
	public String toString(){
		return "course name:"+this.courseName
				+"teacher name:"+this.teacherName
				+"course week:"+this.courseWeek
				+"location:"+this.location
				+"courseTime:"+this.courseTime;
	}
	
	@Override
	public boolean equals(Object obj){
		if(this==obj){
			return true;
		}
		if(obj instanceof Course){
			Course another = (Course)obj;
			if(this.getCourseName().equals(another.getCourseName())
					&&this.getCourseTime().equals(another.getCourseTime())
					&&this.getCourseWeek().equals(another.getCourseWeek())
					&&this.getLocation().equals(another.getLocation())
					&&this.getTeacherName().equals(another.getTeacherName())){
				return true;
			}
					
		}
		return false;
	}
	
	
}
