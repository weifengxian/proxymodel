package proxy.assign;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler{

	private Object object;
	
	public MyInvocationHandler(Object object){
		this.object = object;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		
		
		
		method.invoke(object, args);
		return null;
	}

}
