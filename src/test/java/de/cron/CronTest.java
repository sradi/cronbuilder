package de.cron;
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
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("* * * * *", cron.toString());
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificMinutes() {
		CronDefinition cron = Cron.cron().inTheseMinutes(0, 3, 59).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("0,3,59 * * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificMinute() {
		CronDefinition cron = Cron.cron().inTheseMinutes(30).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("30 * * * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificMinutes() {
		try {
			Cron.cron().inTheseMinutes().everyHour().everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificMinutes() {
		try {
			Cron.cron().inTheseMinutes(60).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMinuteRange() {
		CronDefinition cron = Cron.cron().fromMinute(2).untilMinute(3).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("2-3 * * * *", cron.toString());
	}
	
	@Test
	public void testMinuteRangeEqualValues() {
		try {
			Cron.cron().fromMinute(12).untilMinute(12).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMinuteRangeFromGreaterThanUntil() {
		try {
			Cron.cron().fromMinute(24).untilMinute(23).everyHour().everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testEveryMinuteWithDayAndDatePeriodWithinOneDay() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().from(12, LocalDate.of(2017, 6, 15)).until(20, LocalDate.of(2017, 6, 15)).get();
		assertEquals(1, cron.size());
		assertEquals("* 12-20 15 6 *", cron.get(0).toString());
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriod() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().from(12, LocalDate.of(2017, 6, 15)).until(15, LocalDate.of(2017, 6, 16)).get();
		assertEquals(2, cron.size());
		assertEquals("* 12-24 15 6 *", cron.get(0).toString());
		assertEquals("* 1-15 16 6 *", cron.get(1).toString());
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriodOverMultipleDays() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().from(12, LocalDate.of(2017, 6, 15)).until(15, LocalDate.of(2017, 6, 20)).get();
		assertEquals(3, cron.size());
		assertEquals("* 12-24 15 6 *", cron.get(0).toString());
		assertEquals("* * 16-19 6 *", cron.get(1).toString());
		assertEquals("* 1-15 20 6 *", cron.get(2).toString());
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriodOverMultipleDaysAndMultipleMonths() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().from(18, LocalDate.of(2017, 6, 15)).until(6, LocalDate.of(2017, 10, 5)).get();
		assertEquals(5, cron.size());
		assertEquals("* 18-24 15 6 *", cron.get(0).toString());
		assertEquals("* * 16-30 6 *", cron.get(1).toString());
		assertEquals("* * * 7-9 *", cron.get(2).toString());
		assertEquals("* * 1-4 10 *", cron.get(3).toString());
		assertEquals("* 1-6 5 10 *", cron.get(4).toString());
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriodFromEqualsUntil() {
		try {
			int hour = 13;
			LocalDate date = LocalDate.of(2017, 6, 15);
			Cron.cron().everyMinute().from(hour, date).until(hour, date);
			fail();
		} catch (Exception e) {
		}
	}

	@Test
	public void testEveryMinuteWithDayAndDatePeriodUntilHourBeforeFromHour() {
		try {
			Cron.cron().everyMinute().from(12, LocalDate.of(2017, 6, 15)).until(6, LocalDate.of(2017, 6, 15));
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriodUntilDateBeforeFromDate() {
		try {
			Cron.cron().everyMinute().from(6, LocalDate.of(2017, 6, 15)).until(12, LocalDate.of(2017, 6, 14));
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testEveryMinuteDayAndDatePeriodCertainDaysOfWeek() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 15)).fromDayOfWeek(DayOfWeek.SATURDAY).untilDayOfWeek(DayOfWeek.SUNDAY).get();
		assertEquals(1, cron.size());
		assertEquals("* * 15 6 *", cron.get(0).toString());
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificHours() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(6, 12, 18).everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("* 6,12,18 * * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificHour() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(6).everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("* 6 * * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificHour() {
		try {
			Cron.cron().everyMinute().inTheseHours().everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificHours() {
		try {
			Cron.cron().everyMinute().inTheseHours(25).everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testHourRange() {
		CronDefinition cron = Cron.cron().everyMinute().fromHour(6).untilHour(12).everyDay().everyMonth().everyDayOfWeek().get();
		assertEquals("* 6-12 * * *", cron.toString());
	}
	
	@Test
	public void testHourRangeEqualValues() {
		try {
			Cron.cron().everyMinute().fromHour(20).untilHour(20).everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testHourRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().fromHour(15).untilHour(14).everyDay().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testEveryMinuteEveryHourDatePeriodWithinOneMonth() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 20)).get();
		assertEquals(1, cron.size());
		assertEquals("* * 15-20 6 *", cron.get(0).toString());
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriod() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 7, 15)).get();
		assertEquals(2, cron.size());
		assertEquals("* * 15-30 6 *", cron.get(0).toString());
		assertEquals("* * 1-15 7 *", cron.get(1).toString());
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodOverMultipleMonths() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 9, 5)).get();
		assertEquals(4, cron.size());
		assertEquals("* * 15-30 6 *", cron.get(0).toString());
		assertEquals("* * * 7 *", cron.get(1).toString());
		assertEquals("* * * 8 *", cron.get(2).toString());
		assertEquals("* * 1-5 9 *", cron.get(3).toString());
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodFromEqualsUntil() {
		try {
			LocalDate date = LocalDate.of(2017, 6, 15);
			Cron.cron().everyMinute().everyHour().from(date).until(date);
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodUntilBeforeFrom() {
		try {
			Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 14));
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testEveryMinuteEveryHourDatePeriodCertainDaysOfWeek() {
		ComplexCronDefinition cron = Cron.cron().everyMinute().everyHour().from(LocalDate.of(2017, 6, 15)).until(LocalDate.of(2017, 6, 15)).fromDayOfWeek(DayOfWeek.SATURDAY).untilDayOfWeek(DayOfWeek.SUNDAY).get();
		assertEquals(1, cron.size());
		assertEquals("* * 15 6 *", cron.get(0).toString());
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDays() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(1, 15, 31).everyMonth().everyDayOfWeek().get();
		assertEquals("* * 1,15,31 * *", cron.toString());
	}
	
	@Test
	public void testOneSpecificDay() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().onTheseDays(13).everyMonth().everyDayOfWeek().get();
		assertEquals("* * 13 * *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificDay() {
		try {
			Cron.cron().everyMinute().everyHour().onTheseDays().everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testInvalidSpecificDays() {
		try {
			Cron.cron().everyMinute().everyHour().onTheseDays(32).everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().fromDay(30).untilDay(31).everyMonth().everyDayOfWeek().get();
		assertEquals("* * 30-31 * *", cron.toString());
	}
	
	@Test
	public void testDayRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(1).untilDay(1).everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().fromDay(10).untilDay(9).everyMonth().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificMonths() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths(Month.JANUARY, Month.MARCH, Month.DECEMBER).everyDayOfWeek().get();
		assertEquals("* * * 1,3,12 *", cron.toString());
	}
	
	@Test
	public void testOneSpecificMonth() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths(Month.JULY).everyDayOfWeek().get();
		assertEquals("* * * 7 *", cron.toString());
	}
	
	@Test
	public void testEmptySpecificMonth() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().inTheseMonths().everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMonthRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.MAY).untilMonth(Month.JUNE).everyDayOfWeek().get();
		assertEquals("* * * 5-6 *", cron.toString());
	}
	
	@Test
	public void testMonthRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.OCTOBER).untilMonth(Month.OCTOBER).everyDayOfWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testMonthRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().fromMonth(Month.DECEMBER).untilMonth(Month.NOVEMBER).everyDayOfWeek().get();
		} catch (Exception e) {
			fail("Bei Monaten sol z. B. 11-2 (Nov-Feb) erlaubt sein.");
		}
	}
	
	//*************************************************
	@Test
	public void testSeveralSpecificDaysOfTheWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.SUNDAY).get();
		assertEquals("* * * * 1,4,7", cron.toString());
	}
	
	@Test
	public void testOneSpecificDayOfTheWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek(DayOfWeek.SATURDAY).get();
		assertEquals("* * * * 6", cron.toString());
	}
	
	@Test
	public void testEmptySpecificDayOfTheWeek() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().onTheseDaysOfTheWeek().get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayOfWeekRange() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.SATURDAY).untilDayOfWeek(DayOfWeek.SUNDAY).get();
		assertEquals("* * * * 6-7", cron.toString());
	}
	
	@Test
	public void testDayOfWeekRangeEqualValues() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.WEDNESDAY).untilDayOfWeek(DayOfWeek.WEDNESDAY).get();
			fail();
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testDayOfWeekRangeFromGreaterThanUntil() {
		try {
			Cron.cron().everyMinute().everyHour().everyDay().everyMonth().fromDayOfWeek(DayOfWeek.TUESDAY).untilDayOfWeek(DayOfWeek.MONDAY).get();
		} catch (Exception e) {
			fail("Bei Wochentagen soll z. B. 6-2 (Sa - Di) erlaubt sein.");
		}
	}
	
	//*************************************************
	@Test
	public void testEveryMinuteWithDefaultValues() {
		CronDefinition cron = Cron.cron().everyMinute().get();
		assertEquals("* * * * *", cron.toString());
	}
	
	@Test
	public void testSpecificMinutesWithDefaultValues() {
		CronDefinition cron = Cron.cron().inTheseMinutes(2, 12, 22).get();
		assertEquals("2,12,22 * * * *", cron.toString());
	}
	
	@Test
	public void testMinuteRangeWithDefaultValues() {
		CronDefinition cron = Cron.cron().fromMinute(22).untilMinute(24).get();
		assertEquals("22-24 * * * *", cron.toString());
	}

	@Test
	public void testEveryHourWithDefaultValues() {
		CronDefinition cron = Cron.cron().inTheseMinutes(0,59).everyHour().get();
		assertEquals("0,59 * * * *", cron.toString());
	}
	
	@Test
	public void testSpecificHoursWithDefaultValues() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(19, 21, 23).get();
		assertEquals("* 19,21,23 * * *", cron.toString());
	}
	
	@Test
	public void testHourRangeWithDefaultValues() {
		CronDefinition cron = Cron.cron().fromMinute(22).untilMinute(24).fromHour(6).untilHour(9).get();
		assertEquals("22-24 6-9 * * *", cron.toString());
	}

	@Test
	public void testEveryDayWithDefaultValues() {
		CronDefinition cron = Cron.cron().inTheseMinutes(0,59).everyHour().everyDay().get();
		assertEquals("0,59 * * * *", cron.toString());
	}
	
	@Test
	public void testSpecificDaysWithDefaultValues() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(19, 21, 23).onTheseDays(1, 2, 3).get();
		assertEquals("* 19,21,23 1,2,3 * *", cron.toString());
	}
	
	@Test
	public void testDayRangeWithDefaultValues() {
		CronDefinition cron = Cron.cron().fromMinute(22).untilMinute(24).fromHour(6).untilHour(9).fromDay(2).untilDay(4).get();
		assertEquals("22-24 6-9 2-4 * *", cron.toString());
	}

	@Test
	public void testEveryMonthWithDefaultValues() {
		CronDefinition cron = Cron.cron().inTheseMinutes(0,59).everyHour().everyDay().everyMonth().get();
		assertEquals("0,59 * * * *", cron.toString());
	}
	
	@Test
	public void testSpecificMonthsWithDefaultValues() {
		CronDefinition cron = Cron.cron().everyMinute().inTheseHours(19, 21, 23).everyDay().inTheseMonths(Month.MARCH, Month.JUNE).get();
		assertEquals("* 19,21,23 * 3,6 *", cron.toString());
	}
	
	@Test
	public void testMonthRangeWithDefaultValues() {
		CronDefinition cron = Cron.cron().fromMinute(22).untilMinute(24).everyHour().fromDay(2).untilDay(4).fromMonth(Month.APRIL).untilMonth(Month.SEPTEMBER).get();
		assertEquals("22-24 * 2-4 4-9 *", cron.toString());
	}
}
