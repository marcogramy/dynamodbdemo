package com.example.dynamodbdemo.model;

import com.example.dynamodbdemo.converter.CustomAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@DynamoDbBean(converterProviders = {
        CustomAttributeConverterProvider.class,
        DefaultAttributeConverterProvider.class})
public class Entity extends MiddleEntity {

    private final Long customerId;

    public Entity() {
        customerId = null;
    }

    public Entity(String contextId, String type, Long customerId, Long applicationId, String notificationText) {
        super(contextId, type, applicationId, notificationText);
        this.customerId = customerId;
    }

    public Entity(String contextId, String type, Long customerId, Long applicationId) {
        super(contextId, type, applicationId);
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        // Do nothing, this is a derived attribute
    }


}
