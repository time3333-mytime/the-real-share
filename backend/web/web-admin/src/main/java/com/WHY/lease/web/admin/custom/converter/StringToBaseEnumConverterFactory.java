package com.WHY.lease.web.admin.custom.converter;

import com.WHY.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String code) {
                for (T value : targetType.getEnumConstants()) {
                    if (value.getCode().equals(Integer.valueOf(code))) {
                        return value;
                    }
                }
                throw new IllegalArgumentException("code非法");
            }
        };
    }
}
