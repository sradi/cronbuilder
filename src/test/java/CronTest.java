import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.Month;

import org.junit.Test;

import de.cron.Cron;
import de.cron.CronDefinition;
import de.cron.Day;
import de.cron.Hour;
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
	
	@Test
	public void testMinuteRange() {
		CronDefinition cron = Cron.cron().fromMinute(Minute.fromInt(2)).untilMinute(Minute.fromInt(3)).everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("2-3 * * * *", cron.toString());
	}
	
	@Test
	public void testMinuteRangeEqualValues() {
		try {
			Cron.cron().fromMinute(Minute.fromInt(12)).untilMinute(Minute.fromInt(12)).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMinuteRangeFromGreaterThanUntil() {
		try {
			Cron.cron().fromMinute(Minute.fromInt(24)).untilMinute(Minute.fromInt(23)).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificHours() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(Hour.fromInt(6),Hour.fromInt(12),Hour.fromInt(18)).everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* 6,12,18 * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificHour() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(Hour.fromInt(6)).everyDay().everyMonth().everyDayOfWeek();
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
			Cron.cron().everyMinute().inTheseHours(Hour.fromInt(25)).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testHourRange() {
		CronDefinition cron = Cron.cron().everyMinute().fromHour(Hour.fromInt(6)).untilHour(Hour.fromInt(12)).everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* 6-12 * * *", cron.toString());
	}
	
	@Test
	public void testHourRangeEqualValues() {
		try {
			Cron.cron().everyMinute().fromHour(Hour.fromInt(20)).untilHour(Hour.fromInt(20)).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testHourRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().fromHour(Hour.fromInt(15)).untilHour(Hour.fromInt(14)).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDays() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(Day.fromInt(1),Day.fromInt(15),Day.fromInt(31)).everyMonth().everyDayOfWeek();
		assertEquals("* * 1,15,31 * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificDay() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(Day.fromInt(13)).everyMonth().everyDayOfWeek();
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
			Cron.cron().everyMinute().everyHour().onTheseDays(Day.fromInt(32)).everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().fromDay(Day.fromInt(30)).untilDay(Day.fromInt(31)).everyMonth().everyDayOfWeek();
		assertEquals("* * 30-31 * *", cron.toString());
	}
	
	@Test
	public void testDayRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(Day.fromInt(1)).untilDay(Day.fromInt(1)).everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(Day.fromInt(10)).untilDay(Day.fromInt(9)).everyMonth().everyDayOfWeek();
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
	
	@Test
	public void testMonthRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.MAY).untilMonth(Month.JUNE).everyDayOfWeek();
		assertEquals("* * * 5-6 *", cron.toString());
	}
	
	@Test
	public void testMonthRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.OCTOBER).untilMonth(Month.OCTOBER).everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMonthRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.DECEMBER).untilMonth(Month.NOVEMBER).everyDayOfWeek();
		} catch (Exception e) {
			fail("Bei Monaten sol z. B. 11-2 (Nov-Feb) erlaubt sein.");
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
	
	@Test
	public void testDayOfWeekRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.SATURDAY).untilDayOfWeek(DayOfWeek.SUNDAY);
		assertEquals("* * * * 6-7", cron.toString());
	}
	
	@Test
	public void testDayOfWeekRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.WEDNESDAY).untilDayOfWeek(DayOfWeek.WEDNESDAY);
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayOfWeekRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.TUESDAY).untilDayOfWeek(DayOfWeek.MONDAY);
		} catch (Exception e) {
			fail("Bei Wochentagen soll z. B. 6-2 (Sa - Di) erlaubt sein.");
		}
	}
	
}
