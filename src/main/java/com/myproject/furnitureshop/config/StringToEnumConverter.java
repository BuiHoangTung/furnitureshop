package com.myproject.furnitureshop.config;

import com.myproject.furnitureshop.enums.NotificationChannels;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, NotificationChannels> {
    @Override
    public NotificationChannels convert(String source) {
        return NotificationChannels.valueOf(source.toUpperCase());
    }
}
