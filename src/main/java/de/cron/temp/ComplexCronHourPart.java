package de.cron.temp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronElement;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronSpecificHours;
import de.cron.units.Hour;

public class ComplexCronHourPart extends BaseComplecCronPart {
	
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
			}
		}
		return parts;
	}
	
	@Override
	public List<ComplexCron> getPartsInternal() {
		List<ComplexCron> parts = new ArrayList<>();
		List<ComplexCron> dayParts = dayPart.getPartsInternal();
		Iterator<ComplexCron> dayPartsIterator = dayParts.iterator();
		
		ComplexCron firstDayPart = dayPartsIterator.next();
		ComplexCron lastDayPart = dayParts.get(dayParts.size()-1);
		dayPartsIterator.remove();
		
		parts.add(firstDayPart.prepend(getFromElement()));

		if (isFromEqualToUntil()) {
			return parts; // gleicher Tag im gleichen Monat
		}
		
		if ((dayPart.isFromEqualToUntil()) && (hasIntermediateParts())) {
			// intermediate Tage im gleichen Monat: ganze Range, ohne den ersten und letzten Tag
			parts.add(firstDayPart.prepend(getIntermediatePart()));
		} else if (!dayPart.isFromEqualToUntil()) {
			// intermediate Tage in verschiedenen Monaten: Restliche Tage des 1. Monats
			parts.add(firstDayPart.prepend(getRestOfFromElement()));
		
			if (dayPart.hasIntermediateParts()) {
				// jeden Intermediate (ohne den ersten und den letzten) der naechsten Ebene mit "every" anreichern
				while (dayPartsIterator.hasNext()) {
					ComplexCron intermediateDayPart = dayPartsIterator.next();
					if (!dayPartsIterator.hasNext()) {
						// wir sind beim vorletzten Intermediate angekommen. Abbruch.
						break;
					}
					parts.add(intermediateDayPart.prepend(CronDay.EVERY_DAY));
				}
			}
			
			// intermediate Tage in verschiedenen Monaten: 1. bis x. Tag des letzten Monats
			parts.add(lastDayPart.prepend(getBeginningOfUntilElement()));
		}

		parts.add(lastDayPart.prepend(getUntilElement()));
		return parts;
	}

	private CronElement getRestOfFromElement() {
		Hour intermediateFrom = Hour.fromInt(from.getIntValue() + 1);
		Hour maxFrom = Hour.fromInt(from.getMaxValue());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificHours(intermediateFrom); 
		} else {
			return new CronHourRange(intermediateFrom, maxFrom);
		}
	}
	
	private CronElement getBeginningOfUntilElement() {
		Hour minFrom = Hour.fromInt(until.getMinValue());
		Hour intermediateUntil = Hour.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificHours(minFrom); 
		} else {
			return new CronHourRange(minFrom, intermediateUntil);
		}
	}

	private CronElement getFromElement() {
		return new CronSpecificHours(from);
	}
	
	private CronElement getUntilElement() {
		return new CronSpecificHours(until);
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronHourRange(from, until);
		}
	}
	
	private CronElement getIntermediatePart() {
		Preconditions.checkState(hasIntermediateParts());;
		Hour intermediateFrom = Hour.fromInt(from.getIntValue() + 1);
		Hour intermediateUntil = Hour.fromInt(until.getIntValue() - 1);
		if (intermediateFrom.equals(intermediateUntil)) {
			return new CronSpecificHours(intermediateFrom); 
		} else {
			return new CronHourRange(intermediateFrom, intermediateUntil);
		}
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
