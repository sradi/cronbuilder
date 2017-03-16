package de.cron;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.day.CronDay;
import de.cron.elements.day.CronDayRange;
import de.cron.elements.month.CronSpecificMonths;

/**
 * <b>Please note:</b> The year of LocalDate instances, used by this class, is
 * ignored, as cron definitions don't support year
 * 
 * @author sradi
 *
 */
public class ComplexCronDefinition implements Iterable<CronDefinition> {

	private List<CronDefinition> crons = new ArrayList<>();
	private SimpleCronDefinition baseCronDefinition;
	private LocalDate fromDate;
	private LocalDate untilDate;

	public ComplexCronDefinition(SimpleCronDefinition baseCronDefinition, LocalDate fromDate, LocalDate untilDate) {
		this.baseCronDefinition = baseCronDefinition;
		this.fromDate = fromDate;
		this.untilDate = untilDate;
		ensureFromIsBeforeUntil();
		
		crons.addAll(getCronsForMonths());
	}

	private Collection<? extends CronDefinition> getCronsForMonths() {
		List<CronDefinition> crons = new ArrayList<>();
		int fromDay = fromDate.get(ChronoField.DAY_OF_MONTH);
		int untilDay = untilDate.get(ChronoField.DAY_OF_MONTH);
		
		int currentMonth = fromDate.getMonthValue();
		while (currentMonth <= untilDate.getMonthValue()) {
			if (isOnlyOneMonth()) {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setDayDefinition(new CronDayRange(Day.fromInt(fromDay), Day.fromInt(untilDay)))
						.setMonthDefinition(new CronSpecificMonths(fromDate.getMonth()))
						.build());
				break;
			}
			if (isFirstMonth(currentMonth)) {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setDayDefinition(new CronDayRange(
								Day.fromInt(fromDay),
								Day.fromInt(fromDate.getMonth().maxLength())))
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			} else if (isLastMonth(currentMonth)) {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setDayDefinition(new CronDayRange(
								Day.fromInt(1),
								Day.fromInt(untilDay)))
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			} else {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setDayDefinition(CronDay.EVERY_DAY)
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			}
			currentMonth++;
		}
		
		return crons;
	}

	private void ensureFromIsBeforeUntil() {
		int fromMonth = fromDate.get(ChronoField.MONTH_OF_YEAR);
		int untilMonth = untilDate.get(ChronoField.MONTH_OF_YEAR);
		Preconditions.checkArgument(fromMonth <= untilMonth);
	}

	private boolean isOnlyOneMonth() {
		int fromMonth = fromDate.get(ChronoField.MONTH_OF_YEAR);
		int untilMonth = untilDate.get(ChronoField.MONTH_OF_YEAR);
		return fromMonth == untilMonth;
	}

	private boolean isFirstMonth(int month) {
		return fromDate.getMonthValue() == month;
	}
	
	private boolean isLastMonth(int month) {
		return untilDate.getMonthValue() == month;
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
