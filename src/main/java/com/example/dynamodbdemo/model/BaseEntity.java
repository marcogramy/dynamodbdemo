package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.net.URI;
import java.time.Instant;
import java.util.*;

@DynamoDbBean
public class BaseEntity {

    private String contextId;
    private String type;
    private String text;
    private Map<String, String> optionalData;
    private Map<String, String> data;
    private OptModel optModel;
    private Set<State> states;
    private List<Instant> pushAttempts;
    private boolean removeDataFromMongo;

    public BaseEntity() { // Default constructor
        this.contextId = null;
        this.type = null;
        this.text = null;
        this.optionalData = null;
        this.data = null;
        this.optModel = null;
        this.states = null;
        this.pushAttempts = null;
        this.removeDataFromMongo = false;
    }

    public BaseEntity(String contextId, String type, String text) {
        this.contextId = contextId;
        this.type = type;
        this.text = text;
        this.optionalData = new HashMap<>();
        this.optionalData.put("1", "2");

        this.data = new HashMap<>();
        this.data.put("1", "2");

        OptModel optModel = new OptModel("OptModel", 1, URI.create("www.google.it"));
        this.optModel = optModel;
        this.states = EnumSet.of(Entity.State.DELIVERABLE);
        this.pushAttempts = new ArrayList<>();
        this.removeDataFromMongo = false;
    }

    @DynamoDbPartitionKey
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Optional<String> getText() {
        return Optional.ofNullable(text);
    }

    public void setText(Optional<String> text) {
        this.text = text.orElse(null);
    }

    public Optional<Map> getOptionalData() {
        return Optional.ofNullable(optionalData);
    }

    //TODO: enabling this makes project startup fails
    /*public void setOptionalData(Optional<Map<String, String>> optionalData) {
        this.optionalData = optionalData.orElse(null);
    }*/

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public OptModel getOptModel() {
        return optModel;
    }

    public void setOptModel(OptModel optModel) {
        this.optModel = optModel;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public List<Instant> getPushAttempts() {
        return pushAttempts;
    }

    public void setPushAttempts(List<Instant> pushAttempts) {
        this.pushAttempts = pushAttempts;
    }

    public boolean getRemoveDataFromMongo() {
        return removeDataFromMongo;
    }

    public void setRemoveDataFromMongo(boolean removeDataFromMongo) {
        this.removeDataFromMongo = removeDataFromMongo;
    }

    public enum State {
        DELIVERABLE(true, true),
        SENT(true, true),
        EXPIRED(true, true),
        DELIVERED(false, true),
        READ(false, false),
        CLICKED(false, false);

        private final boolean undelivered;
        private final boolean unread;

        State(boolean undelivered, boolean unread) {
            this.undelivered = undelivered;
            this.unread = unread;
        }

        boolean isUndelivered() {
            return undelivered;
        }

        boolean isUnread() {
            return unread;
        }

    }
}
