package com.ankit.kafkastreams.service;

import com.ankit.kafkastreams.event.Transaction;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Random;

@Service
public class TransactionService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TransactionService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendTransaction() {
        for (int i=0; i<50; i++){
            String transactionId = "txn-"+System.currentTimeMillis()+"-"+i;
            String userId = "user-"+i;
            double amount = 9000 + new Random().nextInt(10) * (200);
            Transaction transaction = new Transaction(transactionId, userId, amount, LocalDate.now().toString());

            this.kafkaTemplate.send("transactions", transactionId, this.objectMapper.writeValueAsString(transaction));
        }
        return "✅ Transaction sent to Kafka!";
    }
}
