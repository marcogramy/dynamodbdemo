package com.example.dynamodbdemo;

import com.example.dynamodbdemo.model.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Entity demoEntity = new Entity(getRandomString(), "type", 10L, 20L);

        try {
            entityRepository.save(demoEntity);
        } catch (Exception ex) {
            LOGGER.log(Level.WARNING, "DynamoDb error: " + ex.getMessage());
        }
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
