package tw.edu.chit.service.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;

public class SyncStudentProxy implements InvocationHandler {

	Logger log = Logger.getLogger(SyncStudentProxy.class);

	private Object delegate;

	public Object bind(Object delegate) {
		this.delegate = delegate;
		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
				delegate.getClass().getInterfaces(), this);
	}

	public Object invoke(Object arg0, Method method, Object[] args)
			throws Throwable {

		Object result = null;
		try {
			result = method.invoke(delegate, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return result;
	}

}
