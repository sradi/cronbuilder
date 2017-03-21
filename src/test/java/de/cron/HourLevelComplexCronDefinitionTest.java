package de.cron;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cron.elements.CronMinute;
import de.cron.elements.CronSpecificMinutes;
import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

@SuppressWarnings("unused")
public class HourLevelComplexCronDefinitionTest {
	
	private DayLevelComplexCronDefinition singleDaySingleMonth;
	private DayLevelComplexCronDefinition twoDaysSingleMonth;
	private DayLevelComplexCronDefinition severalDaysSingleMonth;
	private DayLevelComplexCronDefinition sameDayTwoMonths;

	@Before
	public void setupSimpleCronDefinitions() {
		SimpleCronDefinition everyMinuteCron = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(CronMinute.EVERY_MINUTE)
				.build();
		MonthLevelComplexCronDefinition singleMonthCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.MARCH);
		MonthLevelComplexCronDefinition twoMonthsCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.APRIL);
		MonthLevelComplexCronDefinition severalMonthsCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.JULY); 
		
		this.singleDaySingleMonth = new DayLevelComplexCronDefinition(singleMonthCronDef, Day.fromInt(6), Day.fromInt(6));
		this.twoDaysSingleMonth = new DayLevelComplexCronDefinition(singleMonthCronDef, Day.fromInt(6), Day.fromInt(7));
		this.severalDaysSingleMonth = new DayLevelComplexCronDefinition(singleMonthCronDef, Day.fromInt(2), Day.fromInt(5));
		this.sameDayTwoMonths = new DayLevelComplexCronDefinition(twoMonthsCronDef, Day.fromInt(20), Day.fromInt(20));
	}

	@Test
	public void testFromHourAfterUntilHour_singleDay_singleMonth() {
		try {
			new HourLevelComplexCronDefinition(singleDaySingleMonth, Hour.fromInt(5), Hour.fromInt(4));
			fail("'from' is after 'until' on the same day");
		} catch (Exception e) {
		}
	}

	@Test
	public void testFromHourEqualsUntilHour_singleDay_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(singleDaySingleMonth, Hour.fromInt(2), Hour.fromInt(2));
		assertEquals(1, crons.size());
		assertEquals("* 2 20 3 *", crons.get(0).toString());
	}

	@Test
	public void testFromHourBeforeUntilHour_singleDay_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(singleDaySingleMonth, Hour.fromInt(1), Hour.fromInt(5));
		assertEquals(1, crons.size());
		assertEquals("* 1-5 6 3 *", crons.get(0).toString());
	}

	//********************************************************
	@Test
	public void testFromHourAfterUntilHour_twoDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(twoDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(10));
		assertEquals(2, crons.size());
		assertEquals("* 20-24 6 3 *", crons.get(0).toString());
		assertEquals("* 1-10 7 3 *", crons.get(1).toString());
	}
	
	@Test
	public void testFromHourEqualsUntilHour_twoDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(twoDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(20));
		assertEquals(2, crons.size());
		assertEquals("* 20-24 6 3 *", crons.get(0).toString());
		assertEquals("* 1-20 7 3 *", crons.get(1).toString());
	}
	
	@Test
	public void testFromHourBeforeUntilHour_twoDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(twoDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(22));
		assertEquals(2, crons.size());
		assertEquals("* 20-24 6 3 *", crons.get(0).toString());
		assertEquals("* 1-22 7 3 *", crons.get(1).toString());
	}
	
	//********************************************************
	@Test
	public void testFromHourAfterUntilHour_severalDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(severalDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(10));
		assertEquals(3, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-22 3 *", crons.get(1).toString());
		assertEquals("* 1-10 23 3 *", crons.get(2).toString());
	}
	
	@Test
	public void testFromHourEqualsUntilHour_severalDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(severalDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(20));
		assertEquals(3, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-22 3 *", crons.get(1).toString());
		assertEquals("* 1-20 23 3 *", crons.get(2).toString());
	}
	
	@Test
	public void testFromHourBeforeUntilHour_severalDays_singleMonth() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(severalDaysSingleMonth, Hour.fromInt(20), Hour.fromInt(22));
//		assertEquals(3, crons.size());
		crons.forEach(System.out::println);
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-22 3 *", crons.get(1).toString());
		assertEquals("* 1-22 23 3 *", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testFromHourAfterUntilHour_sameDay_twoMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(20), Hour.fromInt(10));
		assertEquals(4, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * 1-19 4 *", crons.get(2).toString());
		assertEquals("* 0-10 20 4 *", crons.get(3).toString());
	}

	@Test
	public void testFromHourEqualsUntilHour_sameDay_twoMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(20), Hour.fromInt(20));
		assertEquals(4, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * 1-19 4 *", crons.get(2).toString());
		assertEquals("* 0-20 20 4 *", crons.get(3).toString());
	}
	
	@Test
	public void testFromHourBeforeUntilHour_sameDay_twoMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(20), Hour.fromInt(20));
		assertEquals(4, crons.size());
		assertEquals("* 10-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * 1-19 4 *", crons.get(2).toString());
		assertEquals("* 0-20 20 4 *", crons.get(3).toString());
	}
	
	//********************************************************
	@Test
	public void testFromHourAfterUntilHour_sameDay_severalMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(20), Hour.fromInt(10));
		assertEquals(5, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * * 4-6 *", crons.get(2).toString());
		assertEquals("* * 1-19 7 *", crons.get(3).toString());
		assertEquals("* 0-10 20 7 *", crons.get(4).toString());
	}

	@Test
	public void testFromHourEqualsUntilHour_sameDay_severalMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(20), Hour.fromInt(20));
		assertEquals(5, crons.size());
		assertEquals("* 20-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * * 4-6 *", crons.get(2).toString());
		assertEquals("* * 1-19 7 *", crons.get(3).toString());
		assertEquals("* 0-10 20 7 *", crons.get(4).toString());
	}
	
	@Test
	public void testFromHourBeforeUntilHour_sameDay_severalMonths() {
		HourLevelComplexCronDefinition crons = new HourLevelComplexCronDefinition(sameDayTwoMonths, Hour.fromInt(10), Hour.fromInt(20));
		assertEquals(5, crons.size());
		assertEquals("* 10-24 20 3 *", crons.get(0).toString());
		assertEquals("* * 21-31 3 *", crons.get(1).toString());
		assertEquals("* * * 4-6 *", crons.get(2).toString());
		assertEquals("* * 1-19 7 *", crons.get(3).toString());
		assertEquals("* 0-20 20 7 *", crons.get(4).toString());
	}
	
	// Every Minute vom 20.3. 10:30 bis 20.7. 20:45
//		assertEquals("30-59 10 20 3 *", crons.get(0).toString());
//		assertEquals("* 11-24 20 3 *", crons.get(1).toString());
//		assertEquals("* * 21-31 3 *", crons.get(2).toString());
//		assertEquals("* * * 4-6 *", crons.get(3).toString());
//		assertEquals("* * 1-19 7 *", crons.get(4).toString());
//		assertEquals("* 0-19 20 7 *", crons.get(5).toString());
//		assertEquals("0-45 20 20 7 *", crons.get(6).toString());

}
