package de.cron.temp;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronElement;
import de.cron.elements.CronMonthRange;
import de.cron.elements.CronSpecificMonths;

public class ComplexCronMonthPart extends BaseComplecCronPart {

	private Month from;
	private Month until;

	public ComplexCronMonthPart(Month from, Month until) {
		Preconditions.checkArgument(from.compareTo(until) <= 0);
		this.from = from;
		this.until = until;
	}

	@Override
	public List<CronExpression> getParts() {
		CronExpression part;
		if (isFromEqualToUntil()) {
			part = new CronExpressionImpl(getFromElement());
		} else {
			part = new CronExpressionImpl(getFullRangeElement());
		}
		return Arrays.asList(part);
	}

	@Override
	public List<CronExpression> getPartsInternal() {
		List<CronExpression> parts = new ArrayList<>();
		parts.add(new CronExpressionImpl(getFromElement()));
		
		if (isFromEqualToUntil()) {
			return parts;
		}
		
		if (hasIntermediateParts()) {
			parts.add(new CronExpressionImpl(getIntermediateElement()));
		}
		
		parts.add(new CronExpressionImpl(getUntilElement()));
		return parts;
	}
	
	private CronElement getFromElement() {
		return new CronSpecificMonths(from);
	}

	private CronElement getUntilElement() {
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
	
	private CronElement getIntermediateElement() {
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

}
