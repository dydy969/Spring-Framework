package di.constructor;

import di.tire.Tire;

public class Car {

	private Tire tite;
	
	public Car(Tire tire) {
		//의존성 주입되는 곳 
		this.tite = tire;
	}
	
	
	public String getTire() {
		return tite.getProduct()+"장착함 !!";
	}
	
	
}
