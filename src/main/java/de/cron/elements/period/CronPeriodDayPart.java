package de.cron.elements.period;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.CronExpression;
import de.cron.elements.CronDayRange;
import de.cron.elements.CronElement;
import de.cron.elements.CronElementEvery;
import de.cron.elements.CronSpecificDays;
import de.cron.units.Day;

public class CronPeriodDayPart extends BaseCronPeriodPart {
	
	private CronPeriodMonthPart monthPart;
	private Day from;
	private Day until;

	public CronPeriodDayPart(CronPeriodMonthPart monthPart, Day from, Day until) {
		Preconditions.checkArgument(until.getIntValue() - from.getIntValue() >= 0 || !monthPart.isFromEqualToUntil()); // wenn gleicher Monat, mind. gleicher Tag 
		this.monthPart = monthPart;
		this.from = from;
		this.until = until;
	}

	@Override
	public List<CronExpression> getParts() {
		List<CronExpression> periodParts = new ArrayList<>();
		List<CronExpression> nextLevelParts = getNextLevelPart().getPartsInternal();

		if (isFromEqualToUntil()) {
			nextLevelParts.forEach(p -> periodParts.add(p.prepend(getFromElement())));
		} else {
			if (getNextLevelPart().isFromEqualToUntil()) {
				// zwei oder mehr Tage im gleichen Monat...
				nextLevelParts.forEach(p -> periodParts.add(p.prepend(getFullRangeElement())));
			} else {
				// über zwei oder mehrere Monate
				for (int i = 0; i < nextLevelParts.size(); i++) {
					CronExpression part = nextLevelParts.get(i);
					if (i==0) {
						periodParts.add(part.prepend(new CronDayRange(from, Day.fromInt(getNextLevelPart().getLengthOfFromUnit()))));
					} else if (i==(nextLevelParts.size()-1)) {
						periodParts.add(part.prepend(new CronDayRange(Day.fromInt(until.getMinValue()), until)));
					} else {
						periodParts.add(part.prepend(CronElementEvery.INSTANCE));
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
					CronExpression intermediateMonthPart = nextLevelPartsIterator.next();
					if (!nextLevelPartsIterator.hasNext()) {
						// wir sind beim vorletzten Intermediate angekommen. Abbruch.
						break;
					}
					periodParts.add(intermediateMonthPart.prepend(CronElementEvery.INSTANCE));
				}
			}
			
			// intermediate Tage in verschiedenen Monaten: 1. bis x. Tag des letzten Monats
			periodParts.add(lastPart.prepend(getBeginningOfUntilElement()));
		}

		periodParts.add(lastPart.prepend(getUntilElement()));
		return periodParts;
	}

	private CronElement getRestOfFromElement() {
		Day intermediateFrom = Day.fromInt(from.getIntValue() + 1);
		Day maxFrom = Day.fromInt(getNextLevelPart().getLengthOfFromUnit());
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
	public CronPeriodPart getNextLevelPart() {
		return monthPart;
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
