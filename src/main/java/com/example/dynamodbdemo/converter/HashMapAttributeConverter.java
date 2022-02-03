package com.example.dynamodbdemo.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.MapAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.StringAttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.string.StringStringConverter;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.HashMap;
import java.util.Map;

public class HashMapAttributeConverter implements AttributeConverter<Map<String, String>> {

    private static AttributeConverter<Map<String, String>> mapConverter;

    public HashMapAttributeConverter() {
        mapConverter =
                MapAttributeConverter.builder(EnhancedType.mapOf(String.class, String.class))
                        .mapConstructor(HashMap::new)
                        .keyConverter(StringStringConverter.create())
                        .valueConverter(StringAttributeConverter.create())
                        .build();
    }

    @Override
    public AttributeValue transformFrom(Map<String, String> stringStringMap) {
        return mapConverter.transformFrom(stringStringMap);
    }

    @Override
    public Map<String, String> transformTo(AttributeValue attributeValue) {
        return mapConverter.transformTo(attributeValue);
    }

    @Override
    public EnhancedType<Map<String, String>> type() {
        return mapConverter.type();
    }

    @Override
    public AttributeValueType attributeValueType() {
        return mapConverter.attributeValueType();
    }
}
