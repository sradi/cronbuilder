package de.cron.temp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unused")
public class ComplexCronMonthPartTest {
	
	private ComplexCronMonthPart singleMonthPart;
	private ComplexCronMonthPart twoMonthPart;
	private ComplexCronMonthPart severalMonthsPart;

	@Before
	public void setUp() {
		singleMonthPart = new ComplexCronMonthPart(Month.MARCH, Month.MARCH);
		twoMonthPart = new ComplexCronMonthPart(Month.MARCH, Month.APRIL);
		severalMonthsPart = new ComplexCronMonthPart(Month.APRIL, Month.JULY);
	}
	
	@Test
	public void testFromAfterUntil() {
		try {
			new ComplexCronMonthPart(Month.MARCH, Month.FEBRUARY);
			fail("'from' is after 'until'");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testSameMonth_getParts() {
		List<ComplexCron> parts = singleMonthPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("3", parts.get(0).toString());
	}
	
	@Test
	public void testSameMonth_getFirstPart() {
		ComplexCron firstPart = singleMonthPart.getFirstPart();
		assertEquals("3", firstPart.toString());
	}
	
	@Test
	public void testSameMonth_getIntermediateParts() {
		try {
			singleMonthPart.getIntermediateParts();
			fail("no intermediate parts expected");
		} catch (Exception e) {}
	}
	
	@Test
	public void testSameMonth_getLastPart() {
		ComplexCron lastPart = singleMonthPart.getLastPart();
		assertEquals("3", lastPart.toString());
	}
	
	// ********************************
	@Test
	public void testTwoMonthPeriod_getParts() {
		List<ComplexCron> parts = twoMonthPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("3-4", parts.get(0).toString());
	}
	
	@Test
	public void testTwoMonthPeriod_getFirstPart() {
		ComplexCron firstPart = twoMonthPart.getFirstPart();
		assertEquals("3", firstPart.toString());
	}
	
	@Test
	public void testTwoMonthPeriod_getIntermediateParts() {
		try {
			twoMonthPart.getIntermediateParts();
			fail("no intermediate parts expected");
		} catch (Exception e) {}
	}
	
	@Test
	public void testTwoMonthPeriod_getLastPart() {
		ComplexCron lastPart = twoMonthPart.getLastPart();
		assertEquals("4", lastPart.toString());
	}
	
	// ********************************
	@Test
	public void testSeveralMonthsPeriod_getParts() {
		List<ComplexCron> parts = severalMonthsPart.getParts();
		assertEquals(1, parts.size());
		assertEquals("4-7", parts.get(0).toString());
	}
	
	@Test
	public void testSeveralMonthsPeriod_getFirstPart() {
		ComplexCron firstPart = severalMonthsPart.getFirstPart();
		assertEquals("4", firstPart.toString());
	}
	
	@Test
	public void testSeveralMonthsPeriod_getIntermediateParts() {
		List<ComplexCron> intermediateParts = severalMonthsPart.getIntermediateParts();
		assertEquals(1, intermediateParts.size());
		assertEquals("5-6", intermediateParts.get(0).toString());
	}
	
	@Test
	public void testSeveralMonthsPeriod_getLastPart() {
		ComplexCron lastPart = severalMonthsPart.getLastPart();
		assertEquals("7", lastPart.toString());
	}

}
