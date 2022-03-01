package xlash.bot.khux.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import xlash.bot.khux.GameEnum;
import xlash.bot.khux.sheduler.Scheduler;

public class LuxTimes {
	
	public static String[] doubleLuxStartNA = new String[] {"02:00:00", "08:00:00", "14:00:00", "20:00:00"};
	public static String[] doubleLuxStartJP = new String[] {"12:00:00", "22:00:00"};
	public static String[] doubleLuxStopNA = new String[] {"03:00:00", "09:00:00", "15:00:00", "21:00:00"};
	public static String[] doubleLuxStopJP = new String[] {"13:00:00", "23:00:00"};
	
	public static String[] dailyRaidStartNA = new String[] {};
	public static String[] dailyRaidStopNA = new String[] {};
	public static String[] dailyRaidStartJP = new String[] {};
	public static String[] dailyRaidStopJP = new String[] {};
	
	/**
	 * Gets the time difference in minutes for the next double lux time.
	 * @param game
	 * @return time difference in minutes
	 */
	public static int timeDifference(GameEnum game) {
		String[] start;
		SimpleDateFormat sdf;
		if(game.equals(GameEnum.NA)) {
			start = doubleLuxStartNA;
			sdf = Scheduler.SDF_NA;
		}else {
			start = doubleLuxStartJP;
			sdf = Scheduler.SDF_JP;
		}
		
		int closest = Integer.MAX_VALUE;
		Date now = new Date();
		Date currentDay = new Date(now.getTime());
		currentDay.setHours(0);
		currentDay.setMinutes(0);
		currentDay.setSeconds(0);
		for(String time : start) {
			try {
				Date luxTime = new Date(sdf.parse(time).getTime() + currentDay.getTime());
				int difference = (int) ((luxTime.getTime() - now.getTime())/1000);
				if(difference < 0) {
					difference += 86400;
				}
				if(difference < closest) {
					closest = difference;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return closest/60;
	}

}
