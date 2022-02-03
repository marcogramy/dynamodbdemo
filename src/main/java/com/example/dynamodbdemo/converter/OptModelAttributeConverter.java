package com.example.dynamodbdemo.converter;

import com.example.dynamodbdemo.model.OptModel;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.internal.converter.attribute.EnhancedAttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class OptModelAttributeConverter implements AttributeConverter<OptModel> {

    @Override
    public AttributeValue transformFrom(OptModel input) {
        Map<String, AttributeValue> mapAttr = new HashMap<>();
        mapAttr.put("innerValue", EnhancedAttributeValue.fromString(input.getInnerValue()).toAttributeValue());
        mapAttr.put("intValue", EnhancedAttributeValue.fromNumber(String.valueOf(input.getIntValue())).toAttributeValue());
        mapAttr.put("url", EnhancedAttributeValue.fromString(input.getUrl().toString()).toAttributeValue());
        mapAttr.put("contentType", EnhancedAttributeValue.fromString(input.getContentType().name()).toAttributeValue());

        return EnhancedAttributeValue.fromMap(mapAttr).toAttributeValue();
    }

    @Override
    public OptModel transformTo(AttributeValue input) {
        return new OptModel(input.s(), Integer.parseInt(input.n()), input.getValueForField("url", URI.class).orElse(null));
    }

    @Override
    public EnhancedType<OptModel> type() {
        return EnhancedType.of(OptModel.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
