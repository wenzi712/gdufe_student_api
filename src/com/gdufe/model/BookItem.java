package com.gdufe.model;

/*
 * ÿ��book�¶�������item
 * */
public class BookItem {
	private String index;	//�����
	private String locate;	//�ݲص�
	private String status;	//�鿯״̬

	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getLocate() {
		return locate;
	}
	public void setLocate(String locate) {
		this.locate = locate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString(){
		return "\n������"+this.index
				+"\n�ݲص�ַ��"+this.locate
				+"\n����״̬:"+this.status;
	}
	
}
