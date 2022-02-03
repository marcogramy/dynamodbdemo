package com.example.dynamodbdemo.converter;

import com.example.dynamodbdemo.model.OptModel;
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

        // Case 1: using custom AttributeConverter implementation
        //OptionalAttributeConverter<Map<String, String>> optionalMapAttributeConverter = OptionalAttributeConverter.create(new HashMapAttributeConverter());

        // Case 2: using MapAttributeConverter from AWS SDK
        //AttributeConverter<Map<String, String>> mapConverter = MapAttributeConverter.mapConverter(StringStringConverter.create(), StringAttributeConverter.create());
        //OptionalAttributeConverter<Map<String, String>> optionalMapAttributeConverter = OptionalAttributeConverter.create(mapConverter);

        // Custom converter for Optional custom object
        OptionalAttributeConverter<OptModel> optionalOptModelConverter = OptionalAttributeConverter.create(new OptModelAttributeConverter());

        List<AttributeConverter<?>> customConverters = Arrays.asList(optionalStringConverter, optionalLongConverter, optionalOptModelConverter);
        customConvertersMap = customConverters.stream().collect(Collectors.toMap(AttributeConverter::type, c -> c));
    }

    @Override
    public <T> AttributeConverter<T> converterFor(EnhancedType<T> enhancedType) {
        return (AttributeConverter<T>)
                customConvertersMap.computeIfAbsent(enhancedType, defaultProvider::converterFor);
    }
}
