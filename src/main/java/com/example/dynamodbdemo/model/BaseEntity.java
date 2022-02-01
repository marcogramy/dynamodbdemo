package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Map;
import java.util.Optional;

@DynamoDbBean
public class BaseEntity {

    private final String contextId;
    private final String type;
    private final String text;
    private final Map<String, String> optionalData;

    public BaseEntity() { // Default constructor
        this.contextId = null;
        this.type = null;
        this.text = null;
        this.optionalData = null;
    }

    public BaseEntity(String contextId, String type, String text) {
        this.contextId = contextId;
        this.type = type;
        this.text = text;
        this.optionalData = null;
    }

    @DynamoDbPartitionKey
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        // Do nothing, this is a derived attribute
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        // Do nothing, this is a derived attribute
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    public void setText(Optional<String> notificationText) {
        // Do nothing, this is a derived attribute
    }

    public Optional<Map<String, String>> getOptionalData() {
        return Optional.ofNullable(optionalData);
    }

    public void setOptionalData(Optional<Map<String, String>> optionalData) { } // Do nothing, this is a derived attribute
}
