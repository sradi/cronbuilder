package de.cron.temp;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronSpecificHours;
import de.cron.units.Hour;

public class ComplexCronHourPart implements ComplexCronPart {
	
	private ComplexCronDayPart dayPart;
	private Hour from;
	private Hour until;

	public ComplexCronHourPart(ComplexCronDayPart dayPart, Hour from, Hour until) {
		Preconditions.checkArgument(until.getIntValue() - from.getIntValue() >= 0 || !dayPart.isFromEqualToUntil()); // wenn gleicher Monat, mind. gleicher Tag 
		this.dayPart = dayPart;
		this.from = from;
		this.until = until;
	}

	@Override
	public List<ComplexCron> getParts() {
		List<ComplexCron> parts = new ArrayList<>();
		if (isFromEqualToUntil()) {
			List<ComplexCron> dayParts = dayPart.getParts(); // hier kann nur ein DayPart zurueckkommen
			dayParts.forEach(p -> parts.add(p.prepend(new CronSpecificHours(from))));
		} else {
			if (dayPart.isFromEqualToUntil()) {
				// zwei oder mehr Stunden am gleichen Tag...
				List<ComplexCron> dayParts = dayPart.getParts(); // hier kann nur ein DayPart zurueckkommen
				dayParts.forEach(p -> parts.add(p.prepend(new CronHourRange(from, until))));
			} else {
				// zwei oder mehr Stunden über zwei oder mehrere Tage
				List<ComplexCron> intermediateParts = dayPart.getPartsInternal();
				for (int i = 0; i < intermediateParts.size(); i++) {
					ComplexCron part = intermediateParts.get(i);
					if (i==0) {
						parts.add(part.prepend(new CronHourRange(from, Hour.fromInt(from.getMaxValue()))));
					} else if (i==(intermediateParts.size()-1)) {
						parts.add(part.prepend(new CronHourRange(Hour.fromInt(until.getMinValue()), until)));
					} else {
						parts.add(part.prepend(CronHourRange.EVERY_HOUR));
					}
				}
				
//				parts.add(dayPart.getFirstPart().prepend(new CronHourRange(from, Hour.fromInt(from.getMaxValue()))));
//				if (dayPart.hasIntermediateParts()) {
//					List<ComplexCron> intermediateParts = dayPart.getIntermediateParts();
//					intermediateParts.forEach(p -> parts.add(p.prepend(CronDay.EVERY_DAY)));
//				}
//				parts.add(dayPart.getLastPart().prepend(new CronHourRange(Hour.fromInt(until.getMinValue()), until)));
			}
		}
		return parts;
	}
	
	@Override
	public ComplexCron getFirstPart() {
		return dayPart.getFirstPart().prepend(new CronSpecificHours(from));
	}
	
	@Override
	public List<ComplexCron> getPartsInternal() {
		Preconditions.checkState(hasIntermediateParts());
		List<ComplexCron> parts = new ArrayList<>();
		parts.add(getFirstPart());
		// Tage im gleichen Monat
		if (dayPart.isFromEqualToUntil()) {
			ComplexCron singleDayPart = dayPart.getFirstPart();
			parts.add(singleDayPart.prepend(new CronHourRange(Hour.fromInt(from.getIntValue() + 1), Hour.fromInt(until.getIntValue() - 1))));
		} else {
//			List<ComplexCron> intermediateParts = dayPart.getIntermediateParts();
//			for (int i = 0; i < intermediateParts.size(); i++) {
//				ComplexCron part = intermediateParts.get(i);
//				if (i==0) {
//					parts.add(part.prepend(new CronHourRange(Hour.fromInt(from.getIntValue() + 1), Hour.fromInt(from.getMaxValue()))));
//				} else if (i==(intermediateParts.size()-1)) {
//					parts.add(part.prepend(new CronHourRange(Hour.fromInt(1), Hour.fromInt(until.getIntValue() - 1))));
//				} else {
//					parts.add(part.prepend(CronHourRange.EVERY_HOUR));
//				}
//			}
			
			parts.add(dayPart.getFirstPart().prepend(new CronHourRange(Hour.fromInt(from.getIntValue() + 1), Hour.fromInt(from.getMaxValue()))));
			if (dayPart.hasIntermediateParts()) {
				dayPart.getIntermediatePartsWithoutFirstAndLast().forEach(p ->
					parts.add(p.prepend(CronHour.EVERY_HOUR))
				);
			}
			parts.add(dayPart.getLastPart().prepend(new CronHourRange(Hour.fromInt(1), Hour.fromInt(until.getIntValue() - 1))));
			
		}
		parts.add(getLastPart());
		
		return parts;
	}
	
	@Override
	public ComplexCron getLastPart() {
		return dayPart.getLastPart().prepend(new CronSpecificHours(until));
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until) && dayPart.isFromEqualToUntil();
	}

	@Override
	public boolean hasIntermediateParts() {
		return until.getIntValue() - from.getIntValue() > 1 || dayPart.hasIntermediateParts();
	}
	
}
