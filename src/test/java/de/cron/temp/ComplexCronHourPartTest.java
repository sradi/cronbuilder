package de.cron.temp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cron.units.Day;
import de.cron.units.Hour;

public class ComplexCronHourPartTest {
	
	private ComplexCronDayPart singleDayCron;
	
	private ComplexCronHourPart singleHourCron;
	private ComplexCronHourPart twoHoursCron;
	private ComplexCronHourPart severalHoursOnSingleDayCron;
	private ComplexCronHourPart severalHoursOnTwoDaysCron;
	private ComplexCronHourPart severalHoursInSeveralDaysInSameMonthCron;
	private ComplexCronHourPart severalHoursInSeveralDaysInTwoMonthsCron;
	private ComplexCronHourPart severalHoursInSeveralDaysInSeveralMonthsCron;

	@Before
	public void setupSimpleCronDefinitions() {
		ComplexCronMonthPart singleMonthCron = new ComplexCronMonthPart(Month.MARCH, Month.MARCH);
		ComplexCronMonthPart twoMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.APRIL);
		ComplexCronMonthPart severalMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.AUGUST);

		this.singleDayCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(2));
		ComplexCronDayPart twoDaysCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(3));
		ComplexCronDayPart severalDaysSameMonthCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(15));
		ComplexCronDayPart severalDaysTwoMonthsCron = new ComplexCronDayPart(twoMonthsCron, Day.fromInt(2), Day.fromInt(15));
		ComplexCronDayPart severalDaysSeveralsMonthsCron = new ComplexCronDayPart(severalMonthsCron, Day.fromInt(2), Day.fromInt(15));
		
		this.singleHourCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(20));
		this.twoHoursCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(21));
		this.severalHoursOnSingleDayCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(19), Hour.fromInt(23));
		this.severalHoursOnTwoDaysCron = new ComplexCronHourPart(twoDaysCron, Hour.fromInt(19), Hour.fromInt(23));
		this.severalHoursInSeveralDaysInSameMonthCron = new ComplexCronHourPart(severalDaysSameMonthCron, Hour.fromInt(19), Hour.fromInt(23));
		this.severalHoursInSeveralDaysInTwoMonthsCron = new ComplexCronHourPart(severalDaysTwoMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
		this.severalHoursInSeveralDaysInSeveralMonthsCron = new ComplexCronHourPart(severalDaysSeveralsMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
	}

	@SuppressWarnings("unused")
	@Test
	public void testFromHourAfterUntilHour() {
		try {
			new ComplexCronHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(19));
			fail("'from' is after 'until' on the same day");
		} catch (Exception e) {
		}
	}

	//********************************************************
	@Test
	public void testSameHour_getParts() {
		List<CronExpression> crons = singleHourCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("20 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testSameHour_getPartsInternal() {
		List<CronExpression> crons = singleHourCron.getPartsInternal();
		assertEquals(1, crons.size());
		assertEquals("20 2 3", crons.get(0).toString());
	}
	
	//********************************************************
	@Test
	public void testTwoHourPeriod_getParts() {
		List<CronExpression> crons = twoHoursCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("20-21 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testTwoHourPeriod_getPartsInternal() {
		List<CronExpression> crons = twoHoursCron.getPartsInternal();
		assertEquals(2, crons.size());
		assertEquals("20 2 3", crons.get(0).toString());
		assertEquals("21 2 3", crons.get(1).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralHoursOnSingleDayPeriod_getParts() {
		List<CronExpression> crons = severalHoursOnSingleDayCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("19-23 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testSeveralHoursOnSingleDayPeriod_getPartsInternal() {
		List<CronExpression> crons = severalHoursOnSingleDayCron.getPartsInternal();
		assertEquals(3, crons.size());
		assertEquals("19 2 3", crons.get(0).toString());
		assertEquals("20-22 2 3", crons.get(1).toString());
		assertEquals("23 2 3", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralHoursInTwoDaysPeriod_getParts() {
		List<CronExpression> crons = severalHoursOnTwoDaysCron.getParts();
		assertEquals(2, crons.size());
		assertEquals("19-23 2 3", crons.get(0).toString());
		assertEquals("0-23 3 3", crons.get(1).toString());
	}
	
	@Test
	public void testSeveralHoursInTwoDaysPeriod_getPartsInternal() {
		List<CronExpression> crons = severalHoursOnTwoDaysCron.getPartsInternal();
		assertEquals(4, crons.size());
		assertEquals("19 2 3", crons.get(0).toString());
		assertEquals("20-23 2 3", crons.get(1).toString());
		assertEquals("0-22 3 3", crons.get(2).toString());
		assertEquals("23 3 3", crons.get(3).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralHoursInSeveralDaysInSameMonthPeriod_getParts() {
		List<CronExpression> crons = severalHoursInSeveralDaysInSameMonthCron.getParts();
		assertEquals(3, crons.size());
		assertEquals("19-23 2 3", crons.get(0).toString());
		assertEquals("* 3-14 3", crons.get(1).toString());
		assertEquals("0-23 15 3", crons.get(2).toString());
	}
	
	@Test
	public void testSeveralHoursInSeveralDaysInSameMonthPeriod_getPartsInternal() {
		List<CronExpression> crons = severalHoursInSeveralDaysInSameMonthCron.getPartsInternal();
		assertEquals(5, crons.size());
		assertEquals("19 2 3", crons.get(0).toString());
		assertEquals("20-23 2 3", crons.get(1).toString());
		assertEquals("* 3-14 3", crons.get(2).toString());
		assertEquals("0-22 15 3", crons.get(3).toString());
		assertEquals("23 15 3", crons.get(4).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralHoursInSeveralDaysInTwoMonthPeriod_getParts() {
		List<CronExpression> crons = severalHoursInSeveralDaysInTwoMonthsCron.getParts();
		assertEquals(4, crons.size());
		assertEquals("19-23 2 3", crons.get(0).toString());
		assertEquals("* 3-31 3", crons.get(1).toString());
		assertEquals("* 1-14 4", crons.get(2).toString());
		assertEquals("0-23 15 4", crons.get(3).toString());
	}
	
	@Test
	public void testSeveralHoursInSeveralDaysInTwoMonthPeriod_getPartsInternal() {
		List<CronExpression> crons = severalHoursInSeveralDaysInTwoMonthsCron.getPartsInternal();
		assertEquals(6, crons.size());
		assertEquals("19 2 3", crons.get(0).toString());
		assertEquals("20-23 2 3", crons.get(1).toString());
		assertEquals("* 3-31 3", crons.get(2).toString());
		assertEquals("* 1-14 4", crons.get(3).toString());
		assertEquals("0-22 15 4", crons.get(4).toString());
		assertEquals("23 15 4", crons.get(5).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralHoursInSeveralDaysInSeveralMonthPeriod_getParts() {
		List<CronExpression> crons = severalHoursInSeveralDaysInSeveralMonthsCron.getParts();
		assertEquals(5, crons.size());
		assertEquals("19-23 2 3", crons.get(0).toString());
		assertEquals("* 3-31 3", crons.get(1).toString());
		assertEquals("* * 4-7", crons.get(2).toString());
		assertEquals("* 1-14 8", crons.get(3).toString());
		assertEquals("0-23 15 8", crons.get(4).toString());
	}
	
	@Test
	public void testSeveralHoursInSeveralDaysInSeveralMonthPeriod_getPartsInternal() {
		List<CronExpression> crons = severalHoursInSeveralDaysInSeveralMonthsCron.getPartsInternal();
		assertEquals(7, crons.size());
		assertEquals("19 2 3", crons.get(0).toString());
		assertEquals("20-23 2 3", crons.get(1).toString());
		assertEquals("* 3-31 3", crons.get(2).toString());
		assertEquals("* * 4-7", crons.get(3).toString());
		assertEquals("* 1-14 8", crons.get(4).toString());
		assertEquals("0-22 15 8", crons.get(5).toString());
		assertEquals("23 15 8", crons.get(6).toString());
	}
	
}
