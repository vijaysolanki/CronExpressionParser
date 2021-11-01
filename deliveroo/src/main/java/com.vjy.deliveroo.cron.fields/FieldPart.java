package com.vjy.deliveroo.cron.fields;

/**
 * Cron Expression field part model.
 * @author vsolanki
 */
 class FieldPart implements Comparable<FieldPart>{
	protected int from = -1;
	protected int to = -1;
	protected int incrementValue = -1;
	protected String metaChar;
	protected String incrementMetaChar;
	protected boolean all;
	
	@Override
	public int compareTo(FieldPart part) {
		return Integer.compare(from, part.from);
	}
}
