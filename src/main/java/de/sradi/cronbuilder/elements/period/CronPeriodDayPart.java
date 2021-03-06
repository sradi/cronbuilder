package de.sradi.cronbuilder.elements.period;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.CronExpression;
import de.sradi.cronbuilder.elements.CronDayRange;
import de.sradi.cronbuilder.elements.CronElement;
import de.sradi.cronbuilder.elements.CronElementEvery;
import de.sradi.cronbuilder.elements.CronSpecificDays;
import de.sradi.cronbuilder.units.Day;

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
					if (i == 0) {
						if (isFromEqualToUntil()
								|| (from.getIntValue() == getNextLevelPart()
										.getLengthOfFromUnit())) {
							periodParts.add(part.prepend(new CronSpecificDays(from)));
						} else {
							periodParts.add(part.prepend(new CronDayRange(from, Day.fromInt(getNextLevelPart().getLengthOfFromUnit()))));
						}
					} else if (i == (nextLevelParts.size() - 1)) {

						// Zeitraum dauert nur bis zum ersten Tag des letzten
						// Monats
						if (until.getIntValue() == 1) {
							periodParts.add(part.prepend(new CronSpecificDays(until)));
						} else {
							periodParts.add(
								part.prepend(
									new CronDayRange(Day.fromInt(until.getMinValue()), until)
								)
							);
						}
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
		Day intermediateFrom = Day.fromInt(from.getIntValue() + 1);
		Day maxFrom = Day.fromInt(getNextLevelPart().getLengthOfFromUnit());
		if (intermediateFrom.equals(maxFrom)) {
			return new CronSpecificDays(intermediateFrom);
		} else {
			return new CronDayRange(intermediateFrom, maxFrom);
		}
	}

	@Override
	protected CronElement getBeginningOfUntilElement() {
		Day minFrom = Day.fromInt(1);
		Day intermediateUntil = Day.fromInt(until.getIntValue() - 1);
		if (minFrom.equals(intermediateUntil)) {
			return new CronSpecificDays(minFrom);
		} else {
			return new CronDayRange(minFrom, intermediateUntil);
		}
	}

	@Override
	public CronElement getFromElement() {
		return new CronSpecificDays(from);
	}

	@Override
	public CronElement getUntilElement() {
		return new CronSpecificDays(until);
	}

	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronDayRange(from, until);
		}
	}

	@Override
	public CronElement getIntermediateElement() {
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

/*--- Formatiert nach TK Code Konventionen vom 05.03.2002 ---*/
