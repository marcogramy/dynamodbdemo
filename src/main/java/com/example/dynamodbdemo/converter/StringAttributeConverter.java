package com.example.dynamodbdemo.converter;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class StringAttributeConverter implements AttributeConverter<String> {

    public StringAttributeConverter() {}

    @Override
    public AttributeValue transformFrom(String input) {
        return EnhancedAttributeValue.fromString(input).toAttributeValue();
    }

    @Override
    public String transformTo(AttributeValue input) {
        return input.s();
    }

    @Override
    public EnhancedType<String> type() {
        return EnhancedType.of(String.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
