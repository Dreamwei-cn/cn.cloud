package cn.cloud.user_api.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class TestMain {
	
	public static void subList() {
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		List<String> suList = list.subList(0, 2);
		System.out.println("sublist :" + suList);
	}

	public static void main(String[] args) {
		// subList();
		nameOld();
	}
	
	public static void nameNew() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-- MM-- dd");
		LocalDate date = LocalDate.now();
		String time = formatter.format(date);
		System.out.println(date);
		LocalDateTime localDateTime = LocalDateTime.now();
		int  day = localDateTime.getDayOfMonth();
		LocalDateTime time3 = localDateTime.plusDays(5L);
		System.out.println("time3 "+ time3);
		System.out.println("  get: "+ day);
		String time1 = formatter.format(localDateTime);
		Instant instant = Instant.now() ;
		System.out.println(time);
		System.out.println(time1);
		System.out.println(instant);
	}
	public static void nameOld() {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getWeekYear());
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println(calendar.getTime().toString());
		
	}
	
	

}
