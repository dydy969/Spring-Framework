package di.setter;

import di.tire.Tire;

public class Car {

	private Tire tire;
	
	public void setTire(Tire tire) {
		
		//의존성 주입되는 곳
		this.tire = tire;
	}
	
	public String geTire() {
		return tire.getProduct()+"장착함!!";
	}
	
}
