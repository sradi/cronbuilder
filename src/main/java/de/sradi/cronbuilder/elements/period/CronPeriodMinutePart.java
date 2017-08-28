package de.sradi.cronbuilder.elements.period;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.CronExpression;
import de.sradi.cronbuilder.elements.CronElement;
import de.sradi.cronbuilder.elements.CronElementEvery;
import de.sradi.cronbuilder.elements.CronMinuteRange;
import de.sradi.cronbuilder.elements.CronSpecificMinutes;
import de.sradi.cronbuilder.units.Minute;

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
							periodParts.add(part
									.prepend(new CronSpecificMinutes(from)));
						} else {
							periodParts.add(part.prepend(new CronMinuteRange(
									from, Minute.fromInt(getNextLevelPart()
											.getLengthOfFromUnit()))));
						}
					} else if (i==(intermediateParts.size()-1)) {
						periodParts.add(part.prepend(new CronMinuteRange(Minute.fromInt(until.getMinValue()), until)));
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
		Minute intermediateFrom = Minute.fromInt(from.getIntValue() + 1);
		Minute maxFrom = Minute.fromInt(getNextLevelPart().getLengthOfFromUnit());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificMinutes(intermediateFrom); 
		} else {
			return new CronMinuteRange(intermediateFrom, maxFrom);
		}
	}
	
	@Override
	protected CronElement getBeginningOfUntilElement() {
		Minute minFrom = Minute.fromInt(until.getMinValue());
		Minute intermediateUntil = Minute.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificMinutes(minFrom); 
		} else {
			return new CronMinuteRange(minFrom, intermediateUntil);
		}
	}

	@Override
	public CronElement getFromElement() {
		return new CronSpecificMinutes(from);
	}
	
	@Override
	public CronElement getUntilElement() {
		return new CronSpecificMinutes(until);
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronMinuteRange(from, until);
		}
	}
	
	@Override
	public CronElement getIntermediateElement() {
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
	public CronPeriodPart getNextLevelPart() {
		return hourPart;
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
