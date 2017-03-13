import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.Test;

import de.cron.Cron;
import de.cron.CronDefinition;
import de.cron.Minute;

public class CronTest {

	@Test
	public void testEveryMinuteEveryHourEveryDayEveryMonthEveryDayOfWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* * * * *", cron.toString());
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificMinutes() {
		CronDefinition cron = Cron.cron().inTheseMinutes(Minute.fromInt(0), Minute.fromInt(3), Minute.fromInt(59)).everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("0,3,59 * * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificMinute() {
		CronDefinition cron = Cron.cron().inTheseMinutes(Minute.fromInt(30)).everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("30 * * * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificMinutes() {
		try {
			Cron.cron().inTheseMinutes().everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificMinutes() {
		try {
			Cron.cron().inTheseMinutes(Minute.fromInt(60)).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificHours() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(6,12,18).everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* 6,12,18 * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificHour() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(6).everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* 6 * * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificHour() {
		try {
			Cron.cron().everyMinute().inTheseHours().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificHours() {
		try {
			Cron.cron().everyMinute().inTheseHours(25).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDays() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(1,15,31).everyMonth().everyDayOfWeek();
		assertEquals("* * 1,15,31 * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificDay() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(13).everyMonth().everyDayOfWeek();
		assertEquals("* * 13 * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificDay() {
		try {
			Cron.cron().everyMinute().everyHour().onTheseDays().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificDays() {
		try {
			Cron.cron().everyMinute().everyHour().onTheseDays(32).everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificMonths() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths(Month.JANUARY, Month.MARCH, Month.DECEMBER).everyDayOfWeek();
		assertEquals("* * * 1,3,12 *", cron.toString());
	}
	
	@Test
	public void testOneSpecificMonth() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths(Month.JULY).everyDayOfWeek();
		assertEquals("* * * 7 *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificMonth() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDaysOfTheWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY);
		assertEquals("* * * * 1,4,7", cron.toString());
	}
	
	@Test
	public void testOneSpecificDayOfTheWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek(DayOfWeek.SATURDAY);
		assertEquals("* * * * 6", cron.toString());
	}
	
	@Test
	public void testEmptySpecificDayOfTheWeek() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
}
