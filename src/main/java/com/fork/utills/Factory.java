package com.fork.utills;

public class Factory {
	public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		Class<?> c = Class.forName(className);
		Object obj = c.newInstance();
		return obj;
	}
}
