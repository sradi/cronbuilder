package de.cron;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronMonthRange;
import de.cron.elements.CronSpecificDays;
import de.cron.elements.CronSpecificMonths;
import de.cron.units.Day;
import de.cron.units.Hour;

/**
 * <b>Please note:</b> The year of LocalDate instances, used by this class, is
 * ignored, as cron definitions don't support year
 * 
 * @author sradi
 *
 */
class ComplexCronDefinition implements Iterable<CronDefinition> {

	private List<CronDefinition> crons = new ArrayList<>();
	private SimpleCronDefinition baseCronDefinition;
	private LocalDate fromDate;
	private LocalDate untilDate;
	private Hour fromHour;
	private Hour untilHour;

	public ComplexCronDefinition(SimpleCronDefinition baseCronDefinition, LocalDate fromDate, LocalDate untilDate) {
		this.baseCronDefinition = baseCronDefinition;
		this.fromDate = fromDate;
		this.untilDate = untilDate;
		Preconditions.checkArgument(fromDate.isBefore(untilDate));

		crons.addAll(getCrons());
	}

	public ComplexCronDefinition(SimpleCronDefinition baseCronDefinition, Hour fromHour, Hour untilHour,
			LocalDate fromDate, LocalDate untilDate) {
		this.baseCronDefinition = baseCronDefinition;
		this.fromHour = fromHour;
		this.untilHour = untilHour;
		this.fromDate = fromDate;
		this.untilDate = untilDate;
		Preconditions.checkArgument(fromDate.isBefore(untilDate) || isFromHourBeforeUntilHourOnEqualDates());

		crons.addAll(getCrons());
	}

	private Collection<? extends CronDefinition> getCrons() {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		List<SimpleCronDefinition> monthLevelCrons = getMonthLevelCrons();

		if (monthLevelCrons.size() > 3) {
			throw new RuntimeException("More than three month definitions hould never happen");
		}

		Iterator<SimpleCronDefinition> monthLevelCronsIterator = monthLevelCrons.iterator();
		if (monthLevelCrons.size() == 1) {
			SimpleCronDefinition cronOfSingleMonth = monthLevelCronsIterator.next();
			crons.addAll(geDayLevelCronsOfSingleMonth(cronOfSingleMonth));

		} else if (monthLevelCrons.size() > 1) {
			SimpleCronDefinition cronOfFirstMonth = monthLevelCronsIterator.next();
			crons.addAll(getDayLevelCronsOfFirstMonth(cronOfFirstMonth));

			if (monthLevelCrons.size() > 2) {
				SimpleCronDefinition cronOfIntermediateMonth = monthLevelCronsIterator.next();
				crons.addAll(getCronsOfIntermediateMonth(cronOfIntermediateMonth));
			}

			SimpleCronDefinition cronOfLastMonth = monthLevelCronsIterator.next();
			crons.addAll(getDayLevelCronsOfLastMonth(cronOfLastMonth));
		}

		return crons;
	}

	private Collection<? extends SimpleCronDefinition> geDayLevelCronsOfSingleMonth(
			SimpleCronDefinition cronOfSingleMonth) {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		List<SimpleCronDefinition> dayLevelCrons = getDayLevelCrons(cronOfSingleMonth);

		if (dayLevelCrons.size() > 3) {
			throw new RuntimeException("More than three day definitions hould never happen per month");
		}

		Iterator<SimpleCronDefinition> dayLevelCronsIterator = dayLevelCrons.iterator();
		if (dayLevelCrons.size() == 1) {
			SimpleCronDefinition cronOfSingleDay = dayLevelCronsIterator.next();
			crons.addAll(geHourLevelCronsOfSingleDay(cronOfSingleDay));

		} else if (dayLevelCrons.size() > 1) {
			SimpleCronDefinition cronOfFirstDay = dayLevelCronsIterator.next();
			crons.addAll(getHourLevelCronsOfFirstDay(cronOfFirstDay));

			if (dayLevelCrons.size() > 2) {
				SimpleCronDefinition cronOfIntermediateDay = dayLevelCronsIterator.next();
				crons.addAll(getCronsOfIntermediateDay(cronOfIntermediateDay));
			}
			
			SimpleCronDefinition cronOfLastDay = dayLevelCronsIterator.next();
			crons.addAll(getHourLevelCronsOfLastDay(cronOfLastDay));
		}

		return crons;
	}

	private Collection<? extends SimpleCronDefinition> geHourLevelCronsOfSingleDay(
			SimpleCronDefinition cronOfSingleDay) {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfSingleDay)
				.setHourDefinition(new CronHourRange(fromHour, untilHour))
				.build());

		return crons;
	}

	private List<SimpleCronDefinition> getMonthLevelCrons() {
		List<SimpleCronDefinition> monthLevelCrons = new ArrayList<>();
		
		int currentMonth = fromDate.getMonthValue();
		while (currentMonth <= untilDate.getMonthValue()) {
			if (isOnlyOneMonth() || isFirstMonth(currentMonth) || isLastMonth(currentMonth)) {
				monthLevelCrons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setMonthDefinition(new CronSpecificMonths(Month.of(currentMonth)))
						.build());
			} else {
				monthLevelCrons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(baseCronDefinition)
						.setMonthDefinition(new CronMonthRange(Month.of(currentMonth), Month.of(untilDate.getMonthValue() - 1)))
						.build());
				currentMonth = untilDate.getMonthValue() - 1;
			}
			currentMonth++;
		}

		return monthLevelCrons;
	}

	private List<SimpleCronDefinition> getDayLevelCrons(SimpleCronDefinition cronOfSingleMonth) {
		List<SimpleCronDefinition> dayLevelCrons = new ArrayList<>();
		
		int currentDay = fromDate.getDayOfMonth();
		while (currentDay <= untilDate.getDayOfMonth()) {
			// TODO nur eine Range vom ersten bis zum letzten Tag zurückgeben, wenn es keine Hour-Ebene gibt 
			if (isOnlyOneDay() || isFirstDay(fromDate.getMonth(), currentDay) || isLastDay(untilDate.getMonth(), currentDay)) {
				dayLevelCrons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfSingleMonth)
						.setDayDefinition(new CronSpecificDays(Day.fromInt(currentDay)))
						.build());
			} else {
				dayLevelCrons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfSingleMonth)
						.setDayDefinition(new CronDayRange(Day.fromInt(currentDay), Day.fromInt(untilDate.getDayOfMonth() -1)))
						.build());
				currentDay = untilDate.getDayOfMonth() - 1;
			}
			currentDay++;
		}

		return dayLevelCrons;
	}

	private Collection<SimpleCronDefinition> getDayLevelCronsOfFirstMonth(SimpleCronDefinition cronOfFirstMonth) {
		Collection<SimpleCronDefinition> crons = new ArrayList<>();

		int currentDay = fromDate.getDayOfMonth();
		while (currentDay <= fromDate.getMonth().maxLength()) {
			if (isFirstDay(fromDate.getMonth(), currentDay)) {
				SimpleCronDefinition cronFirstDay = new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfFirstMonth)
						.setDayDefinition(new CronSpecificDays(Day.fromInt(currentDay))).build();
				crons.addAll(getHourLevelCronsOfFirstDay(cronFirstDay));
			} else {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfFirstMonth)
						.setHourDefinition(CronHour.EVERY_HOUR)
						.setDayDefinition(new CronDayRange(Day.fromInt(currentDay), Day.fromInt(fromDate.getMonth().maxLength())))
						.build());
				currentDay = fromDate.getMonth().maxLength();
			}
			currentDay++;
		}

		return crons;
	}

	private Collection<SimpleCronDefinition> getDayLevelCronsOfLastMonth(SimpleCronDefinition cronOfLastMonth) {
		List<SimpleCronDefinition> crons = new ArrayList<>();

		int currentDay = 1;
		while (currentDay <= untilDate.getDayOfMonth()) {
			if (isLastDay(untilDate.getMonth(), currentDay)) {
				SimpleCronDefinition cronLastDay = new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfLastMonth)
						.setDayDefinition(new CronSpecificDays(Day.fromInt(currentDay))).build();
				crons.addAll(getHourLevelCronsOfLastDay(cronLastDay));
			} else {
				crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfLastMonth)
						.setHourDefinition(CronHour.EVERY_HOUR)
						.setDayDefinition(new CronDayRange(Day.fromInt(1), Day.fromInt(untilDate.getDayOfMonth() - 1)))
						.build());
				currentDay = untilDate.getDayOfMonth() - 1;
			}
			currentDay++;
		}

		return crons;
	}

	private Collection<? extends SimpleCronDefinition> getHourLevelCronsOfFirstDay(SimpleCronDefinition cronOfFirstDay) {
		Collection<SimpleCronDefinition> crons = new ArrayList<>();
		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfFirstDay)
				.setHourDefinition(new CronHourRange(Hour.fromInt(fromHour.getIntValue()), Hour.fromInt(fromHour.getMaxValue())))
				.build());

		return crons;
	}

	private Collection<? extends SimpleCronDefinition> getHourLevelCronsOfLastDay(SimpleCronDefinition cronOfLastDay) {
		List<SimpleCronDefinition> crons = new ArrayList<>();
		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfLastDay)
				.setHourDefinition(new CronHourRange(Hour.fromInt(1), Hour.fromInt(untilHour.getIntValue())))
				.build());

		return crons;
	}

	private Collection<SimpleCronDefinition> getCronsOfIntermediateMonth(
			SimpleCronDefinition cronOfIntermediateMonth) {
		List<SimpleCronDefinition> crons = new ArrayList<>();

		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfIntermediateMonth)
				.setDayDefinition(CronDay.EVERY_DAY)
				.setHourDefinition(CronHour.EVERY_HOUR)
				.build());

		return crons;
	}

	private Collection<? extends SimpleCronDefinition> getCronsOfIntermediateDay(
			SimpleCronDefinition cronOfIntermediateDay) {
		List<SimpleCronDefinition> crons = new ArrayList<>();

		crons.add(new SimpleCronDefinition.SimpleCronDefinitionBuilder(cronOfIntermediateDay)
				.setHourDefinition(CronHour.EVERY_HOUR)
				.build());

		return crons;
	}

	private boolean isFromHourBeforeUntilHourOnEqualDates() {
		if (fromDate.isEqual(untilDate)) {
			return fromHour.isBefore(untilHour);
		}
		return true;
	}

	private boolean isOnlyOneMonth() {
		int fromMonth = fromDate.getMonthValue();
		int untilMonth = untilDate.getMonthValue();
		return fromMonth == untilMonth;
	}

	private boolean isOnlyOneDay() {
		int fromDay = fromDate.getDayOfMonth();
		int untilDay = untilDate.getDayOfMonth();
		return isOnlyOneMonth() && fromDay == untilDay;
	}

	private boolean isFirstMonth(int currentMonth) {
		return fromDate.getMonthValue() == currentMonth;
	}

	private boolean isFirstDay(Month month, int currentDay) {
		return isFirstMonth(month.getValue()) && fromDate.getDayOfMonth() == currentDay;
	}

	private boolean isLastMonth(int month) {
		return untilDate.getMonthValue() == month;
	}

	private boolean isLastDay(Month lastMonth, int currentDay) {
		return isLastMonth(lastMonth.getValue()) && untilDate.getDayOfMonth() == currentDay;
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
