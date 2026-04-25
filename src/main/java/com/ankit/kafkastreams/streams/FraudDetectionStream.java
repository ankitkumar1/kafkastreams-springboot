package com.ankit.kafkastreams.streams;

import com.ankit.kafkastreams.event.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import tools.jackson.databind.ObjectMapper;

/**
 * This class is used to perform these tasks-
 * 1. Read events from transactions topic.
 * 2. Perform Fraud Detection
 * 3. Write to Fraud Alerts Topic.
 * */
@Configuration
@EnableKafkaStreams
@Slf4j
public class FraudDetectionStream {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> processTransactionStream(StreamsBuilder streamsBuilder) {
        // Step1: Reading from input topic.
        KStream<String, String> transactions = streamsBuilder.stream("transactions");

        // Step2: Perform Fraud Detection
        KStream<String, String> fraudsStream = transactions.filter((key, value) ->
                isSuspicious(value)).peek((key, value) -> log.info("Fraud Alert for transaction: "+ key + " - " + value));

        // Step 3: Write to Fraud Alerts Topic
        fraudsStream.to("fraud-alerts");

        return transactions;
    }

    private boolean isSuspicious(String value) {
        try{
            Transaction transaction = objectMapper.readValue(value, Transaction.class);
            if(transaction.amount() > 10000) {
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return false;
    }

}
