package com.example.dynamodbdemo.model;

import com.example.dynamodbdemo.converter.CustomAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.util.Optional;

@DynamoDbBean(converterProviders = {
        CustomAttributeConverterProvider.class,
        DefaultAttributeConverterProvider.class})
public class Entity extends BaseEntity {

    private final Long customerId;
    private final Long applicationId;
    private final String text;

    public Entity() {
        customerId = null;
        applicationId = null;
        text = null;
    }

    public Entity(String contextId, String type, Long customerId, Long applicationId, String text) {
        super(contextId, type);
        this.customerId = customerId;
        this.applicationId = applicationId;
        this.text = text;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        // Do nothing, this is a derived attribute
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        // Do nothing, this is a derived attribute
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    public void setText(Optional<String> text) {
        // Do nothing, this is a derived attribute
    }
}
