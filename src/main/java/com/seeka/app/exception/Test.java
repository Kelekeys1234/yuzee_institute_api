package com.seeka.app.exception;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test {

	public static void main(final String[] args) throws ParseException {
		String da = "2019-03-29";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(da, formatter);
		System.out.println(date);

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
