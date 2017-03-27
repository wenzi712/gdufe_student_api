package com.gdufe.model;

public class Score {
	private String term;	//开课日期
	private String id;		//课程编号
	private String name;	//课程名称
	private int score;
	private float credit;		//学分
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
	}
	
	@Override
	public boolean equals(Object obj){
		if(this==obj)
			return true;
		if(obj instanceof Score){
			Score another = (Score)obj;
			if(this.getId().equals(another.getId())||this.getName().equals(another.getName()))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "name:"+this.getName()+" "
				+"id:"+this.getId()+" "
				+"score:"+this.getScore();
	}
	
}
