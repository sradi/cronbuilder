package de.cron.temp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cron.units.Day;

public class ComplexCronDayPartTest {
	
	private ComplexCronMonthPart singleMonthCron;

	private ComplexCronDayPart singleDayCron;
	private ComplexCronDayPart twoDaysCron;
	private ComplexCronDayPart severalDaysInSingleMonthCron;
	private ComplexCronDayPart severalDaysInTwoMonthsCron;
	private ComplexCronDayPart severalDaysInSeveralMonthsCron;

	@Before
	public void setupSimpleCronDefinitions() {
		singleMonthCron = new ComplexCronMonthPart(Month.MARCH, Month.MARCH);
		ComplexCronMonthPart twoMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.APRIL);
		ComplexCronMonthPart severalMonthsCron = new ComplexCronMonthPart(Month.MARCH, Month.AUGUST);

		singleDayCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(2), Day.fromInt(2));
		twoDaysCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(5), Day.fromInt(6));
		severalDaysInSingleMonthCron = new ComplexCronDayPart(singleMonthCron, Day.fromInt(3), Day.fromInt(7));
		severalDaysInTwoMonthsCron = new ComplexCronDayPart(twoMonthsCron, Day.fromInt(3), Day.fromInt(7));
		severalDaysInSeveralMonthsCron = new ComplexCronDayPart(severalMonthsCron, Day.fromInt(3), Day.fromInt(7));
	}

	@SuppressWarnings("unused")
	@Test
	public void testFromDayAfterUntilDay() {
		try {
			new ComplexCronDayPart(singleMonthCron, Day.fromInt(5), Day.fromInt(4));
			fail("'from' is after 'until' in the same month");
		} catch (Exception e) {
		}
	}

	//********************************************************
	@Test
	public void testSameDay_getParts() {
		List<ComplexCron> crons = singleDayCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("2 3", crons.get(0).toString());
	}

	@Test
	public void testSameDay_getFirstPart() {
		ComplexCron crons = singleDayCron.getFirstPart();
		assertEquals("2 3", crons.toString());
	}
	
	@Test
	public void testSameDay_getIntermediateParts() {
		try {
			singleDayCron.getIntermediateParts();
			fail("no intermediate parts expected");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testSameDay_getLastPart() {
		ComplexCron crons = singleDayCron.getLastPart();
		assertEquals("2 3", crons.toString());
	}

	//********************************************************
	@Test
	public void testTwoDayPeriod_getParts() {
		List<ComplexCron> crons = twoDaysCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("5-6 3", crons.get(0).toString());
	}

	@Test
	public void testTwoDayPeriod_getFirstPart() {
		ComplexCron crons = twoDaysCron.getFirstPart();
		assertEquals("5 3", crons.toString());
	}
	
	@Test
	public void testTwoDayPeriod_getIntermediateParts() {
		try {
			twoDaysCron.getIntermediateParts();
			fail("no intermediate parts expected");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testTwoDayPeriod_getLastPart() {
		ComplexCron crons = twoDaysCron.getLastPart();
		assertEquals("6 3", crons.toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralDaysInSingleMonthPeriod_getParts() {
		List<ComplexCron> crons = severalDaysInSingleMonthCron.getParts();
		assertEquals(1, crons.size());
		assertEquals("3-7 3", crons.get(0).toString());
	}
	
	@Test
	public void testSeveralDaysInSingleMonthPeriod_getFirstPart() {
		ComplexCron crons = severalDaysInSingleMonthCron.getFirstPart();
		assertEquals("3 3", crons.toString());
	}
	
	@Test
	public void testSeveralDaysInSingleMonthPeriod_getIntermediateParts() {
		List<ComplexCron> crons = severalDaysInSingleMonthCron.getIntermediateParts();
		assertEquals(3, crons.size());
		assertEquals("3 3", crons.get(0).toString());
		assertEquals("4-6 3", crons.get(1).toString());
		assertEquals("7 3", crons.get(2).toString());
	}
	
	@Test
	public void testSeveralDaysInSingleMonthPeriod_getLastPart() {
		ComplexCron crons = severalDaysInSingleMonthCron.getLastPart();
		assertEquals("7 3", crons.toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralDaysInTwoMonthsPeriod_getParts() {
		List<ComplexCron> crons = severalDaysInTwoMonthsCron.getParts();
		assertEquals(2, crons.size());
		assertEquals("3-31 3", crons.get(0).toString());
		assertEquals("1-7 4", crons.get(1).toString());
	}
	
	@Test
	public void testSeveralDaysInTwoMonthsPeriod_getFirstPart() {
		ComplexCron crons = severalDaysInTwoMonthsCron.getFirstPart();
		assertEquals("3 3", crons.toString());
	}
	
	@Test
	public void testSeveralDaysInTwoMonthsPeriod_getIntermediateParts() {
		List<ComplexCron> crons = severalDaysInTwoMonthsCron.getIntermediateParts();
		assertEquals(4, crons.size());
		assertEquals("3 3", crons.get(0).toString());
		assertEquals("4-31 3", crons.get(1).toString());
		assertEquals("1-6 4", crons.get(2).toString());
		assertEquals("7 4", crons.get(3).toString());
	}
	
	@Test
	public void testSeveralDaysInTwoMonthsPeriod_getLastPart() {
		ComplexCron crons = severalDaysInTwoMonthsCron.getLastPart();
		assertEquals("7 4", crons.toString());
	}
	
	//********************************************************
	@Test
	public void testSeveralDaysInSeveralMonthsPeriod_getParts() {
		List<ComplexCron> crons = severalDaysInSeveralMonthsCron.getParts();
		assertEquals(3, crons.size());
		assertEquals("3-31 3", crons.get(0).toString());
		assertEquals("* 4-7", crons.get(1).toString());
		assertEquals("1-7 8", crons.get(2).toString());
	}
	
	@Test
	public void testSeveralDaysInSeveralMonthsPeriod_getFirstPart() {
		ComplexCron crons = severalDaysInSeveralMonthsCron.getFirstPart();
		assertEquals("3 3", crons.toString());
	}
	
	@Test
	public void testSeveralDaysInSeveralMonthsPeriod_getIntermediateParts() {
		List<ComplexCron> crons = severalDaysInSeveralMonthsCron.getIntermediateParts();
		assertEquals(5, crons.size());
		assertEquals("3 3", crons.get(0).toString());
		assertEquals("4-31 3", crons.get(1).toString());
		assertEquals("* 4-7", crons.get(2).toString());
		assertEquals("1-6 8", crons.get(3).toString());
		assertEquals("7 8", crons.get(4).toString());
	}
	
	@Test
	public void testSeveralDaysInSeveralMonthsPeriod_getLastPart() {
		ComplexCron crons = severalDaysInSeveralMonthsCron.getLastPart();
		assertEquals("7 8", crons.toString());
	}

}
