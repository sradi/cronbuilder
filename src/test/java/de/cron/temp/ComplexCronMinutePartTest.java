package de.cron.temp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

public class ComplexCronMinutePartTest {
	
	private ComplexCronHourPart singleHourCron;

	private ComplexCronMinutePart singleMinuteCron;
	private ComplexCronMinutePart twoMinutesCron;
	private ComplexCronMinutePart severalMinutesInSameHourCron;
	private ComplexCronMinutePart severalMinutesInTwoHoursCron;
	private ComplexCronMinutePart severalMinutesInSeveralHoursOnSameDayCron;
	private ComplexCronMinutePart severalMinutesInSeveralHoursOnTwoDaysCron;
	private ComplexCronMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth;
	private ComplexCronMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInTwoMonths;
	private ComplexCronMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInSeveralMonths;

	@Before
	public void setupSimpleCronDefinitions() {
		ComplexCronMonthPart singleMonthCron = new ComplexCronMonthPart(Month.MARCH, Month.MARCH);
		ComplexCronMonthPart twoMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.APRIL);
		ComplexCronMonthPart severalMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.AUGUST);

		ComplexCronDayPart singleDayCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(2));
		ComplexCronDayPart twoDaysCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(3));
		ComplexCronDayPart severalDaysSameMonthCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(15));
		ComplexCronDayPart severalDaysTwoMonthsCron = new ComplexCronDayPart(twoMonthsCron, Day.fromInt(2), Day.fromInt(15));
		ComplexCronDayPart severalDaysSeveralsMonthsCron = new ComplexCronDayPart(severalMonthsCron, Day.fromInt(2), Day.fromInt(15));
		
		this.singleHourCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(20));
		ComplexCronHourPart twoHoursCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(21));
		ComplexCronHourPart severalHoursOnSingleDayCron = new ComplexCronHourPart(singleDayCron, Hour.fromInt(19), Hour.fromInt(23));
		ComplexCronHourPart severalHoursOnTwoDaysCron = new ComplexCronHourPart(twoDaysCron, Hour.fromInt(19), Hour.fromInt(23));
		ComplexCronHourPart severalHoursInSeveralDaysInSameMonthCron = new ComplexCronHourPart(severalDaysSameMonthCron, Hour.fromInt(19), Hour.fromInt(23));
		ComplexCronHourPart severalHoursInSeveralDaysInTwoMonthsCron = new ComplexCronHourPart(severalDaysTwoMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
		ComplexCronHourPart severalHoursInSeveralDaysInSeveralMonthsCron = new ComplexCronHourPart(severalDaysSeveralsMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
		
		this.singleMinuteCron = new ComplexCronMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(55));
		this.twoMinutesCron = new ComplexCronMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(56));
		this.severalMinutesInSameHourCron = new ComplexCronMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(59));
		this.severalMinutesInTwoHoursCron = new ComplexCronMinutePart(twoHoursCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSameDayCron = new ComplexCronMinutePart(severalHoursOnSingleDayCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnTwoDaysCron = new ComplexCronMinutePart(severalHoursOnTwoDaysCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth = new ComplexCronMinutePart(severalHoursInSeveralDaysInSameMonthCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInTwoMonths = new ComplexCronMinutePart(severalHoursInSeveralDaysInTwoMonthsCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInSeveralMonths = new ComplexCronMinutePart(severalHoursInSeveralDaysInSeveralMonthsCron, Minute.fromInt(55), Minute.fromInt(55));
	}

	@SuppressWarnings("unused")
	@Test
	public void testFromHourAfterUntilHour() {
		try {
			new ComplexCronMinutePart(singleHourCron, Minute.fromInt(51), Minute.fromInt(50));
			fail("'from' is after 'until' on the same day");
		} catch (Exception e) {
		}
	}

	//********************************************************
	@Test
	public void testSameMinute_getParts() {
		List<CronExpression> crons = singleMinuteCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("55 20 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testSameMinute_getPartsInternal() {
		List<CronExpression> crons = singleMinuteCron.getPartsInternal();
		assertEquals(1, crons.size());
		assertEquals("55 20 2 3", crons.get(0).toString());
	}
	
	//********************************************************
	@Test
	public void testTwoMinutePeriod_getParts() {
		List<CronExpression> crons = twoMinutesCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("55-56 20 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testTwoMinutePeriod_getPartsInternal() {
		List<CronExpression> crons = twoMinutesCron.getPartsInternal();
		assertEquals(2, crons.size());
		assertEquals("55 20 2 3", crons.get(0).toString());
		assertEquals("56 20 2 3", crons.get(1).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInSameHourPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInSameHourCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("55-59 20 2 3", crons.get(0).toString());
	}
	
	@Test
	public void testSeveralMinutesInSameHourPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSameHourCron.getPartsInternal();
		assertEquals(3, crons.size());
		assertEquals("55 20 2 3", crons.get(0).toString());
		assertEquals("56-58 20 2 3", crons.get(1).toString());
		assertEquals("59 20 2 3", crons.get(2).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInTwoHoursPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInTwoHoursCron.getParts();
		assertEquals(2, crons.size());
		assertEquals("55-59 20 2 3", crons.get(0).toString());
		assertEquals("0-55 21 2 3", crons.get(1).toString());
	}
	
	@Test
	public void testSeveralMinutesInTwoHoursPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInTwoHoursCron.getPartsInternal();
		assertEquals(4, crons.size());
		assertEquals("55 20 2 3", crons.get(0).toString());
		assertEquals("56-59 20 2 3", crons.get(1).toString());
		assertEquals("0-54 21 2 3", crons.get(2).toString());
		assertEquals("55 21 2 3", crons.get(3).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInSeveralHoursOnSameDayPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSameDayCron.getParts();
		assertEquals(3, crons.size());
		assertEquals("55-59 19 2 3", crons.get(0).toString());
		assertEquals("* 20-22 2 3", crons.get(1).toString());
		assertEquals("0-55 23 2 3", crons.get(2).toString());
	}
	
	@Test
	public void testSeveralMinutesInSeveralHoursOnSameDayPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSameDayCron.getPartsInternal();
		assertEquals(5, crons.size());
		assertEquals("55 19 2 3", crons.get(0).toString());
		assertEquals("56-59 19 2 3", crons.get(1).toString());
		assertEquals("* 20-22 2 3", crons.get(2).toString());
		assertEquals("0-54 23 2 3", crons.get(3).toString());
		assertEquals("55 23 2 3", crons.get(4).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInSeveralHoursOnTwoDaysPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnTwoDaysCron.getParts();
		assertEquals(4, crons.size());
		assertEquals("55-59 19 2 3", crons.get(0).toString());
		assertEquals("* 20-23 2 3", crons.get(1).toString());
		assertEquals("* 0-22 3 3", crons.get(2).toString());
		assertEquals("0-55 23 3 3", crons.get(3).toString());
	}
	
	@Test
	public void testSeveralMinutesInSeveralHoursOnTwoDaysPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnTwoDaysCron.getPartsInternal();
		assertEquals(6, crons.size());
		crons.forEach(System.out::println);
		assertEquals("55 19 2 3", crons.get(0).toString());
		assertEquals("56-59 19 2 3", crons.get(1).toString());
		assertEquals("* 20-23 2 3", crons.get(2).toString());
		assertEquals("* 0-22 3 3", crons.get(3).toString());
		assertEquals("0-54 23 3 3", crons.get(4).toString());
		assertEquals("55 23 3 3", crons.get(5).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSameMonthPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth.getParts();
		assertEquals(5, crons.size());
		assertEquals("55-59 19 2 3", crons.get(0).toString());
		assertEquals("* 20-23 2 3", crons.get(1).toString());
		assertEquals("* * 3-14 3", crons.get(2).toString());
		assertEquals("* 0-22 15 3", crons.get(3).toString());
		assertEquals("0-55 23 15 3", crons.get(4).toString());
	}
	
	@Test
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSameMonthPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth.getPartsInternal();
		assertEquals(7, crons.size());
		assertEquals("55 19 2 3", crons.get(0).toString());
		assertEquals("56-59 19 2 3", crons.get(1).toString());
		assertEquals("* 20-23 2 3", crons.get(2).toString());
		assertEquals("* * 3-14 3", crons.get(3).toString());
		assertEquals("* 0-22 15 3", crons.get(4).toString());
		assertEquals("0-54 23 15 3", crons.get(5).toString());
		assertEquals("55 23 15 3", crons.get(6).toString());
}
	
	@Test
	@Ignore
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInTwoMonthsPeriod_getParts() {
		fail();
	}
	
	@Test
	@Ignore
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInTwoMonthsPeriod_getPartsInternal() {
		fail();
	}
	
	@Test
	@Ignore
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSeveralMonthsPeriod_getParts() {
		fail();
	}
	
	@Test
	@Ignore
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSeveralMonthsPeriod_getPartsInternal() {
		fail();
	}
	
}
