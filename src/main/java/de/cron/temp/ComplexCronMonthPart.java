package de.cron.temp;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import de.cron.elements.CronMonthRange;
import de.cron.elements.CronSpecificMonths;

public class ComplexCronMonthPart implements ComplexCronPart {

	private Month from;
	private Month until;

	public ComplexCronMonthPart(Month from, Month until) {
		Preconditions.checkArgument(from.compareTo(until) <= 0);
		this.from = from;
		this.until = until;
	}

	@Override
	public List<ComplexCron> getParts() {
		ComplexCron part;
		if (isFromEqualToUntil()) {
			part = new ComplexCronImpl(new CronSpecificMonths(from));
		} else {
			part = new ComplexCronImpl(new CronMonthRange(from, until));
		}
		return Arrays.asList(part);
	}
	
	@Override
	public ComplexCron getFirstPart() {
		return new ComplexCronImpl(new CronSpecificMonths(from));
	}
	
	@Override
	public List<ComplexCron> getIntermediateParts() {
		Preconditions.checkState(hasIntermediateParts());
		ComplexCron part = new ComplexCronImpl(new CronMonthRange(from.plus(1), until.minus(1)));
		return Arrays.asList(part);
	}
	
	@Override
	public ComplexCron getLastPart() {
		ComplexCron part = new ComplexCronImpl(new CronSpecificMonths(until));
		return part;
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
