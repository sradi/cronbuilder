package de.cron;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronElement;
import de.cron.elements.CronElementEvery;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.elements.CronMonth;
import de.cron.elements.CronSpecificDaysOfWeek;

public class CronExpression {
	
	private static final String CRON_ELEMENT_SEPARATOR = " ";
	
	private List<CronElement> parts = new ArrayList<>();
	
	private CronExpression() {
	}
	
	public CronExpression(CronElement firstPart) {
		this.parts.add(firstPart);
	}
	
	private CronExpression(CronMinute minuteDefinition, CronHour hourDefinition, CronDay dayDefinition,
			CronMonth monthDefinition, CronDayOfWeek dayOfWeekDefinition) {
		parts.add(minuteDefinition);
		parts.add(hourDefinition);
		parts.add(dayDefinition);
		parts.add(monthDefinition);
		parts.add(dayOfWeekDefinition);
	}

	/**
	 * Always return an immutable copy of the current object
	 */
	public CronExpression prepend(CronElement newPart) {
		Preconditions.checkArgument(newPart != null);
		
		CronExpression complexCron = cloneCurrentCrons();
		complexCron.parts.add(0, newPart);
		return complexCron;
	}
	
	public CronExpression append(CronElement newPart) {
		Preconditions.checkArgument(newPart != null);
		
		CronExpression complexCron = cloneCurrentCrons();
		complexCron.parts.add(newPart);
		return complexCron;
	}
	
	private CronExpression cloneCurrentCrons() {
		CronExpression complexCron = new CronExpression();
		for (CronElement p : parts) {
			complexCron.parts.add(p);
		}
		return complexCron;
	}
	
	public CronMinute getMinuteDefinition() {
		Preconditions.checkState(hasValidElementCount());
		CronElement minuteElement = parts.get(0);
		Preconditions.checkState(minuteElement instanceof CronMinute);
		return (CronMinute) minuteElement;
	}

	public CronHour getHourDefinition() {
		Preconditions.checkState(hasValidElementCount());
		CronElement hourElement = parts.get(1);
		Preconditions.checkState(hourElement instanceof CronHour);
		return (CronHour) hourElement;
	}
	
	public CronDay getDayDefinition() {
		Preconditions.checkState(hasValidElementCount());
		CronElement dayElement = parts.get(2);
		Preconditions.checkState(dayElement instanceof CronDay);
		return (CronDay) dayElement;
	}
	
	public CronMonth getMonthDefinition() {
		Preconditions.checkState(hasValidElementCount());
		CronElement monthElement = parts.get(3);
		Preconditions.checkState(monthElement instanceof CronMonth);
		return (CronMonth) monthElement;
	}
	
	public CronDayOfWeek getDayOfWeekDefinition() {
		Preconditions.checkState(hasValidElementCount());
		CronElement dayOfWeekElement = parts.get(4);
		Preconditions.checkState(dayOfWeekElement instanceof CronDayOfWeek);
		return (CronSpecificDaysOfWeek) dayOfWeekElement;
	}

	@Override
	public String toString() {
		StringBuilder cronStringBuilder = new StringBuilder();
		parts.forEach(p ->
			cronStringBuilder
				.append(p)
				.append(CRON_ELEMENT_SEPARATOR)
		);
		return cronStringBuilder.substring(0, cronStringBuilder.length()-1);
	}
	
	private boolean hasValidElementCount() {
		return parts.size() == 5;
	}
	
	public static class CronExpressionBuilder {
		
		private CronMinute minuteDefinition;
		private CronHour hourDefinition;
		private CronDay dayDefinition;
		private CronMonth monthDefinition;
		private CronDayOfWeek dayOfWeekDefinition;
		
		public CronExpressionBuilder setMinuteDefinition(CronMinute minuteDefinition) {
//			Preconditions.checkArgument(minuteDefinition != null);
			this.minuteDefinition = minuteDefinition;
			return this;
		}

		public CronExpressionBuilder setHourDefinition(CronHour hourDefinition) {
//			Preconditions.checkArgument(hourDefinition != null);
			this.hourDefinition = hourDefinition;
			return this;
		}

		public CronExpressionBuilder setDayDefinition(CronDay dayDefinition) {
//			Preconditions.checkArgument(dayDefinition != null);
			this.dayDefinition = dayDefinition;
			return this;
		}

		public CronExpressionBuilder setMonthDefinition(CronMonth monthDefinition) {
//			Preconditions.checkArgument(monthDefinition != null);
			this.monthDefinition = monthDefinition;
			return this;
		}

		public CronExpressionBuilder setDayOfWeekDefinition(CronDayOfWeek dayOfWeekDefinition) {
//			Preconditions.checkArgument(dayOfWeekDefinition != null);
			this.dayOfWeekDefinition = dayOfWeekDefinition;
			return this;
		}
		
		public CronExpression build() {
			Preconditions.checkArgument(minuteDefinition != null);
			
			if (this.hourDefinition == null) {
				this.hourDefinition = CronElementEvery.INSTANCE;
			}
			if (this.dayDefinition == null) {
				this.dayDefinition = CronElementEvery.INSTANCE;
			}
			if (this.monthDefinition == null) {
				this.monthDefinition = CronElementEvery.INSTANCE;
			}
			if (this.dayOfWeekDefinition == null) {
				this.dayOfWeekDefinition = CronElementEvery.INSTANCE;
			}
			return new CronExpression(minuteDefinition, hourDefinition, dayDefinition, monthDefinition, dayOfWeekDefinition);
		}
	}
}
