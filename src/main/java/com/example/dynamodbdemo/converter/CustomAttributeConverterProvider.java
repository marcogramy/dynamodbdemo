package com.example.dynamodbdemo.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.LongAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.OptionalAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAttributeConverterProvider implements AttributeConverterProvider {

    private final Map<EnhancedType<?>, AttributeConverter<?>> customConvertersMap;
    private final AttributeConverterProvider defaultProvider = DefaultAttributeConverterProvider.create();

    public CustomAttributeConverterProvider() {
        OptionalAttributeConverter<String> optionalStringConverter = OptionalAttributeConverter.create(StringAttributeConverter.create());
        OptionalAttributeConverter<Long> optionalLongConverter = OptionalAttributeConverter.create(LongAttributeConverter.create());

        List<AttributeConverter<?>> customConverters = Arrays.asList(optionalStringConverter, optionalLongConverter);
        customConvertersMap = customConverters.stream().collect(Collectors.toMap(AttributeConverter::type, c -> c));
    }

    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return (AttributeConverter<T>)
                customConvertersMap.computeIfAbsent(enhancedType, defaultProvider::converterFor);
    }
}
