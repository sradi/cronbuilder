package de.cron.elements.period;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.cron.CronExpression;
import de.cron.elements.CronElement;
import de.cron.elements.CronElementEvery;

public abstract class BaseCronPeriodPart implements CronPeriodPart {
	
	public abstract CronPeriodPart getNextLevelPart();
	
	protected abstract CronElement getRestOfFromElement();
	
	protected abstract CronElement getBeginningOfUntilElement();
	
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
			periodParts.add(firstPart.prepend(getIntermediateElement()));
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
					periodParts.add(intermediateDayPart.prepend(CronElementEvery.INSTANCE));
				}
			}
			
			// intermediate Tage in verschiedenen Monaten: 1. bis x. Tag des letzten Monats
			periodParts.add(lastPart.prepend(getBeginningOfUntilElement()));
		}

		periodParts.add(lastPart.prepend(getUntilElement()));
		return periodParts;
	}
	
}
