package com.seeka.app.exception;

public class Test {

	public static void main(final String[] args) {
		A a = new A();
		a.setX(10);
		System.out.println("exe1 ::" + a.getX());
		method1(a);
		System.out.println("exe2 ::" + a.getX());
		method2(a);
		System.out.println("exe3 :: " + a.getX());
	}

	static A method1(A a) {
		a = new A();
		a.setX(20);
		return a;
	}

	static A method2(A a) {
		a = new A();
		a.setX(30);
		return a;
	}

}
