package de.cron.elements.period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.cron.CronExpression;
import de.cron.elements.period.CronPeriodMonthPart;

@SuppressWarnings("unused")
public class ComplexCronMonthPartTest {
	
	private CronPeriodMonthPart singleMonthPart;
	private CronPeriodMonthPart twoMonthPart;
	private CronPeriodMonthPart severalMonthsPart;

	@Before
	public void setUp() {
		singleMonthPart = new CronPeriodMonthPart(Month.MARCH, Month.MARCH);
		twoMonthPart = new CronPeriodMonthPart(Month.MARCH, Month.APRIL);
		severalMonthsPart = new CronPeriodMonthPart(Month.APRIL, Month.JULY);
	}
	
	@Test
	public void testFromAfterUntil() {
		try {
			new CronPeriodMonthPart(Month.MARCH, Month.FEBRUARY);
			fail("'from' is after 'until'");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testSameMonth_getParts() {
		List<CronExpression> parts = singleMonthPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("3", parts.get(0).toString());
	}
	
	@Test
	public void testSameMonth_getPartsInternal() {
		List<CronExpression> parts = singleMonthPart.getPartsInternal();
		assertEquals(1, parts.size());
		assertEquals("3", parts.get(0).toString());
	}
	
	// ********************************
	@Test
	public void testTwoMonthPeriod_getParts() {
		List<CronExpression> parts = twoMonthPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("3-4", parts.get(0).toString());
	}
	
	@Test
	public void testTwoMonthPeriod_getPartsInternal() {
		List<CronExpression> intermediateParts = twoMonthPart.getPartsInternal();
		assertEquals(2, intermediateParts.size());
		assertEquals("3", intermediateParts.get(0).toString());
		assertEquals("4", intermediateParts.get(1).toString());
	}
	
	// ********************************
	@Test
	public void testSeveralMonthsPeriod_getParts() {
		List<CronExpression> parts = severalMonthsPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("4-7", parts.get(0).toString());
	}
	
	@Test
	public void testSeveralMonthsPeriod_getPartsInternal() {
		List<CronExpression> intermediateParts = severalMonthsPart.getPartsInternal();
		assertEquals(3, intermediateParts.size());
		assertEquals("4", intermediateParts.get(0).toString());
		assertEquals("5-6", intermediateParts.get(1).toString());
		assertEquals("7", intermediateParts.get(2).toString());
	}
	
}
