package com.example.dynamodbdemo.model;

import com.example.dynamodbdemo.converter.CustomAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.DefaultAttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.net.URI;
import java.util.Optional;

@DynamoDbBean(converterProviders = {
        CustomAttributeConverterProvider.class,
        DefaultAttributeConverterProvider.class})
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

    public Optional<URI> getUrl() {
        return Optional.ofNullable(url);
    }

    public void setUrl(Optional<URI> url) { }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) { }
}
