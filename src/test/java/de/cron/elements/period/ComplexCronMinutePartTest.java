package de.cron.elements.period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.cron.CronExpression;
import de.cron.elements.period.CronPeriodDayPart;
import de.cron.elements.period.CronPeriodHourPart;
import de.cron.elements.period.CronPeriodMinutePart;
import de.cron.elements.period.CronPeriodMonthPart;
import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

public class ComplexCronMinutePartTest {
	
	private CronPeriodHourPart singleHourCron;

	private CronPeriodMinutePart singleMinuteCron;
	private CronPeriodMinutePart twoMinutesCron;
	private CronPeriodMinutePart severalMinutesInSameHourCron;
	private CronPeriodMinutePart severalMinutesInTwoHoursCron;
	private CronPeriodMinutePart severalMinutesInSeveralHoursOnSameDayCron;
	private CronPeriodMinutePart severalMinutesInSeveralHoursOnTwoDaysCron;
	private CronPeriodMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth;
	private CronPeriodMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInTwoMonths;
	private CronPeriodMinutePart severalMinutesInSeveralHoursOnSeveralDaysCronInSeveralMonths;

	@Before
	public void setupSimpleCronDefinitions() {
		CronPeriodMonthPart singleMonthCron = new CronPeriodMonthPart(Month.MARCH, Month.MARCH);
		CronPeriodMonthPart twoMonthsCron = new CronPeriodMonthPart(Month.MARCH, Month.APRIL);
		CronPeriodMonthPart severalMonthsCron = new CronPeriodMonthPart(Month.MARCH, Month.AUGUST);

		CronPeriodDayPart singleDayCron = new CronPeriodDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(2));
		CronPeriodDayPart twoDaysCron = new CronPeriodDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(3));
		CronPeriodDayPart severalDaysSameMonthCron = new CronPeriodDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(15));
		CronPeriodDayPart severalDaysTwoMonthsCron = new CronPeriodDayPart(twoMonthsCron, Day.fromInt(2), Day.fromInt(15));
		CronPeriodDayPart severalDaysSeveralsMonthsCron = new CronPeriodDayPart(severalMonthsCron, Day.fromInt(2), Day.fromInt(15));
		
		this.singleHourCron = new CronPeriodHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(20));
		CronPeriodHourPart twoHoursCron = new CronPeriodHourPart(singleDayCron, Hour.fromInt(20), Hour.fromInt(21));
		CronPeriodHourPart severalHoursOnSingleDayCron = new CronPeriodHourPart(singleDayCron, Hour.fromInt(19), Hour.fromInt(23));
		CronPeriodHourPart severalHoursOnTwoDaysCron = new CronPeriodHourPart(twoDaysCron, Hour.fromInt(19), Hour.fromInt(23));
		CronPeriodHourPart severalHoursInSeveralDaysInSameMonthCron = new CronPeriodHourPart(severalDaysSameMonthCron, Hour.fromInt(19), Hour.fromInt(23));
		CronPeriodHourPart severalHoursInSeveralDaysInTwoMonthsCron = new CronPeriodHourPart(severalDaysTwoMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
		CronPeriodHourPart severalHoursInSeveralDaysInSeveralMonthsCron = new CronPeriodHourPart(severalDaysSeveralsMonthsCron, Hour.fromInt(19), Hour.fromInt(23));
		
		this.singleMinuteCron = new CronPeriodMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(55));
		this.twoMinutesCron = new CronPeriodMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(56));
		this.severalMinutesInSameHourCron = new CronPeriodMinutePart(singleHourCron, Minute.fromInt(55), Minute.fromInt(59));
		this.severalMinutesInTwoHoursCron = new CronPeriodMinutePart(twoHoursCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSameDayCron = new CronPeriodMinutePart(severalHoursOnSingleDayCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnTwoDaysCron = new CronPeriodMinutePart(severalHoursOnTwoDaysCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth = new CronPeriodMinutePart(severalHoursInSeveralDaysInSameMonthCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInTwoMonths = new CronPeriodMinutePart(severalHoursInSeveralDaysInTwoMonthsCron, Minute.fromInt(55), Minute.fromInt(55));
		this.severalMinutesInSeveralHoursOnSeveralDaysCronInSeveralMonths = new CronPeriodMinutePart(severalHoursInSeveralDaysInSeveralMonthsCron, Minute.fromInt(55), Minute.fromInt(55));
	}

	@SuppressWarnings("unused")
	@Test
	public void testFromHourAfterUntilHour() {
		try {
			new CronPeriodMinutePart(singleHourCron, Minute.fromInt(51), Minute.fromInt(50));
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
		assertEquals("* 20-24 2 3", crons.get(1).toString());
		assertEquals("* 1-22 3 3", crons.get(2).toString());
		assertEquals("0-55 23 3 3", crons.get(3).toString());
	}
	
	@Test
	public void testSeveralMinutesInSeveralHoursOnTwoDaysPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnTwoDaysCron.getPartsInternal();
		assertEquals(6, crons.size());
		assertEquals("55 19 2 3", crons.get(0).toString());
		assertEquals("56-59 19 2 3", crons.get(1).toString());
		assertEquals("* 20-24 2 3", crons.get(2).toString());
		assertEquals("* 1-22 3 3", crons.get(3).toString());
		assertEquals("0-54 23 3 3", crons.get(4).toString());
		assertEquals("55 23 3 3", crons.get(5).toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSameMonthPeriod_getParts() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth.getParts();
		assertEquals(5, crons.size());
		assertEquals("55-59 19 2 3", crons.get(0).toString());
		assertEquals("* 20-24 2 3", crons.get(1).toString());
		assertEquals("* * 3-14 3", crons.get(2).toString());
		assertEquals("* 1-22 15 3", crons.get(3).toString());
		assertEquals("0-55 23 15 3", crons.get(4).toString());
	}
	
	@Test
	public void testSeveralMinutesInSeveralHoursOnSeveralDaysInSameMonthPeriod_getPartsInternal() {
		List<CronExpression> crons = severalMinutesInSeveralHoursOnSeveralDaysCronInSameMonth.getPartsInternal();
		assertEquals(7, crons.size());
		assertEquals("55 19 2 3", crons.get(0).toString());
		assertEquals("56-59 19 2 3", crons.get(1).toString());
		assertEquals("* 20-24 2 3", crons.get(2).toString());
		assertEquals("* * 3-14 3", crons.get(3).toString());
		assertEquals("* 1-22 15 3", crons.get(4).toString());
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
