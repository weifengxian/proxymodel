package proxy.statical;
/**
 * 被代理的类，实现了一个接口Person
 * */
public class Student implements Person{

	@Override
	public void sayHello(String content, int age) {
		System.out.println("student sayhello " + content + " " + age);
	}

	@Override
	public void sayGoodBye(boolean seeAgin, double time) {
		System.out.println("student saygoodBye " + time + " " + seeAgin);
	}

}
