package com.gdufe.model;

/*
 * 	������̿γ����
	������ʦ����У��
	9-16(��)
	ʵ��¥802
	[01-02]��
 * */
public class Course {
	private String courseName;
	private String teacherName;
	private String courseWeek;	//�ӵڼ��ܵ��ڼ���
	private String location;	//�Ͽεص�
	private String courseTime;
	private String day;			//�ܼ��Ŀ�
	
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
