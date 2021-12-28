package com.example.dynamodbdemo;

import com.example.dynamodbdemo.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Component
public class EntityRepository {

    private final DynamoDbTable<Entity> table;

    public EntityRepository(@Autowired DynamoDbEnhancedClient dynamo) {
        this.table = dynamo.table("demotable", TableSchema.fromBean(Entity.class));
    }

    public void save(Entity entity) {
        table.putItem(entity);
    }
}
