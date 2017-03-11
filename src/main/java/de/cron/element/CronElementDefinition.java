package de.cron.element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.cron.string.DateTimeStringRepresentation;

public class CronElementDefinition<T extends DateTimeStringRepresentation> implements CronElement {
	
	private List<T> dateTimeElements = new ArrayList<>();
	private T from = null;
	private T until = null;
	
	public static <T> CronElementDefinition<T> exactlyOne(T dateTimeElement) {
		CronElementDefinition<T> cronDefinition = new CronElementDefinition<T>();
		cronDefinition.dateTimeElements.add(dateTimeElement);
		return cronDefinition;
	}
	
//	public static <T> CronElementDefinition<T> exactlThese(T... dateTimeElements) {
//		CronElementDefinition<T> cronDefinition = new CronElementDefinition<T>();
//		cronDefinition.dateTimeElements.addAll(Arrays.asList(dateTimeElements));
//		return cronDefinition;
//	}
//	
//	public static <T> CronElementDefinition<T> fromUntil(T from, T until) {
//		CronElementDefinition<T> cronDefinition = new CronElementDefinition<T>();
//		cronDefinition.from = from;
//		cronDefinition.until = until;
//		return cronDefinition;
//	}

	@Override
	public String getStringRepresentation() {
		if (isRangeSpecified()) {
			return from.getValue() + "-" + until.getValue();
		} else {
			StringBuilder sb = new StringBuilder();
			for (T t : dateTimeElements) {
				sb.append(t.getValue()).append(",");
			}
			return sb.substring(0, sb.length()-1);
		}
	}
	
	public static CronElement every() {
		return new CronElementEvery();
	}
	
	private static class CronElementEvery implements CronElement {

		@Override
		public String getStringRepresentation() {
			return "*";
		}
		
	}

	private boolean isRangeSpecified() {
		return ((from != null) && (until != null) && (dateTimeElements.isEmpty()));
	}
	
//	private static class AnyMonthCronDefition extends MonthCronDefinition {
//	}
	
}
