package de.cron;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cron.elements.CronMinute;
import de.cron.elements.CronSpecificDays;
import de.cron.elements.CronSpecificMinutes;
import de.cron.units.Day;
import de.cron.units.Minute;

@SuppressWarnings("unused")
public class DayLevelComplexCronDefinitionTest {
	
	private MonthLevelComplexCronDefinition singleMonthComplexCronDef;
	private MonthLevelComplexCronDefinition twoMonthsComplexCronDef;
	private MonthLevelComplexCronDefinition severalMonthsComplexCronDef;

	@Before
	public void setupSimpleCronDefinitions() {
		SimpleCronDefinition everyMinuteCron = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(CronMinute.EVERY_MINUTE)
				.build();
		this.singleMonthComplexCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.MARCH);
		this.twoMonthsComplexCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.APRIL);
		this.severalMonthsComplexCronDef = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.JUNE);
	}

	@Test
	public void testFromDayAfterUntilDay_singleMonth() {
		try {
			new ComplexCronDefinition(singleMonthComplexCronDef, Day.fromInt(5), Day.fromInt(4));
			fail("'from' is after 'until' in the same month");
		} catch (Exception e) {
		}
	}

	@Test
	public void testFromDayAfterUntilDay_twoMonths() {
		ComplexCronDefinition crons = new ComplexCronDefinition(twoMonthsComplexCronDef, Day.fromInt(5), Day.fromInt(4));
		assertEquals(2, crons.size());
		assertEquals("* * 5-31 3 *", crons.get(0).toString());
		assertEquals("* * 1-4 4 *", crons.get(1).toString());
	}
	
	@Test
	public void testFromDayAfterUntilDay_severalMonths() {
		ComplexCronDefinition crons = new ComplexCronDefinition(severalMonthsComplexCronDef, Day.fromInt(5), Day.fromInt(4));
		assertEquals(3, crons.size());
		assertEquals("* * 5-31 3 *", crons.get(0).toString());
		assertEquals("* * * 4-5 *", crons.get(1).toString());
		assertEquals("* * 1-4 6 *", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testSameDay_singleMonth() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(singleMonthComplexCronDef, Day.fromInt(2), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * 2 3 *", crons.get(0).toString());
	}

	@Test
	public void testSameDay_twoMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(twoMonthsComplexCronDef, Day.fromInt(2), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(2, crons.size());
		assertEquals("* * 2-31 3 *", crons.get(0).toString());
		assertEquals("* * 1-2 4 *", crons.get(1).toString());
	}
	
	@Test
	public void testSameDay_severalMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(severalMonthsComplexCronDef, Day.fromInt(2), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
//		assertEquals(3, crons.size());
		for (SimpleCronDefinition cron : crons) {
			System.out.println(cron);
		}
		assertEquals("* * 2-31 3 *", crons.get(0).toString());
		assertEquals("* * * 4-5 *", crons.get(1).toString());
		assertEquals("* * 1-2 6 *", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testTwoDayPeriod_singleMonth() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(singleMonthComplexCronDef, Day.fromInt(1), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * 1-2 3 *", crons.get(0).toString());
	}

	@Test
	public void testTwoDayPeriod_twoMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(twoMonthsComplexCronDef, Day.fromInt(1), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(2, crons.size());
		assertEquals("* * 1-31 3 *", crons.get(0).toString());
		assertEquals("* * 1-2 4 *", crons.get(1).toString());
	}
	
	@Test
	public void testTwoDayPeriod_severalMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(severalMonthsComplexCronDef, Day.fromInt(1), Day.fromInt(2));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(3, crons.size());
		assertEquals("* * 1-31 3 *", crons.get(0).toString());
		assertEquals("* * * 4-5 *", crons.get(1).toString());
		assertEquals("* * 1-2 6 *", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralDaysPeriod_singleMonth() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(singleMonthComplexCronDef, Day.fromInt(1), Day.fromInt(5));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * 1-5 3 *", crons.get(0).toString());
	}

	@Test
	public void testSeveralDaysPeriod_twoMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(twoMonthsComplexCronDef, Day.fromInt(1), Day.fromInt(5));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(2, crons.size());
		assertEquals("* * 1-31 3 *", crons.get(0).toString());
		assertEquals("* * 1-5 4 *", crons.get(1).toString());
	}
	
	@Test
	public void testSeveralDaysPeriod_severalMonths() {
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(severalMonthsComplexCronDef, Day.fromInt(1), Day.fromInt(5));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(3, crons.size());
		assertEquals("* * 1-31 3 *", crons.get(0).toString());
		assertEquals("* * * 4-5 *", crons.get(1).toString());
		assertEquals("* * 1-5 6 *", crons.get(2).toString());
	}
	
	@Test
	public void testSeveralDaysPeriod_severalMonths_withSpecificMinutes() {
		SimpleCronDefinition specificMinutesCron = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(new CronSpecificMinutes(Minute.fromInt(15), Minute.fromInt(30), Minute.fromInt(45)))
				.build();
		MonthLevelComplexCronDefinition monthLevelCron = new MonthLevelComplexCronDefinition(specificMinutesCron, Month.MARCH, Month.JUNE);
		ComplexCronDefinition dayLevelCron = new ComplexCronDefinition(monthLevelCron, Day.fromInt(1), Day.fromInt(5));
		List<SimpleCronDefinition> crons = dayLevelCron.getCrons();
		assertEquals(3, crons.size());
		assertEquals("15,30,45 * 1-31 3 *", crons.get(0).toString());
		assertEquals("15,30,45 * * 4-5 *", crons.get(1).toString());
		assertEquals("15,30,45 * 1-5 6 *", crons.get(2).toString());
	}
	
	// TODO mehr Testfaelle fuer fest definierte 'Hours'

}
