package com.mediscreen.patient.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

public class StringToLocalDate implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(source, formatter);
    }

}
