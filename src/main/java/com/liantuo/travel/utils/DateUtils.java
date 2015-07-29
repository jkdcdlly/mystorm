package com.liantuo.travel.utils;

public class DateUtils {
	public static String getDate(String reqTime) {
		return reqTime.substring(0, 10);
	}

	public static String getHour(String reqTime) {
		return reqTime.substring(11, 13);
	}
}
