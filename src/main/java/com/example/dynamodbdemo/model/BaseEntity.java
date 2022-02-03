package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@DynamoDbBean
public class BaseEntity {

    private final String contextId;
    private final String type;
    private final String text;
    private final Map<String, String> optionalData;
    private final Map<String, String> data;
    private final OptModel optModel;

    public BaseEntity() { // Default constructor
        this.contextId = null;
        this.type = null;
        this.text = null;
        this.optionalData = null;
        this.data = null;
        this.optModel = null;
    }

    public BaseEntity(String contextId, String type, String text) {
        this.contextId = contextId;
        this.type = type;
        this.text = text;
        this.optionalData = null;
        this.data = new HashMap<>();
        this.data.put("1", "2");

        OptModel optModel = new OptModel("OptModel", 1, URI.create("www.google.it"));
        this.optModel = optModel;
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

    //TODO: enabling this makes project startup fails
    public void setOptionalData(Optional<Map<String, String>> optionalData) { } // Do nothing, this is a derived attribute

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map data) { } // Do nothing, this is a derived attribute

    public OptModel getOptModel() {
        return optModel;
    }

    public void setOptModel(OptModel optModel) { } // Do nothing, this is a derived attribute
}
