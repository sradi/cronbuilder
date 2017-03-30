package de.cron.elements.period;

public abstract class BaseCronPeriodPart implements CronPeriodPart {
	
	public abstract CronPeriodPart getNextLevelPart();
	
}
