package com.edu.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.edu.entity.Rectangle;
import com.edu.entity.Triangle;




public class Run {

	public static void main(String[] args) {
		
		ApplicationContext factory = new ClassPathXmlApplicationContext("com/edu/main/applicationContext.xml");
		
		Rectangle rc = (Rectangle) factory.getBean("rectangle");
		Triangle tn = (Triangle) factory.getBean("triangle");
		
		rc.setTitle("사각형");
		rc.setHeigth(15);
		rc.setWidth(15);
		
		tn.setTitle("삼각형");
		tn.setHeight(15);
		tn.setWidth(15);
		
		rc.viewSize();
		
		System.out.println("=================");
		
		tn.viewSize();
		
	}
}
