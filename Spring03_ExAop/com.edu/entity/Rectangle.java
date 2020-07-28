package com.edu.entity;

public class Rectangle{

	private String title;
	private int width;
	private int heigth;
	
	public Rectangle() {
		// TODO Auto-generated constructor stub
	}

	public Rectangle(String title, int width, int heigth) {
		super();
		this.title = title;
		this.width = width;
		this.heigth = heigth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	
	public void viewSize() {
		System.out.println(getTitle() + "의 넓이 : " + (getWidth() * getHeigth() ));
	}


	
	
	
}
