package de.cron;

import java.util.Iterator;
import java.util.List;

public class ComplexCronDefinition implements Iterable<CronDefinition> {
	
	private List<CronDefinition> crons;

	public CronDefinition get(int i) {
		// TODO return copy of CronDefinition, in order to make this class immutable
		return crons.get(i);
	}
	
	public int size() {
		return crons.size();
	}

	@Override
	public Iterator<CronDefinition> iterator() {
		return crons.iterator();
	}

}
