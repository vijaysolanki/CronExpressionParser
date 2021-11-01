package com.vjy.deliveroo.cron.fields;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base class for expression fields contains business logic to parse cron expression string.
 *
 * @author vsolanki
 */
public abstract class BaseField {

    public static final String INCREMENT_SEP = "/";

    //@formatter:off
    private static final Pattern CRON_EXP_PTRN = Pattern
            .compile("(?:"
                            + "  (?<all>\\*)  # global flag \n"
                            + "  | (?<start>[0-9]{1,2}|[a-z]{3,3}) \n"
                            + "      (?:\n"
                            + "       -(?<end>[0-9]{1,2}|[a-z]{3,3})\n"
                            + "      )?\n"
                            + ")\n"
                            + "(?:(?<incMetaChar>/)(?<inc>[0-9]{1,2}))?        # increment and increment modifier (/)\n",
                    Pattern.CASE_INSENSITIVE | Pattern.COMMENTS);
    //@formatter:on

    protected FieldType type;
    private final List<FieldPart> parts = new ArrayList<>();

    protected BaseField(FieldType type, String fldExp) {
        this.type = type;
        parse(fldExp);
    }

    private void parse(String exp) {
        // handle range for expression part
        String[] fldRngParts = exp.split(",");

        for (String rngPart : fldRngParts) {

            Matcher m = CRON_EXP_PTRN.matcher(rngPart);
            if (!m.matches()) {
                throw new IllegalArgumentException("Invalid cron input '" + rngPart + "' for field [" + type + "]");
            }

            String startNumber = m.group("start");
            // "-" range is specified
            String endNumber = m.group("end");
            // "/" increment
            String incMetaChar = m.group("incMetaChar");
            String increment = m.group("inc");

            FieldPart fldPart = new FieldPart();

            if (startNumber != null) {
                fldPart.from = mapToValue(startNumber);
                if (endNumber != null) {
                    fldPart.to = mapToValue(endNumber);
                } else if (increment != null) {
                    fldPart.to = type.getTo();
                } else {
                    fldPart.to = fldPart.from;
                }

            } else if (m.group("all") != null) {
                fldPart.from = type.getFrom();
                fldPart.to = type.getTo();
                fldPart.all = true;

            } else {
                throw new IllegalArgumentException(
                        "Invalid expression field part: " + rngPart + " for field [" + type + "]");
            }

            if (increment != null) {
                fldPart.incrementMetaChar = incMetaChar;
                fldPart.incrementValue = Integer.parseInt(increment);
            }

            rangeValidation(fldPart);
            partMetaValidation(fldPart);
            parts.add(fldPart);
        }

//		Collections.sort(parts);
    }

    /**
     * Validate field part range.
     *
     * @param part of expression.
     */
    protected void rangeValidation(FieldPart part) {
        if ((part.from != -1 && part.from < type.getFrom()) || (part.to != -1 && part.to > type.getTo())) {
            throw new IllegalArgumentException(String.format("Invalid interval [%s-%s], must be [%s-%s] for field [%s]",
                    part.from, part.to, type.getFrom(), type.getTo(), type));
        } else if (part.from != -1 && part.to != -1 && part.from > part.to) {
            throw new IllegalArgumentException(String.format(
                    "Invalid interval [%s-%s].  Rolling periods are not supported (ex. 4-1, only 1-4). Must be %s<=_<=%s",
                    part.from, part.to, type.getFrom(), type.getTo()));
        }
    }

    /**
     * Validate meta characters.
     */
    protected void partMetaValidation(FieldPart part) {
        if (part.metaChar != null) {
            throw new IllegalArgumentException(
                    String.format("Invalid meta character [%s] for field [%s]", part.metaChar, type.toString()));
        } else if (part.incrementMetaChar != null && !INCREMENT_SEP.equals(part.incrementMetaChar)) {
            throw new IllegalArgumentException(String.format("Invalid increment meta character [%s] for field [%s]",
                    part.incrementMetaChar, type.toString()));
        }
    }

    /**
     * Convert to number, if months or weeks are present then map name to their
     * numeric value and return corresponding numeric value .
     *
     * @param value string value
     * @return int numeric value
     */
    protected int mapToValue(String value) {
        int idx;
        if (type.getNames() != null && (idx = type.getNames().indexOf(value.toUpperCase(Locale.getDefault()))) >= 0) {
            return idx + type.getFrom();
        }
        return Integer.parseInt(value);
    }

    /**
     * Return undying values of field
     *
     * @return set of integer
     */
    public Set<Integer> get() {

        Set<Integer> result = null;
        switch (type) {

            case MINUTE:
                result = getMinutes();
                break;
            case HOUR:
                result = getHours();
                break;
            case MONTH:
                result = getMonths();
                break;
            default:
                break;
        }

        return result;
    }

    protected Set<Integer> build() {

        Set<Integer> values = new TreeSet<>();
        parts.forEach(fieldPart -> {
            // for all (*)
            if (fieldPart.all && null == fieldPart.incrementMetaChar) {
                int i = fieldPart.from;
                while (i <= fieldPart.to) {
                    values.add(i++);
                }
            }
            // for range (-)
            else if (null == fieldPart.metaChar && null == fieldPart.incrementMetaChar) {
                int inc = fieldPart.from;
                while (inc <= fieldPart.to) {
                    values.add(inc++);
                }
            } // for increment (/)
            else if (null != fieldPart.incrementMetaChar) {
                values.add(fieldPart.from);
                int inc = fieldPart.from + fieldPart.incrementValue;
                while (inc <= fieldPart.to) {
                    values.add(inc);
                    inc = inc + fieldPart.incrementValue;
                }
            }
        });

        return values;
    }

    public Set<Integer> getMinutes() {
        return build();
    }

    public Set<Integer> getHours() {
        return build();
    }

    public Set<Integer> getMonths() {
        return build();
    }
}
