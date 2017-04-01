package com.gdufe.model;

import java.util.ArrayList;

public class Book {
	private String title;	//书名
	private String bookInfo;	//书本的著作信息，作者&出版社
	private String borrowed;	//可借复本
	private String sum;		//馆藏复本
	private String index;	//书本索引号
	private ArrayList<BookItem> items;
	public String getItemLink() {
		return itemLink;
	}
	public void setItemLink(String itemLink) {
		this.itemLink = itemLink;
	}

	private String itemLink;	//到itemLink页面的一个链接
	
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
		return "书名： "+this.title
				+"\n"+this.sum
				+"\n"+this.borrowed
				+"\n书本信息:"+this.bookInfo;
	}
	
}
