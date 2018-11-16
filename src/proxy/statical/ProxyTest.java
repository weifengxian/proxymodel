package proxy.statical;

/**
 * 代理类
 * */
public class ProxyTest implements Person{
	
	private Person o;
	
	public ProxyTest(Person o){
		this.o = o;
	}

	@Override
	public void sayHello(String content, int age) {
		System.out.println("ProxyTest sayHello begin");
		//在代理类的方法中，间接访问被代理对象的方法
		o.sayHello(content, age);
		
		System.out.println("ProxyTest sayHello end");
	}

	@Override
	public void sayGoodBye(boolean seeAgin, double time) {
		System.out.println("ProxyTest sayGoodBye begin");
		//在代理类的方法中，间接访问被代理对象的方法
		o.sayGoodBye(seeAgin, time);
		
		System.out.println("ProxyTest sayGoodBye end");
	}

	public static void main(String[] args) {
		//s为被代理的对象，某些情况下，我们不希望修改已有的代码，可以采用代理间接访问
		Student s = new Student();
		//创建代理类对象
		ProxyTest proxy = new ProxyTest(s);
		//调用代理类对象的方法，间接访问被代理的对象的方法
		proxy.sayHello("welcome to java", 20);
		System.out.println("************");
		
		//调用代理类对象的方法，间接访问被代理的对象的方法
		proxy.sayGoodBye(true, 100);
	}
}
