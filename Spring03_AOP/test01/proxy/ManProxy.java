package test01.proxy;

public class ManProxy implements Develop{

	@Override
	public void classWork() {
		
		//부기능들
		System.out.println("출석카드를 찍는다.");
		try {
			// Man클래스에 주기능 요청
			new Man().classWork();
		}catch (Exception e) {
			System.out.println("쉬는 날이였다.");
		} finally {
			System.out.println("집에 간다");
		}
	}
	
	
}
