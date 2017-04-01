package com.gdufe.model;

/*
 * 每个book下都有数个item
 * */
public class BookItem {
	private String index;	//索书号
	private String locate;	//馆藏地
	private String status;	//书刊状态

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
		return "\n索引："+this.index
				+"\n馆藏地址："+this.locate
				+"\n借阅状态:"+this.status;
	}
	
}
