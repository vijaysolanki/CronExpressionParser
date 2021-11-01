package com.vjy.deliveroo.cron.fields;

import java.util.Set;

/**
 * Class representing Day of Month field.
 * @author vsolanki
 */
public class DayOfMonthField extends SimpleField {

	public DayOfMonthField(FieldType type, String fldExp) {
		super(type, fldExp);
	}

	public Set<Integer> getDaysOfMonth() {
		return build();
	}

	@Override
	public Set<Integer> get() {
		Set<Integer> result = null;

		if(FieldType.DAY_OF_MONTH == type) {
			result = getDaysOfMonth();
		} else {
			result = super.get();
		}
		return result;
	}
}
