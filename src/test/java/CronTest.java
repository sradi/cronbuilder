import org.junit.Assert;
import org.junit.Test;

import de.cron.Cron;
import de.cron.CronDefinition;

public class CronTest {

	@Test
	public void testEveryMinuteEveryHourEveryDayEveryMonthEveryDayOfWeek() {
		CronDefinition cron = Cron.cron().everyMinute().everyHour().everyDay().everyMonth().everyDayOfWeek();
		Assert.assertEquals("* * * * *", cron.toString());
	}

}
