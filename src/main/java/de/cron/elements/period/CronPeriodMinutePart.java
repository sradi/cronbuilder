package de.cron.elements.period;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.CronExpression;
import de.cron.elements.CronDay;
import de.cron.elements.CronElement;
import de.cron.elements.CronMinuteRange;
import de.cron.elements.CronSpecificMinutes;
import de.cron.units.Minute;

public class CronPeriodMinutePart extends BaseCronPeriodPart {
	
	private CronPeriodHourPart hourPart;
	private Minute from;
	private Minute until;

	public CronPeriodMinutePart(CronPeriodHourPart hourPart, Minute from, Minute until) {
		Preconditions.checkArgument(until.getIntValue() - from.getIntValue() >= 0 || !hourPart.isFromEqualToUntil()); // wenn gleicher Monat, mind. gleicher Tag 
		this.hourPart = hourPart;
		this.from = from;
		this.until = until;
	}

	@Override
	public List<CronExpression> getParts() {
		List<CronExpression> parts = new ArrayList<>();
		if (isFromEqualToUntil()) {
			List<CronExpression> hourParts = hourPart.getParts(); // hier kann nur ein DayPart zurueckkommen
			hourParts.forEach(p -> parts.add(p.prepend(getFromElement())));
		} else {
			if (hourPart.isFromEqualToUntil()) {
				// zwei oder mehr Stunden am gleichen Tag...
				List<CronExpression> hourParts = hourPart.getParts(); // hier kann nur ein DayPart zurueckkommen
				hourParts.forEach(p -> parts.add(p.prepend(getFullRangeElement())));
			} else {
				// zwei oder mehr Stunden über zwei oder mehrere Tage
				List<CronExpression> intermediateParts = hourPart.getPartsInternal();
				for (int i = 0; i < intermediateParts.size(); i++) {
					CronExpression part = intermediateParts.get(i);
					if (i==0) {
						parts.add(part.prepend(new CronMinuteRange(from, Minute.fromInt(hourPart.getLengthOfFromUnit()))));
					} else if (i==(intermediateParts.size()-1)) {
						parts.add(part.prepend(new CronMinuteRange(Minute.fromInt(until.getMinValue()), until)));
					} else {
						parts.add(part.prepend(CronMinuteRange.EVERY_MINUTE));
					}
				}
			}
		}
		return parts;
	}
	
	@Override
	public List<CronExpression> getPartsInternal() {
		List<CronExpression> parts = new ArrayList<>();
		List<CronExpression> hourParts = hourPart.getPartsInternal();
		Iterator<CronExpression> hourPartsIterator = hourParts.iterator();
		
		CronExpression firstDayPart = hourPartsIterator.next();
		CronExpression lastDayPart = hourParts.get(hourParts.size()-1);
		hourPartsIterator.remove();
		
		parts.add(firstDayPart.prepend(getFromElement()));

		if (isFromEqualToUntil()) {
			return parts; // gleicher Tag im gleichen Monat
		}
		
		if ((hourPart.isFromEqualToUntil()) && (hasIntermediateParts())) {
			// intermediate Tage im gleichen Monat: ganze Range, ohne den ersten und letzten Tag
			parts.add(firstDayPart.prepend(getIntermediatePart()));
		} else if (!hourPart.isFromEqualToUntil()) {
			// intermediate Tage in verschiedenen Monaten: Restliche Tage des 1. Monats
			parts.add(firstDayPart.prepend(getRestOfFromElement()));
		
			if (hourPart.hasIntermediateParts()) {
				// jeden Intermediate (ohne den ersten und den letzten) der naechsten Ebene mit "every" anreichern
				while (hourPartsIterator.hasNext()) {
					CronExpression intermediateDayPart = hourPartsIterator.next();
					if (!hourPartsIterator.hasNext()) {
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
		Minute intermediateFrom = Minute.fromInt(from.getIntValue() + 1);
		Minute maxFrom = Minute.fromInt(hourPart.getLengthOfFromUnit());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificMinutes(intermediateFrom); 
		} else {
			return new CronMinuteRange(intermediateFrom, maxFrom);
		}
	}
	
	private CronElement getBeginningOfUntilElement() {
		Minute minFrom = Minute.fromInt(until.getMinValue());
		Minute intermediateUntil = Minute.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificMinutes(minFrom); 
		} else {
			return new CronMinuteRange(minFrom, intermediateUntil);
		}
	}

	private CronElement getFromElement() {
		return new CronSpecificMinutes(from);
	}
	
	private CronElement getUntilElement() {
		return new CronSpecificMinutes(until);
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronMinuteRange(from, until);
		}
	}
	
	private CronElement getIntermediatePart() {
		Preconditions.checkState(hasIntermediateParts());;
		Minute intermediateFrom = Minute.fromInt(from.getIntValue() + 1);
		Minute intermediateUntil = Minute.fromInt(until.getIntValue() - 1);
		if (intermediateFrom.equals(intermediateUntil)) {
			return new CronSpecificMinutes(intermediateFrom); 
		} else {
			return new CronMinuteRange(intermediateFrom, intermediateUntil);
		}
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until) && hourPart.isFromEqualToUntil();
	}

	@Override
	public boolean hasIntermediateParts() {
		return until.getIntValue() - from.getIntValue() > 1 || hourPart.hasIntermediateParts();
	}

	@Override
	int getLengthOfFromUnit() {
		return from.getLength();
	}
	
}
