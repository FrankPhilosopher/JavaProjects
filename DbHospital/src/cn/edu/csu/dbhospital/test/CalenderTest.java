package cn.edu.csu.dbhospital.test;

import java.util.Calendar;
import java.util.Date;

public class CalenderTest {

	public static void main(String[] args) {

		Calendar calendarToday = Calendar.getInstance();
		printCalendar(calendarToday);

		calendarToday.add(Calendar.DATE, 30);
		printCalendar(calendarToday);

		Date date1 = new Date();
		Date date2 = new Date();
		System.out.println(date1.equals(date2));

	}

	private static void printCalendar(Calendar calendar) {
		System.out.println(calendar.getFirstDayOfWeek());
		System.out.println(calendar.get(Calendar.YEAR));
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.get(Calendar.DATE));
	}

}
