package de.cron;

import static org.junit.Assert.*;

import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.cron.elements.CronMinute;
import de.cron.elements.CronSpecificMinutes;
import de.cron.units.Minute;

@SuppressWarnings("unused")
public class MonthLevelComplexCronDefinitionTest {
	
	private SimpleCronDefinition everyMinuteCron;
	private SimpleCronDefinition specificMinutesCron;

	@Before
	public void setupSimpleCronDefinitions() {
		this.everyMinuteCron = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(CronMinute.EVERY_MINUTE)
				.build();
		this.specificMinutesCron = new SimpleCronDefinition.SimpleCronDefinitionBuilder()
				.setMinuteDefinition(new CronSpecificMinutes(Minute.fromInt(5), Minute.fromInt(10), Minute.fromInt(15)))
				.build();
	}

	@Test
	public void testFromAfterUntil() {
		try {
			new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.FEBRUARY);
			fail("'from' is after 'until'");
		} catch (Exception e) {
		}
	}
	
	@Test
	public void testSameMonth() {
		MonthLevelComplexCronDefinition monthLevelCron = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.MARCH);
		List<SimpleCronDefinition> crons = monthLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * * 3 *", crons.get(0).toString());
	}
	
	@Test
	public void testTwoMonthPeriod() {
		MonthLevelComplexCronDefinition monthLevelCron = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.MARCH, Month.APRIL);
		List<SimpleCronDefinition> crons = monthLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * * 3-4 *", crons.get(0).toString());
	}
	
	@Test
	public void testSeveralMonthPeriod() {
		MonthLevelComplexCronDefinition monthLevelCron = new MonthLevelComplexCronDefinition(everyMinuteCron, Month.APRIL, Month.JULY);
		List<SimpleCronDefinition> crons = monthLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("* * * 4-7 *", crons.get(0).toString());
	}
	
	@Test
	public void testSeveralMonthPeriodWithSpecificMinutes() {
		MonthLevelComplexCronDefinition monthLevelCron = new MonthLevelComplexCronDefinition(specificMinutesCron, Month.APRIL, Month.JULY);
		List<SimpleCronDefinition> crons = monthLevelCron.getCrons();
		assertEquals(1, crons.size());
		assertEquals("5,10,15 * * 4-7 *", crons.get(0).toString());
	}

}
