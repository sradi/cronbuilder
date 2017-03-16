package de.cron;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.string.day.CronDay;
import de.cron.string.day.CronDayRange;
import de.cron.string.day.CronSpecificDays;
import de.cron.string.month.CronSpecificMonths;

/**
 * <b>Please note:</b> The year of LocalDate instances, used by this class, is
 * ignored, as cron definitions don't support year
 * 
 * @author sradi
 *
 */
public class ComplexCronDefinition implements Iterable<CronDefinition> {

	private List<CronDefinition> crons = new ArrayList<>();

	public ComplexCronDefinition(SimpleCronDefinition baseCronDefinition, LocalDate fromDate, LocalDate untilDate) {
		int fromMonth = fromDate.get(ChronoField.MONTH_OF_YEAR);
		int untilMonth = untilDate.get(ChronoField.MONTH_OF_YEAR);
		Preconditions.checkArgument(fromMonth <= untilMonth);
		
		if (fromMonth == untilMonth) {
			int fromDay = fromDate.get(ChronoField.DAY_OF_MONTH);
			int untilDay = untilDate.get(ChronoField.DAY_OF_MONTH);
			// TODO Diese Precondigion wird auch im Day-Objekt geprueft. Hier entfernen? Best Practice?
			Preconditions
					.checkArgument(fromDay < untilDay, "If ");
			crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder()
					.setMinuteDefinition(baseCronDefinition.getMinuteDefinition())
					.setHourDefinition(baseCronDefinition.getHourDefinition())
					.setDayDefinition(new CronDayRange(Day.fromInt(fromDay), Day.fromInt(untilDay)))
					.setMonthDefinition(new CronSpecificMonths(fromDate.getMonth()))
					.build());
		} else {
			crons.addAll(getCronsForMonths(baseCronDefinition, fromDate, untilDate));
		}
	}

	private Collection<? extends CronDefinition> getCronsForMonths(SimpleCronDefinition baseCronDefinition,
			LocalDate fromDate, LocalDate untilDate) {
		List<CronDefinition> crons = new ArrayList<>();
		
		int currentMonth = fromDate.getMonthValue();
		while (currentMonth <= untilDate.getMonthValue()) {
			if (isFirstMonth(fromDate, currentMonth)) {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder()
						.setMinuteDefinition(baseCronDefinition.getMinuteDefinition())
						.setHourDefinition(baseCronDefinition.getHourDefinition())
						.setDayDefinition(new CronDayRange(
								Day.fromInt(fromDate.get(ChronoField.DAY_OF_MONTH)),
								Day.fromInt(fromDate.getMonth().maxLength())))
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			} else if (isLastMonth(untilDate, currentMonth)) {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder()
						.setMinuteDefinition(baseCronDefinition.getMinuteDefinition())
						.setHourDefinition(baseCronDefinition.getHourDefinition())
						.setDayDefinition(new CronDayRange(
								Day.fromInt(1),
								Day.fromInt(untilDate.get(ChronoField.DAY_OF_MONTH))))
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			} else {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder()
						.setMinuteDefinition(baseCronDefinition.getMinuteDefinition())
						.setHourDefinition(baseCronDefinition.getHourDefinition())
						.setDayDefinition(CronDay.EVERY_DAY)
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			}
			currentMonth++;
		}
		
		return crons;
	}

	private boolean isFirstMonth(LocalDate fromDate, int currentMonth) {
		return fromDate.getMonthValue() == currentMonth;
	}
	
	private boolean isLastMonth(LocalDate untilDate, int currentMonth) {
		return untilDate.getMonthValue() == currentMonth;
	}

	public CronDefinition get(int i) {
		// TODO return copy of CronDefinition, in order to make this class
		// immutable
		return crons.get(i);
	}

	public int size() {
		return crons.size();
	}

	@Override
	public Iterator<CronDefinition> iterator() {
		return crons.iterator();
	}

}
