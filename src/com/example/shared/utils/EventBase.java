package com.example.shared.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)//Target�Լ�Ҳ�����Լ��������Լ�,ֻ������ANNOTATION_TYPE֮��. 
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
	Class<?> listenerType();
	String listenerSetter();
	String methodname();
}
