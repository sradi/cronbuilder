package de.sradi.cronbuilder;

import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.Test;

public class CronPeriodTest {

	@Test
	public void test1() {
		LocalDate fromDate = LocalDate.of(2018, 7, 25);
		LocalDate untilDate = LocalDate.of(2018, 8, 1);
		CronPeriodExpression crons =
			Cron.cron()
				.aRandomMinute()
				.inTheseHours(7)
				.from(fromDate)
				.until(untilDate)
				.fromDayOfWeek(DayOfWeek.MONDAY)
				.untilDayOfWeek(DayOfWeek.FRIDAY)
				.get();

		assertEquals(2, crons.size());
		assertEquals("H 7 25-31 7 1-5", crons.get(0).toString());
		assertEquals("H 7 1 8 1-5", crons.get(1).toString());
	}

}
