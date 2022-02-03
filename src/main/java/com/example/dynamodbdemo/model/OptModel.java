package com.example.dynamodbdemo.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.net.URI;

@DynamoDbBean
public class OptModel {

    private final String innerValue;
    private int intValue;
    private URI url;
    private ContentType contentType;

    public enum ContentType {
        URLENCODED, JSON, XML;
    }

    public OptModel() {
        innerValue = null;
    }

    public OptModel(String s, int i, URI u) {
        innerValue = s;
        intValue = i;
        url = u;
        contentType = ContentType.JSON;
    }

    public String getInnerValue() {
        return innerValue;
    }

    public void setInnerValue(String innerValue) { }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) { }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) { }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) { }
}
