package proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 方法委托类
 * */
public class MyInvocationHandler implements InvocationHandler {

	private Object object;
	
	public MyInvocationHandler(Object object){
		this.object = object;
	}
	//处理代理实例上的方法调用并返回结果。
	//Object proxy   调用该方法的代理实例
	//Method method  所述方法对应于调用代理实例上的接口方法的实例
	//Object[] args  包含的方法调用传递代理实例的参数值的对象的数组，或null如果接口方法没有参数。
	
	// 在invoke方法中对被代理类进行增强或做一下其他操作。
	// 该方法负责集中处理动态代理类上的所有方法调用。第一个参数既是代理类实例，第二个参数是被调用的方法对象
	// 第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("MyInvocationHandler invoke begin");
		System.out.println("代理类: " + proxy.getClass().getName());
		System.out.println("method: " + method.getName());
		for(Object o : args){
			System.out.println("arg: " + o);
		}
		//在调用被代理类的方法前，对该方法的增强
		//通过反射调用 被代理类的方法
		method.invoke(object, args);
		//在调用被代理类的方法后，处理一些业务
		System.out.println("MyInvocationHandler invoke end");
		return null;
	}
	
	public static void main(String[] args) {
		//创建需要被调用的类
		Student s = new Student();
		//这一句是生成代理类的class文件，前提是你要在工程根目录下创建com/sun/proxy目录，不然会报不到路径的io异常
		System.getProperties().put("sun.misc.PrxoyGenerator.saveGeneratedFiles", "true");
		//获得加载被代理类的 类加载器，（当前线程的创建者提供类加载器）
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//指明被代理类实现的接口
		Class<?>[] interfaces = s.getClass().getInterfaces();
		//创建被代理类的委托类，之后想要调用被代理类的方法时，都会委托给这个类的invoke(Object proxy, Method method, Object[] args)方法
		MyInvocationHandler h = new MyInvocationHandler(s);
		//生成代理类（Proxy提供了创建动态代理类和实例的静态方法，它也是由这些方法创建的所有动态代理类的超类。 ）
		Person proxy = (Person)Proxy.newProxyInstance(loader, interfaces, h);//Proxy类的public static Object newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)方法内部通过拼接字节码的方式来创建代理类
		//调用代理类的方法，就会调用被代理类的委托类的invoke方法
		//通过代理类调用 被代理类的方法时，就可以将方法名和方法参数都委托给这个委托类的对象h。
		proxy.sayHello("zhangsan", 20);
		proxy.sayGoodBye(true, 100);
		System.out.println("end");
		
	}

}
/**
 * Proxy.newProxyInstance(loader, interfaces, h);   指定一个动态加载代理类的类加载器
 * Class<?>[] interfaces：                                                                                指明被代理类实现的接口，之后我们通过拼接字节码生成的类才能知道调用哪些方法。
 * InvocationHandler h：                                                                                      这是一个方法委托类，我们通过代理调用被代理类的方法时，就可以将方法名和方法参数都委托给这个委托类。
 * */

//jdk的代理让我们在不直接访问某些对象的情况下，通过代理机制也可以访问被代理对象的方法，
//这种技术可以应用在很多地方比如RPC框架，Spring AOP机制，但是我们看到jdk的代理机制必须要求被代理类实现某个方法，
//这样在生成代理类的时候才能知道重新那些方法。这样一个没有实现任何接口的类就无法通过jdk的代理机制进行代理，当然解决方法是使用cglib的代理机制进行代理。

//Proxy 的静态方法：
//方法 1: 该方法用于获取指定代理对象所关联的调用处理器
//static InvocationHandler getInvocationHandler(Object proxy) 

//方法 2：该方法用于获取关联于指定类装载器和一组接口的动态代理类的类对象
//static Class getProxyClass(ClassLoader loader, Class[] interfaces) 

//方法 3：该方法用于判断指定类对象是否是一个动态代理类
//static boolean isProxyClass(Class cl) 

//方法 4：该方法用于为指定类装载器、一组接口及调用处理器生成动态代理类实例
//static Object newProxyInstance(ClassLoader loader, Class[] interfaces, 
// InvocationHandler h)
//#########################################################
//java.lang.reflect.InvocationHandler：这是调用处理器接口，它自定义了一个 invoke 方法，用于集中处理在动态代理类对象上的方法调用，通常在该方法中实现对委托类的代理访问。
//清单 2. InvocationHandler 的核心方法
//该方法负责集中处理动态代理类上的所有方法调用。第一个参数既是代理类实例，第二个参数是被调用的方法对象
//第三个方法是调用参数。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
//Object invoke(Object proxy, Method method, Object[] args)

//#########################################################
//java.lang.ClassLoader：这是类装载器类，负责将类的字节码装载到 Java 虚拟机（JVM）中并为其定义类对象，然后该类才能被使用。Proxy 静态方法生成动态代理类同样需要通过类装载器来进行装载才能使用，它与普通类的唯一区别就是其字节码是由 JVM 在运行时动态生成的而非预存在于任何一个 .class 文件中。
//每次生成动态代理类对象时都需要指定一个类装载器对象（参见 Proxy 静态方法 4 的第一个参数）
