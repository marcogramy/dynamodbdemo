package com.example.dynamodbdemo;

import com.example.dynamodbdemo.model.BaseEntity;
import com.example.dynamodbdemo.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class EntityRepository {

    private final static Logger LOGGER = Logger.getLogger(DynamoDbDemoApplication.class.getName());

    private final DynamoDbTable<Entity> table;
    private final DynamoDbClient dynamoDbClient;

    @Autowired
    public EntityRepository(DynamoDbEnhancedClient dynamo, DynamoDbClient dynamoDbClient) {
        this.table = dynamo.table("demotable", TableSchema.fromBean(Entity.class));
        this.dynamoDbClient = dynamoDbClient;
    }

    public void save(Entity entity) {
        table.putItem(entity);
    }

    private Key getKey(String dispatchId) {
        return Key.builder()
                .partitionValue(dispatchId)
                .build();
    }

    // load
    public Entity load(String dispatchId) {
        return table.getItem(r->r.key(getKey(dispatchId)));
    }

    // pushState -> aggiungo un elemento ad un array
    /*public Entity pushState(String dispatchId, BaseEntity.State state) {
        Entity entity = table.getItem(r->r.key(getKey(dispatchId)));
        entity.getStates().add(state);
        return table.updateItem(entity);
    }*/

    public Entity pushState(String dispatchId, Entity.State state, Instant time) {
        Entity entity = table.getItem(r->r.key(getKey(dispatchId)));

        HashMap<String, AttributeValue> itemKey = new HashMap<>();
        itemKey.put("contextId", AttributeValue.builder()
                .s(dispatchId)
                .build());

        //GetItemRequest req = GetItemRequest.builder().tableName("demotable").key(itemKey).build();
        //GetItemResponse resp = dynamoDbClient.getItem(req);

        HashMap<String, AttributeValueUpdate> updatedValues = new HashMap<>();

        // Update the column specified by name with updatedVal
        updatedValues.put("states", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().ss(state.name()).build())
                .action(AttributeAction.ADD)
                .build());
        updatedValues.put("removeDataFromMongo", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().bool(true).build())
                .action(AttributeAction.PUT)
                .build());

        try {
            UpdateItemRequest request = UpdateItemRequest.builder()
                    .tableName("demotable")
                    .key(itemKey)
                    .attributeUpdates(updatedValues)
                    /*.updateExpression("SET pushAttempts = list_append(pushAttempts, :append_value)")
                    .expressionAttributeValues(Collections.singletonMap(":append_value",
                            AttributeValue.builder().l(AttributeValue.builder().s(time.toString()).build()).build()))*/
                    .returnValues(ReturnValue.ALL_NEW)
                    .build();

            UpdateItemResponse response = dynamoDbClient.updateItem(request);
        } catch (Exception ex) {
            LOGGER.warning("Error while updating DynamoDb oject: " + ex.getMessage());
        }

        //entity.getStates().add(state);
        return entity;
    }

    //TODO: addSentStateAndExpireAt -> aggiungo SENT ad un array e setto il valore expireAt

    //TODO: pushDelivered -> aggiungo DELIVERED e setto deliveredAt

    // pushExpired -> aggiungo EXPIRED e setto removeDataFromMongo a true
    public Entity pushExpired(String dispatchId) {
        Entity entity = table.getItem(r->r.key(getKey(dispatchId)));
        entity.getStates().add(BaseEntity.State.EXPIRED);
        return table.updateItem(entity);
    }

    // pushPushAttempt -> aggiungo il valore Instant a pushAttempts
    public Entity addPushAttempt(String dispatchId, Instant time) {
        Entity entity = table.getItem(r->r.key(getKey(dispatchId)));
        entity.getPushAttempts().add(time);
        return table.updateItem(entity);
    }

    // setExpireAt -> setto il valore expireAt (Instant)
    public Entity setExpireAt(String dispatchId, Instant expireAt) {
        Entity entity = table.getItem(r->r.key(getKey(dispatchId)));
        entity.setExpireAt(expireAt);
        return table.updateItem(entity);
    }

    //TODO: setRemoveDataFromMongo -> setto il valore removeDataFromMongo (bool)
}
