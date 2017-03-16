import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

import de.cron.ComplexCronDefinition;
import de.cron.Cron;
import de.cron.CronDefinition;

public class CronTest {

	@Test
	public void testEveryMinuteEveryHourEveryDayEveryMonthEveryDayOfWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* * * * *", cron.toString());
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificMinutes() {
		CronDefinition cron = Cron.cron().inTheseMinutes(0, 3, 59).everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("0,3,59 * * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificMinute() {
		CronDefinition cron = Cron.cron().inTheseMinutes(30).everyHour().everyDay().everyMonth().everyDayOfWeek();
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
			Cron.cron().inTheseMinutes(60).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMinuteRange() {
		CronDefinition cron = Cron.cron().fromMinute(2).untilMinute(3).everyHour().everyDay().everyMonth().everyDayOfWeek();
		assertEquals("2-3 * * * *", cron.toString());
	}
	
	@Test
	public void testMinuteRangeEqualValues() {
		try {
			Cron.cron().fromMinute(12).untilMinute(12).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMinuteRangeFromGreaterThanUntil() {
		try {
			Cron.cron().fromMinute(24).untilMinute(23).everyHour().everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificHours() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(6, 12, 18).everyDay().everyMonth().everyDayOfWeek();
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
	
	@Test
	public void testHourRange() {
		CronDefinition cron = Cron.cron().everyMinute().fromHour(6).untilHour(12).everyDay().everyMonth().everyDayOfWeek();
		assertEquals("* 6-12 * * *", cron.toString());
	}
	
	@Test
	public void testHourRangeEqualValues() {
		try {
			Cron.cron().everyMinute().fromHour(20).untilHour(20).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testHourRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().fromHour(15).untilHour(14).everyDay().everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testEveryMinuteEveryHourDatePeriod() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 7, 15));
		assertEquals(2, cron.size());
		assertEquals("* * 15-30 6 *", cron.get(0).toString());
		assertEquals("* * 1-15 7 *", cron.get(1).toString());
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodFromEqualsUntil() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 15));
		assertEquals(1, cron.size());
		assertEquals("* * 15 6 *", cron.get(0).toString());
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodUntilBeforeFrom() {
		try {
			Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 14));
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDays() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(1, 15, 31).everyMonth().everyDayOfWeek();
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
	
	@Test
	public void testDayRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().fromDay(30).untilDay(31).everyMonth().everyDayOfWeek();
		assertEquals("* * 30-31 * *", cron.toString());
	}
	
	@Test
	public void testDayRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(1).untilDay(1).everyMonth().everyDayOfWeek();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(10).untilDay(9).everyMonth().everyDayOfWeek();
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
