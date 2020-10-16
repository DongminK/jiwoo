package jiwoo.cache;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CacheInfoFactory implements MethodInterceptor {

	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

		boolean isSuperMethod = false;
		for (Method cacheMethod : CacheInfo.class.getMethods()) {

			if (method.getName().equals(cacheMethod.getName())) {
				isSuperMethod = true;
				break;
			}
		}

		if (!isSuperMethod && object instanceof CacheInfo) {
			((CacheInfo) object).destory();
		}

		Object returnValue = methodProxy.invokeSuper(object, args);

		if (!isSuperMethod && object instanceof CacheInfo) {
			((CacheInfo) object).update();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static <T extends CacheInfo> T newInstance(Class<T> clz) {

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clz);
		enhancer.setCallback(new CacheInfoFactory());

		T obj = (T) enhancer.create();
		obj.buildFactory();

		return obj;

	}
}
