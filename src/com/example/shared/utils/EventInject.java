package com.example.shared.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase
(listenerSetter = "setOnClickListener", methodname = "onClick", listenerType = View.OnClickListener.class)
public @interface EventInject {
int[] value();
}
