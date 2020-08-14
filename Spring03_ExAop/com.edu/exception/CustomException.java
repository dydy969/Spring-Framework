package common.exception;

//Exception 객체를 상속
public class CustomException extends Exception{

	//message : Exception을 생성할 때 생성자의 매개변수로
	// 문자열로 넘기면, getMessage 메서드를 통해 다시 사용할 수 있다.
	public CustomException(String message) {
		super(message);
	}
}
