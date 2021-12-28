package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
public class BaseEntity {

    private final String contextId;
    private final String type;

    public BaseEntity() { // Default constructor
        this.contextId = null;
        this.type = null;
    }

    public BaseEntity(String contextId, String type) {
        this.contextId = contextId;
        this.type = type;
    }

    @DynamoDbPartitionKey
    public String getContextId() {
        return contextId;
    }

    public String getType() {
        return type;
    }

    // Mandatory methods for DynamoDb
    public void setContextId(String contextId) {
        // Do nothing, this is a derived attribute
    }
    public void setType(String type) {
        // Do nothing, this is a derived attribute
    }
}
