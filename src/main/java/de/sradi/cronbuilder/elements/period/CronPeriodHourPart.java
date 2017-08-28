package de.sradi.cronbuilder.elements.period;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.CronExpression;
import de.sradi.cronbuilder.elements.CronElement;
import de.sradi.cronbuilder.elements.CronElementEvery;
import de.sradi.cronbuilder.elements.CronHourRange;
import de.sradi.cronbuilder.elements.CronSpecificHours;
import de.sradi.cronbuilder.units.Hour;

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
						if (isFromEqualToUntil()
								|| (from.getIntValue() == getNextLevelPart()
										.getLengthOfFromUnit())) {
							periodParts.add(part.prepend(new CronSpecificHours(
									from)));
						} else {
							periodParts.add(part.prepend(new CronHourRange(
									from, Hour.fromInt(getNextLevelPart()
											.getLengthOfFromUnit()))));
						}
					} else if (i==(intermediateParts.size()-1)) {
						periodParts.add(part.prepend(new CronHourRange(Hour.fromInt(until.getMinValue()), until)));
					} else {
						periodParts.add(part.prepend(CronElementEvery.INSTANCE));
					}
				}
			}
		}
		return periodParts;
	}

	@Override
	protected CronElement getRestOfFromElement() {
		Hour intermediateFrom = Hour.fromInt(from.getIntValue() + 1);
		Hour maxFrom = Hour.fromInt(getNextLevelPart().getLengthOfFromUnit());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificHours(intermediateFrom); 
		} else {
			return new CronHourRange(intermediateFrom, maxFrom);
		}
	}
	
	@Override
	protected CronElement getBeginningOfUntilElement() {
		Hour minFrom = Hour.fromInt(until.getMinValue());
		Hour intermediateUntil = Hour.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificHours(minFrom); 
		} else {
			return new CronHourRange(minFrom, intermediateUntil);
		}
	}

	@Override
	public CronElement getFromElement() {
		return new CronSpecificHours(from);
	}
	
	@Override
	public CronElement getUntilElement() {
		return new CronSpecificHours(until);
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronHourRange(from, until);
		}
	}
	
	@Override
	public CronElement getIntermediateElement() {
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
