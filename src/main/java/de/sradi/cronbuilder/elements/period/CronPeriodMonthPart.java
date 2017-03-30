package de.sradi.cronbuilder.elements.period;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import de.sradi.cronbuilder.CronExpression;
import de.sradi.cronbuilder.elements.CronElement;
import de.sradi.cronbuilder.elements.CronMonthRange;
import de.sradi.cronbuilder.elements.CronSpecificMonths;

public class CronPeriodMonthPart implements CronPeriodPart {

	private Month from;
	private Month until;

	public CronPeriodMonthPart(Month from, Month until) {
		Preconditions.checkArgument(from.compareTo(until) <= 0);
		this.from = from;
		this.until = until;
	}

	@Override
	public List<CronExpression> getParts() {
		CronExpression part;
		if (isFromEqualToUntil()) {
			part = new CronExpression(getFromElement());
		} else {
			part = new CronExpression(getFullRangeElement());
		}
		return Arrays.asList(part);
	}

	@Override
	public List<CronExpression> getPartsInternal() {
		List<CronExpression> parts = new ArrayList<>();
		parts.add(new CronExpression(getFromElement()));
		
		if (isFromEqualToUntil()) {
			return parts;
		}
		
		if (hasIntermediateParts()) {
			parts.add(new CronExpression(getIntermediateElement()));
		}
		
		parts.add(new CronExpression(getUntilElement()));
		return parts;
	}
	
	@Override
	public CronElement getFromElement() {
		return new CronSpecificMonths(from);
	}

	@Override
	public CronElement getUntilElement() {
		CronSpecificMonths part = new CronSpecificMonths(until);
		return part;
	}
	
	private CronElement getFullRangeElement() {
		if (isFromEqualToUntil()) {
			return getFromElement();
		} else {
			return new CronMonthRange(from, until);
		}
	}
	
	@Override
	public CronElement getIntermediateElement() {
		Preconditions.checkState(hasIntermediateParts());;
		Month intermediateFrom = from.plus(1);
		Month intermediateUntil = until.minus(1);
		if (intermediateFrom.equals(intermediateUntil)) {
			return new CronSpecificMonths(intermediateFrom); 
		} else {
			return new CronMonthRange(intermediateFrom, intermediateUntil);
		}
	}
	
	@Override
	public boolean hasIntermediateParts() {
		return from.compareTo(until) < -1;
	}

	@Override
	public boolean isFromEqualToUntil() {
		return from.equals(until);
	}

	@Override
	public int getLengthOfFromUnit() {
		return from.maxLength();
	}

}
