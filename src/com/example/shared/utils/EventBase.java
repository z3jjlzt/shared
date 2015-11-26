package com.example.shared.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)//Target自己也用了自己来声明自己,只能用在ANNOTATION_TYPE之上. 
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
	Class<?> listenerType();
	String listenerSetter();
	String methodname();
}
