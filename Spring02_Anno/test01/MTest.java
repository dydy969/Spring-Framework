package test01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MTest {

	public static void main(String[] args) {
		// Spring MVC 하면 자동으로 해준다
		ApplicationContext factory = new ClassPathXmlApplicationContext("test01/applicationContext.xml");
		
		System.out.println(factory.getBean("myNickName"));
		
	}
}
