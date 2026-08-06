package com.enrollment.request.deserializer;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.EnumSet;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.util.StdConverter;

public class DayOfWeekDeserializer extends StdConverter<Collection<String>, EnumSet<DayOfWeek>> {
	
	@Override
    public EnumSet<DayOfWeek> convert(Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return EnumSet.noneOf(DayOfWeek.class);
        }
        return values.stream()
                .map(val -> DayOfWeek.valueOf(val.toUpperCase()))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(DayOfWeek.class)));
    }
}
