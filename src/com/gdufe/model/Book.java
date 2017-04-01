package com.gdufe.model;

import java.util.ArrayList;

public class Book {
	private String title;	//����
	private String bookInfo;	//�鱾��������Ϣ������&������
	private String borrowed;	//�ɽ踴��
	private String sum;		//�ݲظ���
	private String index;	//�鱾������
	private ArrayList<BookItem> items;
	public String getItemLink() {
		return itemLink;
	}
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}

	private String itemLink;	//��itemLinkҳ���һ������
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setBorrowed(String borrowed) {
		this.borrowed = borrowed;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	
	public ArrayList<BookItem> getItems() {
		return items;
	}
	public void setItems(ArrayList<BookItem> items) {
		this.items = items;
	}
	public String getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(String bookInfo) {
		this.bookInfo = bookInfo;
	}
	public String getBorrowed() {
		return borrowed;
	}
	public String getSum() {
		return sum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String toString(){
		return "������ "+this.title
				+"\n"+this.sum
				+"\n"+this.borrowed
				+"\n�鱾��Ϣ:"+this.bookInfo;
	}
	
}
