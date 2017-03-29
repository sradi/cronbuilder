package de.cron;

import java.time.Month;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayOfWeek;
import de.cron.elements.CronHour;
import de.cron.elements.CronMinute;
import de.cron.elements.period.CronPeriodDayPart;
import de.cron.elements.period.CronPeriodHourPart;
import de.cron.elements.period.CronPeriodMinutePart;
import de.cron.elements.period.CronPeriodMonthPart;
import de.cron.elements.period.CronPeriodPart;
import de.cron.units.Day;
import de.cron.units.Hour;
import de.cron.units.Minute;

public class CronPeriodExpression implements Iterable<CronExpression> {
	
	private List<CronExpression> crons;

	private CronPeriodExpression(CronMinute minuteElement, CronHour hourElement, CronDay dayElement,
			CronPeriodPart cronPeriodMonth, CronDayOfWeek dayOfWeekElement) {
		List<CronExpression> monthPeriodExpression = cronPeriodMonth.getParts();
		monthPeriodExpression.forEach(expr -> crons.add(expr.prepend(dayElement).prepend(hourElement).prepend(minuteElement).append(dayOfWeekElement)));
	}

	private CronPeriodExpression(CronMinute minuteElement, CronHour hourElement, CronPeriodPart cronPeriodDayPart, CronDayOfWeek dayOfWeekElement) {
		List<CronExpression> dayPeriodExpression = cronPeriodDayPart.getParts();
		dayPeriodExpression.forEach(expr -> crons.add(expr.prepend(hourElement).prepend(minuteElement).append(dayOfWeekElement)));
	}

	private CronPeriodExpression(CronMinute minuteElement, CronPeriodHourPart cronPeriodHourPart,
			CronDayOfWeek dayOfWeekElement) {
		List<CronExpression> hourPeriodExpression = cronPeriodHourPart.getParts();
		hourPeriodExpression.forEach(expr -> crons.add(expr.prepend(minuteElement).append(dayOfWeekElement)));
	}

	private CronPeriodExpression(CronPeriodMinutePart cronPeriodMinutePart, CronDayOfWeek dayOfWeekElement) {
		List<CronExpression> minutePeriodExpression = cronPeriodMinutePart.getParts();
		minutePeriodExpression.forEach(expr -> crons.add(expr.append(dayOfWeekElement)));
	}

	@Override
	public Iterator<CronExpression> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int size() {
		return crons.size();
	}
	
	public CronExpression get(int index) {
		return crons.get(index);
	}

	public static class CronPeriodExpressionBuilder {

		private CronDayOfWeek dayOfWeekElement;
		private CronMinute minuteElement;
		private CronHour hourElement;
		private CronDay dayElement;
		
		private Minute fromMinute;
		private Minute untilMinute;
		private Hour fromHour;
		private Hour untilHour;
		private Day fromDay;
		private Day untilDay;
		private Month fromMonth;
		private Month untilMonth;

		public CronPeriodExpressionBuilder setDayOfWeekDefinition(CronDayOfWeek dayOfWeekElement) {
			Preconditions.checkArgument(dayOfWeekElement != null);
			this.dayOfWeekElement = dayOfWeekElement;
			return this;
		}

		public CronPeriodExpressionBuilder setMinuteDefinition(CronMinute minuteElement) {
			Preconditions.checkArgument(minuteElement != null);
			this.minuteElement = minuteElement;
			return this;
		}
		
		public CronPeriodExpressionBuilder setHourDefinition(CronHour hourElement) {
			Preconditions.checkArgument(hourElement != null);
			this.hourElement = hourElement;
			return this;
		}
		
		public CronPeriodExpressionBuilder setDayDefinition(CronDay dayElement) {
			Preconditions.checkArgument(dayElement != null);
			this.dayElement = dayElement;
			return this;
		}

		public CronPeriodExpressionBuilder setFromMinute(Minute fromMinute) {
			Preconditions.checkArgument(fromMinute != null);
			this.fromMinute = fromMinute;
			return this;
		}

		public CronPeriodExpressionBuilder setUntilMinute(Minute untilMinute) {
			Preconditions.checkArgument(untilMinute != null);
			this.untilMinute = untilMinute;
			return this;
		}

		public CronPeriodExpressionBuilder setFromHour(Hour fromHour) {
			Preconditions.checkArgument(fromHour != null);
			this.fromHour = fromHour;
			return this;
		}

		public CronPeriodExpressionBuilder setUntilHour(Hour untilHour) {
			Preconditions.checkArgument(untilHour != null);
			this.untilHour = untilHour;
			return this;
		}

		public CronPeriodExpressionBuilder setFromDay(Day fromDay) {
			Preconditions.checkArgument(fromDay != null);
			this.fromDay = fromDay;
			return this;
		}

		public CronPeriodExpressionBuilder setUntilDay(Day untilDay) {
			Preconditions.checkArgument(untilDay != null);
			this.untilDay = untilDay;
			return this;
		}

		public CronPeriodExpressionBuilder setFromMonth(Month fromMonth) {
			Preconditions.checkArgument(fromMonth != null);
			this.fromMonth = fromMonth;
			return this;
		}

		public CronPeriodExpressionBuilder setUntilMonth(Month untilMonth) {
			Preconditions.checkArgument(untilMonth != null);
			this.untilMonth = untilMonth;
			return this;
		}
		
		public CronPeriodExpression build() {
			Preconditions.checkState(isMonthLevelPeriod() || isDayLevelPeriod() || isHourLevelPeriod() || isMinuteLevelPeriod());
			if (isMonthLevelPeriod()) {
				return new CronPeriodExpression(minuteElement, hourElement, dayElement, new CronPeriodMonthPart(fromMonth, untilMonth), dayOfWeekElement);
			} else if (isDayLevelPeriod()) {
				return new CronPeriodExpression(
						minuteElement,
						hourElement,
						new CronPeriodDayPart(
								new CronPeriodMonthPart(fromMonth, untilMonth),
								fromDay,
								untilDay),
						dayOfWeekElement);
			} else if (isHourLevelPeriod()) {
				return new CronPeriodExpression(
						minuteElement,
						new CronPeriodHourPart(
								new CronPeriodDayPart(
										new CronPeriodMonthPart(fromMonth, untilMonth),
										fromDay,
										untilDay),
								fromHour,
								untilHour),
						dayOfWeekElement);
			} else if (isMinuteLevelPeriod()) {
				return new CronPeriodExpression(
						new CronPeriodMinutePart(
								new CronPeriodHourPart(
										new CronPeriodDayPart(
												new CronPeriodMonthPart(fromMonth, untilMonth),
												fromDay,
												untilDay),
										fromHour,
										untilHour),
								fromMinute,
								untilMinute),
						dayOfWeekElement);
			} else {
				throw new RuntimeException("This should not happen. CheckState() failed.");
			}
		}

		private boolean isMonthLevelPeriod() {
			return minuteElement != null
					&& hourElement != null
					&& dayElement != null
					&& fromMinute == null
					&& untilMinute == null
					&& fromHour == null
					&& untilHour == null
					&& fromDay == null
					&& untilDay == null
					&& fromMonth != null
					&& untilMonth != null;
		}

		private boolean isDayLevelPeriod() {
			return minuteElement != null
					&& hourElement != null
					&& dayElement == null
					&& fromMinute == null
					&& untilMinute == null
					&& fromHour == null
					&& untilHour == null
					&& fromDay != null
					&& untilDay != null
					&& fromMonth != null
					&& untilMonth != null;
		}

		private boolean isHourLevelPeriod() {
			return minuteElement != null
					&& hourElement == null
					&& dayElement == null
					&& fromMinute == null
					&& untilMinute == null
					&& fromHour != null
					&& untilHour != null
					&& fromDay != null
					&& untilDay != null
					&& fromMonth != null
					&& untilMonth != null;
		}

		private boolean isMinuteLevelPeriod() {
			return minuteElement == null
					&& hourElement == null
					&& dayElement == null
					&& fromMinute != null
					&& untilMinute != null
					&& fromHour != null
					&& untilHour != null
					&& fromDay != null
					&& untilDay != null
					&& fromMonth != null
					&& untilMonth != null;
		}

	}

}
