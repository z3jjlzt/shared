package com.example.shared.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.app.Activity;
import android.util.Log;
import android.view.View;

/**
 * @author z3jjlzt
 *注释注入辅助类
 */
public class ViewInjectUtils {
	private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

	public static void inject(Activity activity) {
		injectViews(activity);
		injectEvents(activity);
	}

	private static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			LztViewInject viewInject = field.getAnnotation(LztViewInject.class);
			if (viewInject != null) {
				Log.e("sb", field.getName() + "");
				int viewid = viewInject.value();
				if (viewid != -1) {

					try {
						Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
						Object object = method.invoke(activity, viewid);
						field.setAccessible(true);
						field.set(activity, object);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	private static void injectEvents(Activity activity) {
		Class<? extends Activity> clazz =activity.getClass();
		Method[] methods = clazz.getMethods();
		for(Method method : methods){
			Annotation[] annotations = method.getAnnotations();
			for(Annotation annotation: annotations){
				Class<? extends Annotation> annotationtype = annotation.annotationType();
				EventBase eventBase =annotationtype.getAnnotation(EventBase.class);
				if(eventBase!=null){
					String listenerSetter =eventBase.listenerSetter();
					String methodname = eventBase.methodname();
				Class<?> listenerType= 	 eventBase.listenerType();
				
				try {
					Method method1 =annotationtype.getDeclaredMethod("value");
					int[] viewids=(int[])method1.invoke(annotation, null);
					   DynamicHandler handler = new DynamicHandler(activity);  
					                           handler.addMethod(methodname, method);  
					                           Object listener = Proxy.newProxyInstance(  
					                                   listenerType.getClassLoader(),  
					                                   new Class<?>[] { listenerType }, handler);  
					                           //遍历所有的View，设置事件  
					                           for (int viewId : viewids)  
					                           {  
					                               View view = activity.findViewById(viewId);  
					                               Method setEventListenerMethod = view.getClass()  
					                                       .getMethod(listenerSetter, listenerType);  
					                               setEventListenerMethod.invoke(view, listener);  
					                           } 

				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
				
			
		}

	}
}
