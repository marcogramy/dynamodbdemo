package com.example.dynamodbdemo.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.LongAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.MapAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.OptionalAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.string.StringStringConverter;

import java.util.*;
import java.util.stream.Collectors;

public class CustomAttributeConverterProvider implements AttributeConverterProvider {

    private final Map<EnhancedType<?>, AttributeConverter<?>> customConvertersMap;
    private final AttributeConverterProvider defaultProvider = DefaultAttributeConverterProvider.create();

    public CustomAttributeConverterProvider() {
        OptionalAttributeConverter<String> optionalStringConverter = OptionalAttributeConverter.create(StringAttributeConverter.create());
        OptionalAttributeConverter<Long> optionalLongConverter = OptionalAttributeConverter.create(LongAttributeConverter.create());

        MapAttributeConverter<Map<String, String>> mapConverter = MapAttributeConverter.builder(EnhancedType.mapOf(String.class, String.class))
                .mapConstructor(HashMap::new)
                .keyConverter(StringStringConverter.create())
                .valueConverter(StringAttributeConverter.create())
                .build();
        OptionalAttributeConverter<Map<String, String>> optionalMapAttributeConverter = OptionalAttributeConverter.create(mapConverter);

        List<AttributeConverter<?>> customConverters = Arrays.asList(optionalStringConverter, optionalLongConverter, optionalMapAttributeConverter);
        customConvertersMap = customConverters.stream().collect(Collectors.toMap(AttributeConverter::type, c -> c));
    }

    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return (AttributeConverter<T>)
                customConvertersMap.computeIfAbsent(enhancedType, defaultProvider::converterFor);
    }
}
