package com.example.dynamodbdemo;

import com.example.dynamodbdemo.model.BaseEntity;
import com.example.dynamodbdemo.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DemoService {

    private final static Logger LOGGER = Logger.getLogger(DynamoDbDemoApplication.class.getName());

    private EntityRepository entityRepository;

    @Autowired
    DemoService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;

        // Example entity object
        String randomId = getRandomString();
        Entity demoEntity = new Entity(randomId, "type", 10L, "fixed text");

        try {
            entityRepository.save(demoEntity);

            demoEntity.setText(Optional.of("modified text"));
            entityRepository.save(demoEntity);

            entityRepository.pushState(randomId, BaseEntity.State.SENT, Instant.now());
            entityRepository.pushState("asdf", BaseEntity.State.SENT, Instant.now());

        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "DynamoDb error: " + ex.getMessage());
        }

        String dispatchId = "ihloohsuaq";

        //entityRepository.setExpireAt(dispatchId, Instant.now());
        //entityRepository.pushExpired(dispatchId);
        //entityRepository.addPushAttempt(dispatchId, Instant.now());
        //entityRepository.pushState(dispatchId, BaseEntity.State.DELIVERED);

        //Entity loadedEntity = entityRepository.load(dispatchId);
        //LOGGER.log(Level.INFO, "loadedEntity: " + loadedEntity.getText());
    }

    private String getRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
