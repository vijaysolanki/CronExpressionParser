package com.vjy.deliveroo.cron.fields;

import java.util.Set;

/**
 * Class representing Day of Week field.
 * @author vsolanki
 */
public class DayOfWeekField extends SimpleField {

	public DayOfWeekField(FieldType type, String fldExp) {
		super(type, fldExp);
	}

	@Override
	protected int mapToValue(String value) {
		// 0 and 7 are represented as sunday
		return "0".equals(value) ? 7 : super.mapToValue(value);
	}

	public Set<Integer> getDaysOfWeek() {
		return build();
	}

	@Override
	public Set<Integer> get() {
		Set<Integer> result = null;

		if(FieldType.DAY_OF_WEEK == type) {
			result = getDaysOfWeek();
		} else {
			result = super.get();
		}
		return result;
	}
}
