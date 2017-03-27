package de.cron.temp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronDay;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronElement;
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
		List<ComplexCron> monthParts = monthPart.getPartsInternal();

		if (isFromEqualToUntil()) {
			monthParts.forEach(p -> parts.add(p.prepend(getFromElement())));
		} else {
			if (monthPart.isFromEqualToUntil()) {
				// zwei oder mehr Tage im gleichen Monat...
				monthParts.forEach(p -> parts.add(p.prepend(getFullRangeElement())));
			} else {
				// über zwei oder mehrere Monate
				for (int i = 0; i < monthParts.size(); i++) {
					ComplexCron part = monthParts.get(i);
					if (i==0) {
						parts.add(part.prepend(new CronDayRange(from, Day.fromInt(from.getMaxValue()))));
					} else if (i==(monthParts.size()-1)) {
						parts.add(part.prepend(new CronDayRange(Day.fromInt(until.getMinValue()), until)));
					} else {
						parts.add(part.prepend(CronDayRange.EVERY_DAY));
					}
				}
			}
		}
		return parts;
	}
	
	@Override
	public List<ComplexCron> getPartsInternal() {
		List<ComplexCron> parts = new ArrayList<>();
		List<ComplexCron> monthParts = monthPart.getPartsInternal();
		Iterator<ComplexCron> monthPartsIterator = monthParts.iterator();
		
		ComplexCron firstMonthPart = monthPartsIterator.next();
		ComplexCron lastMonthPart = monthParts.get(monthParts.size()-1);
		monthPartsIterator.remove();
		
		parts.add(firstMonthPart.prepend(getFromElement()));

		if (isFromEqualToUntil()) {
			return parts; // gleicher Tag im gleichen Monat
		}
		
		if ((monthPart.isFromEqualToUntil()) && (hasIntermediateParts())) {
			// intermediate Tage im gleichen Monat: ganze Range, ohne den ersten und letzten Tag
			parts.add(firstMonthPart.prepend(getIntermediatePart()));
		} else if (!monthPart.isFromEqualToUntil()) {
			// intermediate Tage in verschiedenen Monaten: Restliche Tage des 1. Monats
			parts.add(firstMonthPart.prepend(getRestOfFromElement()));
		
			if (monthPart.hasIntermediateParts()) {
				// jeden Intermediate (ohne den ersten und den letzten) der naechsten Ebene mit "every" anreichern
				while (monthPartsIterator.hasNext()) {
					ComplexCron intermediateMonthPart = monthPartsIterator.next();
					if (!monthPartsIterator.hasNext()) {
						// wir sind beim vorletzten Intermediate angekommen. Abbruch.
						break;
					}
					parts.add(intermediateMonthPart.prepend(CronDay.EVERY_DAY));
				}
			}
			
			// intermediate Tage in verschiedenen Monaten: 1. bis x. Tag des letzten Monats
			parts.add(lastMonthPart.prepend(getBeginningOfUntilElement()));
		}

		parts.add(lastMonthPart.prepend(getUntilElement()));
		return parts;
	}

	private CronElement getRestOfFromElement() {
		Day intermediateFrom = Day.fromInt(from.getIntValue() + 1);
		Day maxFrom = Day.fromInt(from.getMaxValue());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificDays(intermediateFrom); 
		} else {
			return new CronDayRange(intermediateFrom, maxFrom);
		}
	}
	
	private CronElement getBeginningOfUntilElement() {
		Day minFrom = Day.fromInt(1);
		Day intermediateUntil = Day.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificDays(minFrom); 
		} else {
			return new CronDayRange(minFrom, intermediateUntil);
		}
	}

	private CronElement getFromElement() {
		return new CronSpecificDays(from);
	}
	
	private CronElement getUntilElement() {
		return new CronSpecificDays(until);
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronDayRange(from, until);
		}
	}
	
	private CronElement getIntermediatePart() {
		Preconditions.checkState(hasIntermediateParts());;
		Day intermediateFrom = Day.fromInt(from.getIntValue() + 1);
		Day intermediateUntil = Day.fromInt(until.getIntValue() - 1);
		if (intermediateFrom.equals(intermediateUntil)) {
			return new CronSpecificDays(intermediateFrom); 
		} else {
			return new CronDayRange(intermediateFrom, intermediateUntil);
		}
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
