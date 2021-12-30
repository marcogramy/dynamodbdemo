package com.example.dynamodbdemo.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.OptionalAttributeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAttributeConverterProvider implements AttributeConverterProvider {

    private final Map<EnhancedType<?>, AttributeConverter<?>> customConvertersMap;
    private final AttributeConverterProvider defaultProvider = DefaultAttributeConverterProvider.create();

    public CustomAttributeConverterProvider() {
        StringAttributeConverter stringAttributeConverter = new StringAttributeConverter();
        OptionalAttributeConverter<String> optionalStringConverter = OptionalAttributeConverter.create(stringAttributeConverter);

        List<AttributeConverter<?>> customConverters = Arrays.asList(optionalStringConverter);
        customConvertersMap = customConverters.stream().collect(Collectors.toMap(AttributeConverter::type, c -> c));
    }

    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return (AttributeConverter<T>)
                customConvertersMap.computeIfAbsent(enhancedType, defaultProvider::converterFor);
    }
}
