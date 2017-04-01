package com.gdufe.model;

public class Course {
	private String courseName;
	private String teacherName;
	private String courseWeek;	//从第几周到第几周
	private String location;	//上课地点
	private String courseTime;
	private int day;			//周几的课
	
	public String getCourseName() {
		return courseName;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
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
		return "course name:"+this.courseName+" "
				+"teacher name:"+this.teacherName+" "
				+"course week:"+this.courseWeek+" "
				+"location:"+this.location+" "
				+"courseTime:"+this.courseTime+" "
				+"day:"+this.day;
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
					&&this.getTeacherName().equals(another.getTeacherName())
					&&this.getDay()==another.getDay()){
				return true;
			}
					
		}
		return false;
	}
	
	
}
