package de.cron.temp;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronSpecificDays;
import de.cron.units.Day;

public class ComplexCronDayPart implements ComplexCronPart {
	
	private ComplexCronMonthPart monthPart;
	private Day from;
	private Day until;

	public ComplexCronDayPart(ComplexCronMonthPart monthPart, Day from, Day until) {
		Preconditions.checkArgument(until.getIntValue() - from.getIntValue() >= 0 || !monthPart.isFromEqualToUntil()); // wenn gleicher Monat, mind. gleicher Tag 
		this.monthPart = monthPart;
		this.from = from;
		this.until = until;
	}

	@Override
	public List<ComplexCron> getParts() {
		List<ComplexCron> parts = new ArrayList<>();
		if (isFromEqualToUntil()) {
			List<ComplexCron> monthParts = monthPart.getParts(); // hier kann nur ein MonthPart zurueckkommen
			monthParts.forEach(p -> parts.add(p.prepend(new CronSpecificDays(from))));
		} else {
			if (monthPart.isFromEqualToUntil()) {
				// zwei oder mehr Tage im gleichen Monat...
				List<ComplexCron> monthParts = monthPart.getParts(); // hier kann nur ein MonthPart zurueckkommen
				monthParts.forEach(p -> parts.add(p.prepend(new CronDayRange(from, until))));
			} else {
				// zwei oder mehr Tage über zwei oder mehrere Monate
				parts.add(monthPart.getFirstPart().prepend(new CronDayRange(from, Day.fromInt(from.getMaxValue()))));
				if (monthPart.hasIntermediateParts()) {
					List<ComplexCron> intermediateParts = monthPart.getIntermediateParts();
					intermediateParts.forEach(p -> parts.add(p.prepend(CronDay.EVERY_DAY)));
				}
				parts.add(monthPart.getLastPart().prepend(new CronDayRange(Day.fromInt(until.getMinValue()), until)));
			}
		}
		return parts;
	}
	
	@Override
	public ComplexCron getFirstPart() {
		return monthPart.getFirstPart().prepend(new CronSpecificDays(from));
	}
	
	@Override
	public List<ComplexCron> getIntermediateParts() {
		Preconditions.checkState(hasIntermediateParts());
		List<ComplexCron> parts = new ArrayList<>();
		parts.add(getFirstPart());
		// Tage im gleichen Monat
		if (monthPart.isFromEqualToUntil()) {
			ComplexCron singleMonthPart = monthPart.getFirstPart();
			parts.add(singleMonthPart.prepend(new CronDayRange(Day.fromInt(from.getIntValue() + 1), Day.fromInt(until.getIntValue() - 1))));
		} else {
			parts.add(monthPart.getFirstPart().prepend(new CronDayRange(Day.fromInt(from.getIntValue() + 1), Day.fromInt(from.getMaxValue()))));
			if (monthPart.hasIntermediateParts()) {
				monthPart.getIntermediateParts().forEach(p ->
					parts.add(p.prepend(CronDay.EVERY_DAY))
				);
			}
			parts.add(monthPart.getLastPart().prepend(new CronDayRange(Day.fromInt(1), Day.fromInt(until.getIntValue() - 1))));
		}
		parts.add(getLastPart());
		
		return parts;
	}
	
	@Override
	public ComplexCron getLastPart() {
		return monthPart.getLastPart().prepend(new CronSpecificDays(until));
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until) && monthPart.isFromEqualToUntil();
	}

	@Override
	public boolean hasIntermediateParts() {
		return until.getIntValue() - from.getIntValue() > 1 || monthPart.hasIntermediateParts();
	}
	
}
