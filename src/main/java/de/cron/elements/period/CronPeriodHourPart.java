package de.cron.elements.period;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.CronExpression;
import de.cron.elements.CronElement;
import de.cron.elements.CronHour;
import de.cron.elements.CronHourRange;
import de.cron.elements.CronSpecificHours;
import de.cron.units.Hour;

public class CronPeriodHourPart extends BaseCronPeriodPart {
	
	private CronPeriodDayPart dayPart;
	private Hour from;
	private Hour until;

	public CronPeriodHourPart(CronPeriodDayPart dayPart, Hour from, Hour until) {
		Preconditions.checkArgument(until.getIntValue() - from.getIntValue() >= 0 || !dayPart.isFromEqualToUntil()); // wenn gleicher Monat, mind. gleicher Tag 
		this.dayPart = dayPart;
		this.from = from;
		this.until = until;
	}

	@Override
	public List<CronExpression> getParts() {
		List<CronExpression> periodParts = new ArrayList<>();
		List<CronExpression> nextLevelParts = getNextLevelPart().getParts(); // hier kann nur ein DayPart zurueckkommen
		
		if (isFromEqualToUntil()) {
			nextLevelParts.forEach(p -> periodParts.add(p.prepend(getFromElement())));
		} else {
			if (getNextLevelPart().isFromEqualToUntil()) {
				// zwei oder mehr Stunden am gleichen Tag...
				nextLevelParts.forEach(p -> periodParts.add(p.prepend(getFullRangeElement())));
			} else {
				// zwei oder mehr Stunden über zwei oder mehrere Tage
				List<CronExpression> intermediateParts = getNextLevelPart().getPartsInternal();
				for (int i = 0; i < intermediateParts.size(); i++) {
					CronExpression part = intermediateParts.get(i);
					if (i==0) {
						periodParts.add(part.prepend(new CronHourRange(from, Hour.fromInt(getNextLevelPart().getLengthOfFromUnit()))));
					} else if (i==(intermediateParts.size()-1)) {
						periodParts.add(part.prepend(new CronHourRange(Hour.fromInt(until.getMinValue()), until)));
					} else {
						periodParts.add(part.prepend(CronHourRange.EVERY_HOUR));
					}
				}
			}
		}
		return periodParts;
	}
	
	@Override
	public List<CronExpression> getPartsInternal() {
		List<CronExpression> periodParts = new ArrayList<>();
		List<CronExpression> nextLevelParts = getNextLevelPart().getPartsInternal();
		Iterator<CronExpression> nextLevelPartsIterator = nextLevelParts.iterator();
		
		CronExpression firstPart = nextLevelPartsIterator.next();
		CronExpression lastPart = nextLevelParts.get(nextLevelParts.size()-1);
		nextLevelPartsIterator.remove();
		
		periodParts.add(firstPart.prepend(getFromElement()));

		if (isFromEqualToUntil()) {
			return periodParts; // gleicher Tag im gleichen Monat
		}
		
		if ((getNextLevelPart().isFromEqualToUntil()) && (hasIntermediateParts())) {
			// intermediate Tage im gleichen Monat: ganze Range, ohne den ersten und letzten Tag
			periodParts.add(firstPart.prepend(getIntermediatePart()));
		} else if (!getNextLevelPart().isFromEqualToUntil()) {
			// intermediate Tage in verschiedenen Monaten: Restliche Tage des 1. Monats
			periodParts.add(firstPart.prepend(getRestOfFromElement()));
		
			if (getNextLevelPart().hasIntermediateParts()) {
				// jeden Intermediate (ohne den ersten und den letzten) der naechsten Ebene mit "every" anreichern
				while (nextLevelPartsIterator.hasNext()) {
					CronExpression intermediateDayPart = nextLevelPartsIterator.next();
					if (!nextLevelPartsIterator.hasNext()) {
						// wir sind beim vorletzten Intermediate angekommen. Abbruch.
						break;
					}
					periodParts.add(intermediateDayPart.prepend(CronHour.EVERY_HOUR));
				}
			}
			
			// intermediate Tage in verschiedenen Monaten: 1. bis x. Tag des letzten Monats
			periodParts.add(lastPart.prepend(getBeginningOfUntilElement()));
		}

		periodParts.add(lastPart.prepend(getUntilElement()));
		return periodParts;
	}

	private CronElement getRestOfFromElement() {
		Hour intermediateFrom = Hour.fromInt(from.getIntValue() + 1);
		Hour maxFrom = Hour.fromInt(getNextLevelPart().getLengthOfFromUnit());
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
	public CronPeriodPart getNextLevelPart() {
		return dayPart;
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until) && getNextLevelPart().isFromEqualToUntil();
	}

	@Override
	public boolean hasIntermediateParts() {
		return until.getIntValue() - from.getIntValue() > 1 || getNextLevelPart().hasIntermediateParts();
	}

	@Override
	public int getLengthOfFromUnit() {
		return from.getLength();
	}
	
}
