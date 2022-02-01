package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.Instant;
import java.util.Optional;

@DynamoDbBean
public class MiddleEntity extends BaseEntity {

    private final Long applicationId;
    private final String text;
    private final String notificationText;
    private final Instant deliveredAt;

    public MiddleEntity() {
        this.deliveredAt = null;
        applicationId = null;
        this.text = null;
        notificationText = null;
    }

    public MiddleEntity(String contextId, String type, Long applicationId) {
        super(contextId, type);
        this.applicationId = applicationId;
        this.deliveredAt = null;
        this.text = "text";
        this.notificationText = null;
    }

    public MiddleEntity(String contextId, String type, Long applicationId, String notificationText) {
        super(contextId, type);
        this.applicationId = applicationId;
        this.text = "text";
        this.notificationText = notificationText;
        this.deliveredAt = null;
    }

    public Optional<Long> getApplicationId() {
        return Optional.ofNullable(applicationId);
    }

    public void setApplicationId(Optional<Long> applicationId) {
        // Do nothing, this is a derived attribute
    }

    public String getText() {
        return text;
    }

    public void setText(String text) { } // Do nothing, this is a derived attribute

    public Optional<String> getNotificationText() {
        return Optional.ofNullable(notificationText);
    }

    public void setNotificationText(Optional<String> notificationText) {
        // Do nothing, this is a derived attribute
    }

    public Optional<Instant> getDeliveredAt() {
        return Optional.ofNullable(deliveredAt);
    }

    //public void setDeliveredAt(Optional<Instant> deliveredAt) { } // Do nothing, this is a derived attribute

}
