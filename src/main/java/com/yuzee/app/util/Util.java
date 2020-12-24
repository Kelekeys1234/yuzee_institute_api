package com.yuzee.app.util;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Util {

	public static Integer perPageCount = 10;

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

	public static String getOneTimePassword() throws Exception {
		Random random = new Random();
		return "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9);
	}

	public static String getOneTimePasswordForReset() throws Exception {
		Random random = new Random();
		return "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + ""
				+ random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9);
	}

	public static int getYearsBetweenDates(Date first, Date second) {
		return (int) ChronoUnit.YEARS.between(first.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
				second.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}

	public static String[] getEnumNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

	public static boolean containsIgnoreCase(List<String> lst, String searchKey) {
		AtomicBoolean result = new AtomicBoolean();
		lst.stream().forEach(e -> {
			if (searchKey.equalsIgnoreCase(e)) {
				result.set(true);
			}
		});
		return result.get();
	}
}
