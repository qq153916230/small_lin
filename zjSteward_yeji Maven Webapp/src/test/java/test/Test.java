package test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		/*Date date = new Date();
		long date1 = date.getTime() - 1000*60*60*24;
		SimpleDateFormat  sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sd.format(date1));
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		System.out.println(yesterday);*/
		
		SimpleDateFormat  sd = new SimpleDateFormat("dd");
		System.out.println(sd.format(new Date()));
	}
}
