package di.constructor;

import di.tire.GoldTire;

public class Person {

	public static void main(String[] args) {
		
		Car car = new Car( new GoldTire());
		
		System.out.println(car.getTire());
	
		
	}
}
